package com.aperture.sadrpc.client;

import com.aperture.sadrpc.request.RpcRequest;
import com.aperture.sadrpc.response.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.UUID;

@Slf4j
public class RpcProxy {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<?> clz) {
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class<?>[]{clz},
                (proxy, method, args) -> {
                    RpcRequest request = new RpcRequest();
                    request.setRequestId(UUID.randomUUID().toString());
                    request.setClassName(method.getDeclaringClass().getName());
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setParameters(args);
                    // todo
                    NettyClient rpcClient = new NettyClient("127.0.0.1", 1234);
                    RpcResponse response = rpcClient.send(request);
                    if (response.getThrowable() != null) {
                        log.error("invoke err, ", response.getThrowable());
                    }
                    return response.getResult();
                });
    }
}
