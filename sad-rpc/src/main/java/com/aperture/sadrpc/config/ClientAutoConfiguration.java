package com.aperture.sadrpc.config;

import com.aperture.sadrpc.annotation.SadConsumer;
import com.aperture.sadrpc.client.RpcProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;

@Configuration
@ConditionalOnClass(SadConsumer.class)
@Slf4j
public class ClientAutoConfiguration {

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                Class<?> clz = bean.getClass();
                for (Field field : clz.getDeclaredFields()) {
                    SadConsumer anno = field.getAnnotation(SadConsumer.class);
                    if (anno != null) {
                        log.info("proxy consumer, {}", beanName);
                        field.setAccessible(true);
                        Class<?> type = field.getType();
                        try {
                            field.set(bean, RpcProxy.create(type));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } finally {
                            field.setAccessible(false);
                        }
                    }
                }
                return bean;
            }
        };
    }
}
