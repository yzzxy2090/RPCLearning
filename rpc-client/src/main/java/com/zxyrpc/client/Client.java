package com.zxyrpc.client;

import com.zxyrpc.model.UserInfo;
import com.zxyrpc.proxy.RPCProxy;
import com.zxyrpc.service.UserService;

//在客户端调用接口层的服务接口(实际上通过远程服务端的接口实现完成实质性处理)
public class Client {

    public static void main(String[] args) {

        final String remoteIp = "192.168.56.1";
        final Integer remotePort = 1234;

        RPCProxy rpcProxy = new RPCProxy();

        /**
         * 这里要拿到UserService接口的实现类——使用动态代理
         * 通过UserService接口的实现类的代理对象完成
         * 该代理对象通过rpcProxy.getProxy(UserService.class)拿到
         * 即这里的userService实际上是一个远程服务端的UserService的代理对象
         *
         * remoteIp为目标远程过程所属服务器的ip地址
         * remotePort为目标远程过程所属的进程的端口号
         */
        UserService userService = rpcProxy.getProxy(UserService.class, remoteIp, remotePort);

        /**
         * 这里getUserInfo()是另一个项目rpc_interface中的方法，即远程过程调用
         *
         * 实际实现时，调用getUserInfo()时会首先调用代理对象中RPCInvocationHandler实例的invoke方法
         */
        UserInfo userInfo = userService.getUserInfo();
        System.out.println("userID: " + userInfo.getUserId());
        System.out.println("userPhoneBrand: " + userInfo.getPhoneBrand());
    }
}