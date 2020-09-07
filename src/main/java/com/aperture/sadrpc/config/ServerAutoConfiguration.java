package com.aperture.sadrpc.config;

import com.aperture.sadrpc.annotation.SadProvider;
import com.aperture.sadrpc.server.NettyServer;
import com.aperture.sadrpc.server.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
@ConditionalOnClass(SadProvider.class)
@EnableConfigurationProperties(SadRpcProperties.class)
@Slf4j
public class ServerAutoConfiguration {

    @Autowired
    private SadRpcProperties properties;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(SadProvider.class);
        if (!beans.isEmpty()) {
            log.info("server init");
            beans.forEach(ServiceProvider::registry);
            NettyServer server = new NettyServer(properties.getPort());
            server.start();
        }
    }
}
