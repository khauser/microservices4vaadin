package microservices4vaadin.frontend.ui;

import org.jsoup.nodes.Element;

import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;

public class MySessionInitListener implements SessionInitListener {

    private static final long serialVersionUID = -6881763622904226924L;

    @Override
    public final void sessionInit(final SessionInitEvent event)
            throws ServiceException {

        event.getSession().addBootstrapListener(new BootstrapListener() {
            private static final long serialVersionUID = -4273381397632294262L;

            @Override
            public void modifyBootstrapPage(final BootstrapPageResponse response) {
                final Element head = response.getDocument().head();
                head.appendElement("meta")
                        .attr("name", "viewport")
                        .attr("content",
                                "width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no");
                head.appendElement("meta")
                        .attr("name", "apple-mobile-web-app-capable")
                        .attr("content", "yes");
                head.appendElement("meta")
                        .attr("name", "apple-mobile-web-app-status-bar-style")
                        .attr("content", "black-translucent");
            }

            @Override
            public void modifyBootstrapFragment(
                    final BootstrapFragmentResponse response) {
            }
        });
    }

}
