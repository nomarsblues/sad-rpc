package com.aperture.sadrpc.client;

import com.aperture.sadrpc.request.RpcRequest;
import com.aperture.sadrpc.response.RpcResponse;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class ClientHandler extends ChannelDuplexHandler {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof RpcRequest) {
            RpcResult.put(((RpcRequest)msg).getRequestId());
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RpcResponse) {
            RpcResponse response = (RpcResponse) msg;
            RpcResult.complete(response.getRequestId(), response);
        }
        super.channelRead(ctx, msg);
    }
}
