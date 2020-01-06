package com.zxyrpc.handler;

import com.zxyrpc.request.RPCRequest;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Runnable接口应该由那些打算通过某一线程执行其实例的类来实现, 类必须定义一个名为run的无参数方法。
 * 实现了Runnable接口，ServiceProcessor的实例就可以通过线程执行
 */
public class ServiceProcessor implements Runnable {

    private Socket acceptSocket;

    private Object target;

    public ServiceProcessor(Socket acceptSocket, Object target) {
        this.acceptSocket = acceptSocket;
        this.target = target;
    }

    @Override
    public void run() {
        try {
            /**
             * 通过acceptSocket得到网络请求的输入流
             * 并通过ObjectInputStream的readObject()方法相关数据反序列化为RPCRequest对象
             */
            InputStream inputStream = acceptSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            RPCRequest rpcRequest = (RPCRequest) objectInputStream.readObject();

            /**
             * 针对拿到的请求发起请求处理过程invokeTargetProcess(rpcRequest)
             * 返回值为要发送给客户端的调用结果对象
             * 这里resultObject其实就是UserInfo对象
             */
            Object resultObject = invokeTargetProcess(rpcRequest);

            /**
             * 处理完成后通过acceptSocket向客户端发送调用结果
             * 将调用结果对象序列化后向客户端传输
             */
            OutputStream outputStream = acceptSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(resultObject);
            objectOutputStream.flush();

            inputStream.close();
            objectInputStream.close();

            outputStream.close();
            objectOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object invokeTargetProcess(RPCRequest rpcRequest) {

        String methodName = rpcRequest.getMethodName();
        Object[] parameters = rpcRequest.getParameters();

        Class[] parameterTypes = new Class[parameters.length];
        for(int i=0; i<parameters.length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }

        //Method为java.lang.reflect.Method
        Method method = null;
        try {
            /**
             * 通过target.getClass().getDeclaredMethod(methodName, parameterTypes)得到目标方法
             * parameterTypes为一个Class数组，表示参数列表中各参数的类型
             */
            method = target.getClass().getDeclaredMethod(methodName, parameterTypes);

            /**
             * 反射调用实现方法
             * invoke为Method的方法，实现对指定方法的反射调用
             *
             * method代表getUserInfo()方法的反射方法信息，即method是getUserInfo()的一个反射对象
             *
             * public Object invoke(Object obj, Object... args)
             * invoke参数列表中obj指要调用方法的目标实现类，在这里即为com.zxyrpc.service.implement.UserServiceImpl
             * args指调用该方法所需要的参数，为一个对象数组类型，这里就是指getUserInfo()的参数列表，为null
             *
             * method.invoke(target, parameters)就相当于userServiceImpl.getUserInfo(parameters)
             * userServiceImpl是target，即实现类的一个对象
             */
            Object invokeResult = method.invoke(target, parameters);
            return invokeResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
