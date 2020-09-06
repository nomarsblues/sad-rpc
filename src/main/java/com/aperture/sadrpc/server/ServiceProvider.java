package com.aperture.sadrpc.server;

import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
    private static Map<String, Object> beanMap = new HashMap<>();

    public static void registry(String name, Object bean) {
        beanMap.put(name, bean);
    }

    public static Object find(String name) {
        return beanMap.get(name);
    }
}
