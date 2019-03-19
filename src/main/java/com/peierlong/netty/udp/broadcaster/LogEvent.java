package com.peierlong.netty.udp.broadcaster;

import java.net.InetSocketAddress;

/**
 * @author Peiel
 * @version V1.0
 * @date 2019-03-19
 */
public class LogEvent {
    public static final byte SEPARATOR = ':';

    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    public LogEvent(String logfile, String msg) {
        this(null, logfile, msg, -1);
    }

    public LogEvent(InetSocketAddress source, String logfile, String msg, long received) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceivedTimestamp() {
        return received;
    }

}
