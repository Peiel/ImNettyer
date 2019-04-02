package com.peierlong.netty.echo.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Peiel
 * @version V1.0
 * @date 2019-03-07
 */
@Sharable
public class EchoServerChannelHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String body = (String) msg;
        System.out.println("Server received: " + body + ", counter is " + ++counter);

        body += "$_";
        ctx.writeAndFlush(Unpooled.copiedBuffer(body.getBytes()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
