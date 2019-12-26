package com.atguigu.gmall0715.service;

import com.atguigu.gamll0715.bean.UserAddress;

import java.util.List;

/**
 * Created by lbin8521 on 2019/12/26.
 */
public interface UserAddressService {
    /**
     * 根据用户id查询用户地址列表
     */
    List<UserAddress> findUserAddressByUserId(String userId);
}
