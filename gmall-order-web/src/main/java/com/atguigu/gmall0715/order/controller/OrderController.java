package com.atguigu.gmall0715.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.atguigu.gamll0715.bean.UserAddress;
import com.atguigu.gmall0715.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

//    @Autowired
    @Reference
    private UserService userService;
    // 根据用户Id 查询收货地址列表！

    // http://localhost:8081/trade?userId=1
    @RequestMapping("trade")
    public List<UserAddress> trade(String userId){
        return userService.findUserAddressByUserId(userId);
    }
}
