package com.zxyrpc.proxy;

import com.zxyrpc.invoke.RPCInvoke;
import com.zxyrpc.request.RPCRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RPCInvokecationHandler implements InvocationHandler {

    private String remoteIp;
    private Integer remotePort;

    public RPCInvokecationHandler(String remoteIp, Integer remotePort) {
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
    }

    /**
     * 通过invoke方法实现对远程方法的调用
     *
     * @param proxy --> 代理类，也就是UserService的代理类
     * @param method --> getUserInfo()
     * @param args --> getUserInfo()方法所需要的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //实现对远程方法的调用
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Object[] parameters = args == null ? new Object[]{} : args;

        //将远程调用所需要的信息封装为rpcRequest
        RPCRequest rpcRequest = new RPCRequest(className, methodName, parameters);

        //接下来要将rpcRequest作为参数向远程服务端()发起过程调用
        RPCInvoke rpcInvoke = new RPCInvoke(remoteIp, remotePort);
        Object invokeResult = rpcInvoke.invoke(rpcRequest);

        return invokeResult;
    }
}
