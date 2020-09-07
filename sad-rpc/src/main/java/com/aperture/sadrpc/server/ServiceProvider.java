package com.aperture.sadrpc.server;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ServiceProvider {
    private static Map<String, Object> beanMap = new HashMap<>();

    public static void registry(String name, Object bean) {
        log.info("registry {}", name);
        beanMap.put(name, bean);
    }

    public static Object find(String name) {
        return beanMap.get(name);
    }
}
