package com.peierlong.netty.codec;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author Peiel
 * @version V1.0
 * @date 2019-03-15
 */
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {

    public CombinedByteCharCodec() {
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
