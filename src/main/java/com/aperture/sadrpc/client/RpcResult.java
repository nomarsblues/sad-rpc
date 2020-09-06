package com.aperture.sadrpc.client;


import com.aperture.sadrpc.response.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class RpcResult {
    private static Map<String, CompletableFuture<RpcResponse>> result = new ConcurrentHashMap<>();

    public static void put(String requestId) {
        result.put(requestId, new CompletableFuture<>());
    }

    public static RpcResponse get(String requestId) {
        CompletableFuture<RpcResponse> future = result.get(requestId);
        try {
            RpcResponse response = future.get();
            result.remove(requestId);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void complete(String requestId, RpcResponse response) {
        CompletableFuture<RpcResponse> future = result.get(requestId);
        future.complete(response);
    }
}
