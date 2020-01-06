package com.zxyrpc.invoke;

import com.zxyrpc.request.RPCRequest;

import java.io.*;
import java.net.Socket;

/**
 * rpc对远程服务端的调用
 */

public class RPCInvoke {

    private String remoteIp;

    private Integer remotePort;

    public RPCInvoke(String remoteIp, Integer remotePort) {
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
    }

    //将rpcRequest作为参数向远端服务端发起调用，返回调用结果类型设为Object
    public Object invoke(RPCRequest rpcRequest) {

        try {
            //与远端服务器建立网络连接
            Socket socket = new Socket(remoteIp, remotePort);

            //创建一个输出流向远端服务器传送相关数据
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();

            //将相关数据都写出去之后要获取远程服务器的相应数据
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object readObject = objectInputStream.readObject();

            inputStream.close();
            outputStream.close();
            socket.close();

            return readObject;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
