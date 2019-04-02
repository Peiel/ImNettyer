package com.peierlong.netty.time.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 包名: com.peierlong.netty.time.client
 * 创建人 : Elong
 * 时间: 2017/1/8 下午8:16
 * 描述 :
 */
public class TimeClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int counter;
    private byte[] req;

    public TimeClientHandler() {
        req = ("当前时间是多少呢" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead() msg: " + msg);
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("channelRead0() msg : " + msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }
}
