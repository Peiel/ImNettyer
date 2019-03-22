package com.peierlong.netty;

import java.nio.channels.SelectionKey;

/**
 * @author Peiel
 * @version V1.0
 * @date 2019-03-22
 */
public class myTest {

    public static void main(String[] args) {
        System.out.println(SelectionKey.OP_READ);
        System.out.println(SelectionKey.OP_WRITE);
        System.out.println(SelectionKey.OP_CONNECT);
        System.out.println(SelectionKey.OP_ACCEPT);



    }

}
