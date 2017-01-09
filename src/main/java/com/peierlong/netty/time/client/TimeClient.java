package com.peierlong.netty.time.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 包名: com.peierlong.netty.time.client
 * 创建人 : Elong
 * 时间: 2017/1/8 下午7:49
 * 描述 :
 */
public class TimeClient {

    public void connect() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            //发起异步连接请求
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
            //等待客户端链路关闭
            future.channel().closeFuture().sync();
        } finally {
            //退出，释放NIO线程组
            eventLoopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        try {
            new TimeClient().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
