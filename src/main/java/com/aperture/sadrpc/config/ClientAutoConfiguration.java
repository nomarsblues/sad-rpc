package com.aperture.sadrpc.config;

import com.aperture.sadrpc.annotation.SadConsumer;
import com.aperture.sadrpc.client.RpcProxy;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConditionalOnClass(SadConsumer.class)
public class ClientAutoConfiguration implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Reflections reflections = new Reflections("com.aperture");
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        Set<Class<?>> targetClzs = reflections.getTypesAnnotatedWith(SadConsumer.class);
        for (Class<?> targetClz : targetClzs) {
            beanFactory.registerSingleton(targetClz.getSimpleName(), RpcProxy.create(targetClz));
        }
    }
}
