package com.yesterz.netty.medium;

import com.yesterz.netty.annotation.Remote;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.Map;

@Component
public class InitialMedium implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 初始化之前
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 初始化之后才处理
        // isAnnotationPresent 来判断这个类上面是否有Controller注解
        if (bean.getClass().isAnnotationPresent(Remote.class)) {
//            System.out.println(bean.getClass().getName());
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method m : methods) {
                String key = bean.getClass().getInterfaces()[0].getName() + "." + m.getName();
                Map<String, BeanMethod> beanMap = Medium.beanMap;
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(m);
                beanMap.put(key, beanMethod);
            }

        }

        return bean;
    }
}
