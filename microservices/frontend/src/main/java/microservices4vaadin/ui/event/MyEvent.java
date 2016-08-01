package microservices4vaadin.ui.event;

import org.springframework.context.ApplicationEvent;

import microservices4vaadin.ui.MyViewType;


public abstract class MyEvent {

    public static final class UserLoginRequestedEvent extends ApplicationEvent {

        private static final long serialVersionUID = -7321399321684894402L;

        private final String userName, password;

        public UserLoginRequestedEvent(final Object object, final String userName,
                final String password) {
            super(object);
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class BrowserResizeEvent extends ApplicationEvent {
        private static final long serialVersionUID = -2271776614739128205L;

        public BrowserResizeEvent(Object source) {
            super(source);
            // TODO Auto-generated constructor stub
        }
    }

    public static class UserLoggedOutEvent extends ApplicationEvent {
        private static final long serialVersionUID = -4853914464587997188L;

        public UserLoggedOutEvent(Object source) {
            super(source);
            // TODO Auto-generated constructor stub
        }
    }

//    public static class NotificationsCountUpdatedEvent {
//    }


    public static final class PostViewChangeEvent extends ApplicationEvent {
        private static final long serialVersionUID = -3716302525487625973L;
        private final MyViewType view;

        public PostViewChangeEvent(final Object object, final MyViewType view) {
            super(object);
            this.view = view;
        }

        public MyViewType getView() {
            return view;
        }
    }

    public static class CloseOpenWindowsEvent extends ApplicationEvent {
        private static final long serialVersionUID = -4672026509699779702L;

        public CloseOpenWindowsEvent(Object source) {
            super(source);
            // TODO Auto-generated constructor stub
        }
    }

    public static class ProfileUpdatedEvent extends ApplicationEvent {
        private static final long serialVersionUID = 2215996354583273590L;

        public ProfileUpdatedEvent(Object source) {
            super(source);
            // TODO Auto-generated constructor stub
        }
    }

}
