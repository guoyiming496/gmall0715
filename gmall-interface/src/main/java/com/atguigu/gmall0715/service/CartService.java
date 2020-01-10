package com.atguigu.gmall0715.service;

import com.atguigu.gamll0715.bean.CartInfo;

import java.util.List;

/**
 * Created by lbin8521 on 2020/1/8.
 */
public interface CartService {
    //添加购物车
    void addToCart(String skuId,String userId,Integer skuNum);

    //根据用户id查询购物车列表
    List<CartInfo> getCartList(String userId);

}
