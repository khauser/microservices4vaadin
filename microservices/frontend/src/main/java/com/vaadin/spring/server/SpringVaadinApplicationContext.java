package com.vaadin.spring.server;

import org.springframework.context.ApplicationContext;

public class SpringVaadinApplicationContext {

    private static transient ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringVaadinApplicationContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
