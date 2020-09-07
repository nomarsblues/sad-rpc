package com.aperture.sadrpc.config;

import com.aperture.sadrpc.annotation.SadConsumer;
import com.aperture.sadrpc.client.RpcProxy;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConditionalOnClass(SadConsumer.class)
@Slf4j
public class ClientAutoConfiguration implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("client start proxy");
        Reflections reflections = new Reflections("com.aperture");
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        Set<Class<?>> targetClzs = reflections.getTypesAnnotatedWith(SadConsumer.class);
        for (Class<?> targetClz : targetClzs) {
            beanFactory.registerSingleton(targetClz.getSimpleName(), RpcProxy.create(targetClz));
        }
    }
}
