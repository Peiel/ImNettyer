package com.peierlong.netty.time.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;

/**
 * 包名: com.peierlong.netty.time.server
 * 创建人 : Elong
 * 时间: 2017/1/8 下午7:41
 * 描述 :
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
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

        String currentTime = Objects.equals(body, "当前时间是多少呢") ? new Date().toString() : "what ars you say?";

        ByteBuf responseBuf = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(responseBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
