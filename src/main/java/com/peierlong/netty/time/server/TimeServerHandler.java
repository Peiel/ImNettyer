package com.peierlong.netty.time.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * 包名: com.peierlong.netty.time.server
 * 创建人 : Elong
 * 时间: 2017/1/8 下午7:41
 * 描述 :
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = null;
        try {
            body = new String(req, "UTF-8").substring(0, new String(req, "UTF-8").length() - System.getProperty("line.separator").length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("收到客户端的请求， 请求内容: " + body + " counter : " + ++counter);

        String currentTime = "当前时间是多少呢".equals(body) ? new Date().toString() : "what ars you say?";

        ByteBuf responseBuf = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(responseBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
