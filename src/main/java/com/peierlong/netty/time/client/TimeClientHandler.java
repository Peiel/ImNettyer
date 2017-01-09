package com.peierlong.netty.time.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 包名: com.peierlong.netty.time.client
 * 创建人 : Elong
 * 时间: 2017/1/8 下午8:16
 * 描述 :
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    private int counter;
    private byte[] req;

    public TimeClientHandler() {
        req = ("当前时间是多少呢" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] readBytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(readBytes);
        String body = new String(readBytes, "UTF-8");
        System.out.println("当前时间是： " + body + "， counter : " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
