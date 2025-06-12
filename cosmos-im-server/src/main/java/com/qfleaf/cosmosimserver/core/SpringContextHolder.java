package com.qfleaf.cosmosimserver.core;

import jakarta.annotation.Nonnull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) {
        SpringContextHolder.context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }
}
