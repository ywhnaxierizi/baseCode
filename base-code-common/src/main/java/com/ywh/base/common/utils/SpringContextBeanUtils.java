package com.ywh.base.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring上下文工具类，可以手动获取指定的bean
 */
@Component
public class SpringContextBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextBeanUtils.applicationContext == null) {
            SpringContextBeanUtils.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(Class<T> tclazz) {
        return getApplicationContext().getBean(tclazz);
    }

    public static <T> T getBean(String beanName, Class<T> tClazz) {
        return getApplicationContext().getBean(beanName, tClazz);
    }

}
