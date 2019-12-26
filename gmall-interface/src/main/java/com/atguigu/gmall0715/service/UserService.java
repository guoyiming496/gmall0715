package com.atguigu.gmall0715.service;

import com.atguigu.gamll0715.bean.UserAddress;
import com.atguigu.gamll0715.bean.UserInfo;

import java.util.List;

/**
 * Created by lbin8521 on 2019/12/25.
 */
//业务层接口
public interface UserService {
    List<UserInfo> findAll();
    List<UserAddress> findUserAddressByUserId(String userId);
}
