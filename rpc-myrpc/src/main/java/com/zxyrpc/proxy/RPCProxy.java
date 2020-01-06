package com.zxyrpc.proxy;

import java.lang.reflect.Proxy;

/**
 * RPC代理类
 */
public class RPCProxy {

    //classInterfaces为提供服务的目标类的类型
    public <T> T getProxy(Class<T> classInterfaces, String remoteIp, Integer remotePort) {

        /**
         * 使用jdk的动态代理java.lang.reflect.Proxy来实现
         *
         * 通过Proxy的newProxyInstance获得动态代理的实例
         * public static Object newProxyInstance(ClassLoader loader,
         *                                       Class<?>[] interfaces,
         *                                       InvocationHandler h)
         *
         * ClassLoader为类加载器，即getProxy参数列表中classInterfaces的类加载器
         * interfaces为一个Class数组，因为参数列表可能传入多个接口
         * InvocationHandler是java.lang.reflect.InvocationHandler，是一个接口
         *      只有一个方法为public Object invoke(Object proxy, Method method, Object[] args)
         */

        return (T)Proxy.newProxyInstance(classInterfaces.getClassLoader(),
                new Class<?>[] {classInterfaces},
                new RPCInvokecationHandler(remoteIp, remotePort));
    }

}
