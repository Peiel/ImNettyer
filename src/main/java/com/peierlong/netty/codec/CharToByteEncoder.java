package com.peierlong.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * @author Peiel
 * @version V1.0
 * @date 2019-03-15
 */
public class CharToByteEncoder extends MessageToByteEncoder<Character> {


    protected void encode(ChannelHandlerContext ctx, Character msg, ByteBuf out) throws Exception {
        out.writeChar(msg);
    }
}
