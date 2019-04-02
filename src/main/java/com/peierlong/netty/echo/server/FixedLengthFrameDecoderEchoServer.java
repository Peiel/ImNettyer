package com.peierlong.netty.echo.server;

import com.peierlong.netty.test.FixedLengthFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;

/**
 * @author Peiel
 * @version V1.0
 * @date 2019-04-02
 */
public class FixedLengthFrameDecoderEchoServer {

    private int port;


    public FixedLengthFrameDecoderEchoServer(int port) {
        this.port = port;
    }

    private void start() throws Exception {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoServerChannelHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println(FixedLengthFrameDecoderEchoServer.class.getName() + " start and listen on " + future.channel().localAddress());
            future.channel().closeFuture().sync();
        } finally {
            nioEventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        FixedLengthFrameDecoderEchoServer server = new FixedLengthFrameDecoderEchoServer(8800);
        server.start();
    }

}
