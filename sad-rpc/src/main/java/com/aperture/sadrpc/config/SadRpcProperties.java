package com.aperture.sadrpc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "sad.rpc")
public class SadRpcProperties {
    private int port;
    private String zkPath;
}
