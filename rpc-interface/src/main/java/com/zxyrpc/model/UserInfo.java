package com.zxyrpc.model;

import java.io.Serializable;

//这里的UserInfo对象需要在网络中进行传输，需要支持序列化
public class UserInfo implements Serializable {

    private Integer userId;
    private String phoneBrand;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }
}
