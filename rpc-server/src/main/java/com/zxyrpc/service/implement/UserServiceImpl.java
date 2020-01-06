package com.zxyrpc.service.implement;

import com.zxyrpc.model.UserInfo;
import com.zxyrpc.service.UserService;

//服务器端对接口层服务接口的具体实现
//需要在服务端项目pom文件导入对接口层的依赖以引入UserService这个接口
public class UserServiceImpl implements UserService {

    @Override
    public UserInfo getUserInfo() {
        //这里在现实环境中应该是对服务器端连接的数据库的增删改查操作
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1);
        userInfo.setPhoneBrand("23333333333");

        return userInfo;
    }

}