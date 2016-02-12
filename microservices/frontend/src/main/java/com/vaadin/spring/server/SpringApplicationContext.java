package com.vaadin.spring.server;

import org.springframework.context.ApplicationContext;

import java.io.Serializable;

/**
 * @author xpoft
 */
public class SpringApplicationContext implements Serializable {

    private static final long serialVersionUID = -7536223892169804133L;

    private static transient ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringApplicationContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
