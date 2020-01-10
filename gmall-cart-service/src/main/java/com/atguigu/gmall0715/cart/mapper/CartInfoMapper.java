package com.atguigu.gmall0715.cart.mapper;

import com.atguigu.gamll0715.bean.CartInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by lbin8521 on 2020/1/8.
 */
public interface CartInfoMapper extends Mapper<CartInfo> {
    //根据用户id查询购物车数据
    List<CartInfo> selectCartListWithCurPrice(String userId);

}
