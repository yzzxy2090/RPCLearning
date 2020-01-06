package com.zxyrpc.request;

import java.io.Serializable;

/**
 * RPCRequest用于封装对远程过程的请求并通过RPCRequest对象实现数据的网络传输
 */

public class RPCRequest implements Serializable {

    //客户端所需要调用的过程所属类的类名
    private String className;

    //户端所需要调用的过程方法名
    private String methodName;

    //参数列表
    private Object[] parameters;

    public RPCRequest(String className, String methodName, Object[] parameters) {
        this.className = className;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
