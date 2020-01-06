package com.zxyrpc.service;

import com.zxyrpc.model.UserInfo;

//客户端通过这个UserService接口去调用服务器上的UserService实现类的相关方法
public interface UserService {
    UserInfo getUserInfo();
}
