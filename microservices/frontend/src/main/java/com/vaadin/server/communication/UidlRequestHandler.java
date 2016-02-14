/*
 * Copyright 2000-2014 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.server.communication;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.server.LegacyApplicationUIProvider;
import com.vaadin.server.LegacyCommunicationManager.InvalidUIDLSecurityKeyException;
import com.vaadin.server.ServletPortletHelper;
import com.vaadin.server.SessionExpiredHandler;
import com.vaadin.server.SynchronizedRequestHandler;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.JsonConstants;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.server.SpringUIProvider;
import com.vaadin.ui.UI;

import elemental.json.JsonException;
import microservices4vaadin.ui.MyVaadinUI;

/**
 * Processes a UIDL request from the client.
 *
 * Uses {@link ServerRpcHandler} to execute client-to-server RPC invocations and
 * {@link UidlWriter} to write state changes and client RPC calls back to the
 * client.
 *
 * @author Vaadin Ltd
 * @since 7.1
 */
@SuppressWarnings("deprecation")
public class UidlRequestHandler extends SynchronizedRequestHandler implements
        SessionExpiredHandler {

    private static final long serialVersionUID = -7219295552075912879L;

    public static final String UIDL_PATH = "UIDL/";

    private ServerRpcHandler rpcHandler = new ServerRpcHandler();

    public UidlRequestHandler() {
    }

    @Override
    protected boolean canHandleRequest(VaadinRequest request) {
        return ServletPortletHelper.isUIDLRequest(request);
    }

    @Override
    public boolean synchronizedHandleRequest(VaadinSession session,
            VaadinRequest request, VaadinResponse response) throws IOException {
        UI uI = getBrowserDetailsUI(request, session);
        if (uI == null) {
            // This should not happen but it will if the UI has been closed. We
            // really don't want to see it in the server logs though
            UIInitHandler.commitJsonResponse(request, response,
                    getUINotFoundErrorJSON(session.getService(), request));
            return true;
        }

        StringWriter stringWriter = new StringWriter();

        try {
            rpcHandler.handleRpc(uI, request.getReader(), request);

            writeUidl(request, response, uI, stringWriter);
        } catch (JsonException e) {
            getLogger().log(Level.SEVERE, "Error writing JSON to response", e);
            // Refresh on client side
            writeRefresh(request, response);
            return true;
        } catch (InvalidUIDLSecurityKeyException e) {
            getLogger().log(Level.WARNING,
                    "Invalid security key received from {0}",
                    request.getRemoteHost());
            // Refresh on client side
            writeRefresh(request, response);
            return true;
        } finally {
            stringWriter.close();
        }

        return UIInitHandler.commitJsonResponse(request, response,
                stringWriter.toString());
    }

    private void writeRefresh(VaadinRequest request, VaadinResponse response)
            throws IOException {
        String json = VaadinService.createCriticalNotificationJSON(null, null,
                null, null);
        UIInitHandler.commitJsonResponse(request, response, json);
    }

    private void writeUidl(VaadinRequest request, VaadinResponse response,
            UI ui, Writer writer) throws IOException {
        openJsonMessage(writer, response);

        new UidlWriter().write(ui, writer, false);

        closeJsonMessage(writer);
    }

    protected void closeJsonMessage(Writer outWriter) throws IOException {
        outWriter.write("}]");
    }

    /**
     * Writes the opening of JSON message to be sent to client.
     *
     * @param outWriter
     * @param response
     * @throws IOException
     */
    protected void openJsonMessage(Writer outWriter, VaadinResponse response)
            throws IOException {
        // some dirt to prevent cross site scripting
        outWriter.write("for(;;);[{");
    }

    private static final Logger getLogger() {
        return Logger.getLogger(UidlRequestHandler.class.getName());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.vaadin.server.SessionExpiredHandler#handleSessionExpired(com.vaadin
     * .server.VaadinRequest, com.vaadin.server.VaadinResponse)
     */
    @Override
    public boolean handleSessionExpired(VaadinRequest request,
            VaadinResponse response) throws IOException {
        if (!ServletPortletHelper.isUIDLRequest(request)) {
            return false;
        }
        VaadinService service = request.getService();
        SystemMessages systemMessages = service.getSystemMessages(
                ServletPortletHelper.findLocale(null, null, request), request);

        service.writeStringResponse(response, JsonConstants.JSON_CONTENT_TYPE,
                VaadinService.createCriticalNotificationJSON(
                        systemMessages.getSessionExpiredCaption(),
                        systemMessages.getSessionExpiredMessage(), null,
                        systemMessages.getSessionExpiredURL()));

        return true;
    }

    /**
     * Returns the JSON which should be returned to the client when a request
     * for a non-existent UI arrives.
     *
     * @param service
     *            The VaadinService
     * @param vaadinRequest
     *            The request which triggered this, or null if not available
     * @since 7.1
     * @return A JSON string
     */
    static String getUINotFoundErrorJSON(VaadinService service,
            VaadinRequest vaadinRequest) {
        SystemMessages ci = service.getSystemMessages(
                vaadinRequest.getLocale(), vaadinRequest);
        // Session Expired is not really the correct message as the
        // session exists but the requested UI does not.
        // Using Communication Error for now.
        String json = VaadinService.createCriticalNotificationJSON(
                ci.getCommunicationErrorCaption(),
                ci.getCommunicationErrorMessage(), null,
                ci.getCommunicationErrorURL());

        return json;
    }

    private UI getBrowserDetailsUI(VaadinRequest request, VaadinSession session) {
        VaadinService vaadinService = request.getService();

        List<UIProvider> uiProviders = session.getUIProviders();

        UIClassSelectionEvent classSelectionEvent = new UIClassSelectionEvent(
                request);

        UIProvider provider = null;
        Class<? extends UI> uiClass = null;
        for (UIProvider p : uiProviders) {
            // Check for existing LegacyWindow
            if (p instanceof LegacyApplicationUIProvider) {
                LegacyApplicationUIProvider legacyProvider = (LegacyApplicationUIProvider) p;

                UI existingUi = legacyProvider
                        .getExistingUI(classSelectionEvent);
                if (existingUi != null) {
                    reinitUI(existingUi, request);
                    return existingUi;
                }
            }

            uiClass = p.getUIClass(classSelectionEvent);
            if (uiClass != null) {
                provider = p;
                break;
            }
        }
        if (provider == null) {
            provider = new SpringUIProvider();
            uiClass = MyVaadinUI.class;
        }

        if (uiClass == null) {
            return null;
        }

        // Check for an existing UI based on embed id

        String embedId = getEmbedId(request);

        UI retainedUI = session.getUIByEmbedId(embedId);
        if (retainedUI != null) {
            if (vaadinService.preserveUIOnRefresh(provider, new UICreateEvent(
                    request, uiClass))) {
                if (uiClass.isInstance(retainedUI)) {
                    reinitUI(retainedUI, request);
                    return retainedUI;
                } else {
                    getLogger().info(
                            "Not using the preserved UI " + embedId
                                    + " because it is of type "
                                    + retainedUI.getClass() + " but " + uiClass
                                    + " is expected for the request.");
                }
            }
            /*
             * Previous UI without preserve on refresh will be closed when the
             * new UI gets added to the session.
             */
        }

        // No existing UI found - go on by creating and initializing one

        Integer uiId = Integer.valueOf(session.getNextUIid());

        // Explicit Class.cast to detect if the UIProvider does something
        // unexpected
        UICreateEvent event = new UICreateEvent(request, uiClass, uiId);
        UI ui = uiClass.cast(provider.createInstance(event));

        // Initialize some fields for a newly created UI
        if (ui.getSession() != session) {
            // Session already set for LegacyWindow
            ui.setSession(session);
        }

        PushMode pushMode = provider.getPushMode(event);
        if (pushMode == null) {
            pushMode = session.getService().getDeploymentConfiguration()
                    .getPushMode();
        }
        ui.getPushConfiguration().setPushMode(pushMode);

        Transport transport = provider.getPushTransport(event);
        if (transport != null) {
            ui.getPushConfiguration().setTransport(transport);
        }

        // Set thread local here so it is available in init
        UI.setCurrent(ui);

        ui.doInit(request, uiId.intValue(), embedId);

        session.addUI(ui);

        // Warn if the window can't be preserved
        if (embedId == null
                && vaadinService.preserveUIOnRefresh(provider, event)) {
            getLogger().warning(
                    "There is no embed id available for UI " + uiClass
                            + " that should be preserved.");
        }

        return ui;
    }

    /**
     * Constructs an embed id based on information in the request.
     *
     * @since 7.2
     *
     * @param request
     *            the request to get embed information from
     * @return the embed id, or <code>null</code> if id is not available.
     *
     * @see UI#getEmbedId()
     */
    protected String getEmbedId(VaadinRequest request) {
        // Parameters sent by vaadinBootstrap.js
        String windowName = request.getParameter("v-wn");
        String appId = request.getParameter("v-appId");

        if (windowName != null && appId != null) {
            return windowName + '.' + appId;
        } else {
            return null;
        }
    }

    /**
     * Updates a UI that has already been initialized but is now loaded again,
     * e.g. because of {@link PreserveOnRefresh}.
     *
     * @param ui
     * @param request
     */
    private void reinitUI(UI ui, VaadinRequest request) {
        UI.setCurrent(ui);
        ui.doRefresh(request);
    }

}
