package com.zxyrpc.handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * target为客户端想要调用的远程实现类对象，这里即为com.zxyrpc.service.implement.UserServiceImpl
 */
public class ServerHandler {

    //服务器端对于接收请求来说只需要知道请求所指定的端口号以及该请求指定的实现类
    public void handle(Object target, Integer port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            /**
             * 服务端serverSocket一直监听是否有向当前端口（port）发送的请求
             * accept()该方法将一直等待，直到客户端连接到服务器上给定的端口(port)
             * 然后会创建一个新的Socket对象s，如果有安全管理器，则使用s.getInetAddress(), getHostAddress()和s.getPort()作为其参数来调用安全管理器的checkAccept方法，以确保允许该操作. 这可能会导致SecurityException。
             * accept()方法最终返回值是这个新创建的s
             *
             * accept()所处线程会被阻塞，直到连接队列中有了新连接，线程再被唤醒
             * 然后将连接的信息封装到一个SocketImpl对象中，再将此SocketImpl对象封装到一个Socket对象中
             * 最后将此Socket对象返回给应用程序使用
             *
             * 其中抽象类SocketImpl是实际实现套接字的所有类的通用超类，创建客户端和服务器套接字都可以使用它
             *
             * 与客户端建立连接后就开启一个线程运行对该请求的处理过程ServiceProcessor()
             */
            while(true) {
                Socket acceptSocket = serverSocket.accept();

                Thread thread = new Thread(new ServiceProcessor(acceptSocket, target));
                thread.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
