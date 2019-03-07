package com.peierlong.netty.transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author Peiel
 * @version V1.0
 * @date 2019-03-07
 */
public class PlainOioServer {

    public static void server(int port) throws Exception {
        ServerSocket socket = new ServerSocket(port);
        try {
            while (true) {
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            InputStream in = clientSocket.getInputStream();

                            byte[] bytes = new byte[8];
                            int readSize;
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            while ((readSize = in.read(bytes)) > 0) {
                                byteArrayOutputStream.write(bytes, 0, readSize);
                            }
                            System.out.println(byteArrayOutputStream.toString("UTF-8"));

                            out = clientSocket.getOutputStream();
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        server(8800);
    }


}
