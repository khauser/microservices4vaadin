package microservices4vaadin.frontend.ui;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.jdal.annotation.SerializableProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.vaadin.annotations.PropertyId;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import microservices4vaadin.auth.AcmeUser;
import microservices4vaadin.frontend.rest.controller.AcmeUserController;
import microservices4vaadin.frontend.rest.controller.UserServiceController;
import microservices4vaadin.frontend.rest.resource.dto.AbstractPersonDTO.Gender;
import microservices4vaadin.frontend.rest.resource.dto.UserServiceUserDTO;
import microservices4vaadin.frontend.ui.event.MyEvent.ProfileUpdatedEvent;

@UIScope
@SpringComponent
public class ProfileWindow extends Window {

    private static final long serialVersionUID = 8301371976147083612L;

    public static final String ID = "profileWindow";

    private boolean initialized = false;

    @PropertyId("firstName")
    private TextField firstNameField;
    @PropertyId("lastName")
    private TextField lastNameField;
    @PropertyId("title")
    private ComboBox<String> titleField;
    @PropertyId("gender")
    private RadioButtonGroup<Gender> genderField;
    @PropertyId("phone")
    private TextField phoneField;
    @PropertyId("language")
    private ComboBox<String> languageField;

    @PropertyId("email")
    private TextField emailField;

    private PasswordField passwordOldField;
    private PasswordField passwordNewField;
    private PasswordField passwordConfirmField;

    private Binder<UserServiceUserDTO> binderUserServiceUser = new Binder<>();
    private Binder<AcmeUser> binderAcmeUser = new Binder<>();

    @Autowired
    @SerializableProxy
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    @SerializableProxy
    private AcmeUserController acmeUserController;

    @Autowired
    @SerializableProxy
    private UserServiceController userController;

    private AcmeUser acmeUser;
    private UserServiceUserDTO user;

    @PostConstruct
    protected void init() {
        setId(ID);
        setModal(true);
        setResizable(false);
        setClosable(true);
        //setSizeFull();
    }

    private void enter() {
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);

        content.addComponent(buildProfileTab());
        content.addComponent(buildFooter());

        setContent(content);
    }

    private Component buildProfileTab() {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption("Edit profile");
        root.setIcon(VaadinIcons.USER);
        root.setSpacing(true);
        root.setMargin(true);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);

        firstNameField = new TextField("First name");
        details.addComponent(firstNameField);
        lastNameField = new TextField("Last name");
        details.addComponent(lastNameField);

        titleField = new ComboBox<>("Title");
        //titleField.setInputPrompt("Please specify");
        titleField.setItems(Arrays.asList("Mr.", "Mrs.", "Ms."));
        //titleField.setNewItemsAllowed(true);
        details.addComponent(titleField);

        genderField = new RadioButtonGroup<>("Gender");
        genderField.setItems(Arrays.asList(Gender.FEMALE, Gender.MALE));
        genderField.addStyleName("horizontal");
        details.addComponent(genderField);

        phoneField = new TextField("Phone");
        phoneField.setWidth("100%");
        //phoneField.setNullRepresentation("");
        details.addComponent(phoneField);

        Label section = new Label("Contact info");
        section.addStyleName(ValoTheme.LABEL_H4);
        section.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(section);

        emailField = new TextField("E-Mail");
        emailField.setWidth("100%");
        //emailField.setRequired(true);
        //emailField.setNullRepresentation("");
        details.addComponent(emailField);

        passwordOldField = new PasswordField("Old password");
        passwordOldField.setWidth("100%");
        //passwordOldField.setRequired(true);
        //passwordOldField.setNullRepresentation("");
        passwordOldField.setValue(null);
        details.addComponent(passwordOldField);

        passwordNewField = new PasswordField("New password");
        passwordNewField.setWidth("100%");
        //passwordNewField.setRequired(true);
        //passwordNewField.setNullRepresentation("");
        passwordNewField.setValue(null);
        details.addComponent(passwordNewField);

        passwordConfirmField = new PasswordField("Confirm new password");
        passwordConfirmField.setWidth("100%");
        //passwordConfirmField.setRequired(true);
        //passwordConfirmField.setNullRepresentation("");
        passwordConfirmField.setValue(null);
        details.addComponent(passwordConfirmField);

        return root;
    }

    private Component buildFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button save = new Button("Save", this::save);
        Button cancel = new Button("Cancel", this::cancel);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        save.focus();
        footer.addComponent(cancel);
        footer.addComponent(save);
        footer.setComponentAlignment(save, Alignment.TOP_RIGHT);
        return footer;
    }

    /**
     * @param event
     */
    public void save(Button.ClickEvent event) {
        try {
            String newPassword = null;
            if (passwordNewField.getValue() != null) {
                newPassword = passwordNewField.getValue();
                if (!newPassword.equals(passwordConfirmField.getValue()) || newPassword.length()<6) {
                    Notification.show("New password is not valid", Notification.Type.ERROR_MESSAGE);
                    return;
                }
            }
            acmeUserController.updateCredentials(emailField.getValue(), newPassword, passwordOldField.getValue());

            userController.update(user);

            Notification.show("Saved", Notification.Type.HUMANIZED_MESSAGE);

            eventPublisher.publishEvent(new ProfileUpdatedEvent(MyVaadinUI.getCurrent()));
            close();


            binderUserServiceUser.writeBean(user);
            binderAcmeUser.writeBean(acmeUser);
        } catch (ValidationException e) {
            Notification.show("Person could not be saved, " +
              "please check error messages for each field.");
        }
    }

    public void cancel(Button.ClickEvent event) {
        Notification.show("Cancelled", Notification.Type.HUMANIZED_MESSAGE);
        close();
    }

    /**
     * @param user
     */
    public void open(UserServiceUserDTO user) {
        this.user = user;
        this.acmeUser = user.getAcmeUser();

        if (!initialized) {
            enter();
            initialized = true;
        }

        binderUserServiceUser.readBean(user);
        binderAcmeUser.readBean(acmeUser);

        UI.getCurrent().addWindow(this);
        focus();
        center();
    }

}
