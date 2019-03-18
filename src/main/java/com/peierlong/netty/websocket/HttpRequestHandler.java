package com.peierlong.netty.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Peiel
 * @version V1.0
 * @date 2019-03-18
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    private static File INDEX;

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            String path = location.toURI() + "index.html";
            path = path.contains("file:") ? path.substring(5) : path;
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // 过去请求是升级了的 WebSocket 请求，则递增引用计数器并且将它传递给在 ChatServerInitializer 中的 ChanelInBoundHandler
        if (wsUri.equalsIgnoreCase(request.uri())) {
            ctx.fireChannelRead(request.retain());
        } else {
            // 处理符合 HTTP 1.1 的 100 continue 请求
            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }

            // 读取 index.html 中的内容
            RandomAccessFile file = new RandomAccessFile(INDEX, "r");
            DefaultHttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

            boolean keepAlive = HttpUtil.isKeepAlive(request);
            if (keepAlive) {
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderNames.KEEP_ALIVE);
            }
            ctx.writeAndFlush(response);

            if (ctx.pipeline().get(SslHandler.class) == null) {
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }

            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }

        }
    }

    private void send100Continue(ChannelHandlerContext ctx) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
