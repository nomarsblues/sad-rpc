package com.aperture.sadrpc.client;

import com.aperture.sadrpc.request.RpcRequest;
import com.aperture.sadrpc.response.RpcResponse;
import com.aperture.sadrpc.serialize.JsonSerialization;
import com.aperture.sadrpc.serialize.RpcDecoder;
import com.aperture.sadrpc.serialize.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyClient {
    private String ip;
    private Integer port;

    public NettyClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public RpcResponse send(RpcRequest request) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(65535,0,4))
                                .addLast(new RpcEncoder(RpcRequest.class, new JsonSerialization()))
                                .addLast(new RpcDecoder(RpcResponse.class, new JsonSerialization()))
                                .addLast(new ClientHandler());
                    }

                }).option(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            channelFuture.channel().writeAndFlush(request).sync();
            return RpcResult.get(request.getRequestId());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
