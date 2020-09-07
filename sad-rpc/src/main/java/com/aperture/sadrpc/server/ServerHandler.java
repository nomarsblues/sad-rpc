package com.aperture.sadrpc.server;

import com.aperture.sadrpc.request.RpcRequest;
import com.aperture.sadrpc.response.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

public class ServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest request)
            throws Exception {
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        try {
            Object result = handle(request);
            response.setResult(result);
        } catch (Exception e) {
            response.setThrowable(e);
        }
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handle(RpcRequest request) throws Exception {
        Object provider = ServiceProvider.find(request.getClassName());
        Class<?> clz = provider.getClass();
        Method method = clz.getMethod(request.getMethodName(), request.getParameterTypes());
        return method.invoke(provider, request.getParameters());
    }
}
