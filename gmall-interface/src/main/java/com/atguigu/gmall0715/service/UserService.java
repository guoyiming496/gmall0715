package com.atguigu.gmall0715.service;

import com.atguigu.gamll0715.bean.UserAddress;
import com.atguigu.gamll0715.bean.UserInfo;

import java.util.List;

/**
 * Created by lbin8521 on 2019/12/25.
 */
//业务层接口
public interface UserService {
    /**
     * 查询所有数据
     * @return
     */
    List<UserInfo> findAll();

    /**
     * 根据用户id查询用户地址列表
     * @param userId
     * @return
     */
    List<UserAddress> findUserAddressByUserId(String userId);

    /**
     * 登录
     * @param userInfo
     * @return
     */
    UserInfo login(UserInfo userInfo);

    /**
     * 解密token获取用户id
     * @param userId
     * @return
     */
    UserInfo verfiy(String userId);

}
