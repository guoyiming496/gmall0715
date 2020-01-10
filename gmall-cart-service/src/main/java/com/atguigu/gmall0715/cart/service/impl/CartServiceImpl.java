package com.atguigu.gmall0715.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gamll0715.bean.CartInfo;
import com.atguigu.gamll0715.bean.SkuInfo;
import com.atguigu.gmall0715.cart.constant.CartConst;
import com.atguigu.gmall0715.cart.mapper.CartInfoMapper;
import com.atguigu.gmall0715.config.RedisUtil;
import com.atguigu.gmall0715.service.CartService;
import com.atguigu.gmall0715.service.ManageService;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lbin8521 on 2020/1/8.
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartInfoMapper cartInfoMapper;

    @Reference
    private ManageService manageService;

    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void addToCart(String skuId, String userId, Integer skuNum) {
     /*
     1.判断购物车中是否有要添加的该物品
     true：数量相加 mysql
     false： 直接添加数据 mysql
     2. 添加完成之后，必须更新redis
      */
     //获取redis
        Jedis jedis = redisUtil.getJedis();
     //数据类型
       String cartKey =  CartConst.USER_KEY_PREFIX+userId+CartConst.USER_CART_KEY_SUFFIX;
         if(!jedis.exists(cartKey)){
             loadCartCache(userId);
         }
//        CartInfo cartInfo = new CartInfo();
//////        cartInfo.setUserId(userId);
//////        cartInfo.setSkuId(skuId);
//////        CartInfo cartInfoExist = cartInfoMapper.selectOne(cartInfo);
        Example example = new Example(CartInfo.class);
        example.createCriteria().andEqualTo("userId",userId).andEqualTo("skuId",skuId);
        CartInfo cartInfoExist = cartInfoMapper.selectOneByExample(example);
        if(cartInfoExist!=null){
            //说明购物车中有该商品！ 数据应该相加
            cartInfoExist.setSkuNum(cartInfoExist.getSkuNum()+skuNum);
             //初始化skuPrice
              cartInfoExist.setSkuPrice(cartInfoExist.getCartPrice());
              //更新数据
              cartInfoMapper.updateByPrimaryKeySelective(cartInfoExist);
              //redis
//              jedis.hset(cartKey,skuId,JSON.toJSONString(cartInfoExist));
        }else {
            // 商品在数据库中不存在！
            SkuInfo skuInfo = manageService.getSkuInfo(skuId);
            CartInfo cartInfo1 = new CartInfo();

            cartInfo1.setSkuPrice(skuInfo.getPrice());
            cartInfo1.setCartPrice(skuInfo.getPrice());
            cartInfo1.setSkuNum(skuNum);
            cartInfo1.setSkuId(skuId);
            cartInfo1.setUserId(userId);
            cartInfo1.setImgUrl(skuInfo.getSkuDefaultImg());
            cartInfo1.setSkuName(skuInfo.getSkuName());
            // 添加到数据库！
            cartInfoMapper.insertSelective(cartInfo1);
            //redis
//            jedis.hset(cartKey,skuId,JSON.toJSONString(cartInfoExist));
            cartInfoExist = cartInfo1;
        }
        //redis
        jedis.hset(cartKey,skuId,JSON.toJSONString(cartInfoExist));
        //设置过期时间 ctrl+alt+m 抽取代码
        setCartKeyExpire(userId, jedis, cartKey);


        jedis.close();

    }

    @Override
    public List<CartInfo> getCartList(String userId) {
        /*
        1.先获取缓存中的数据
        1.true
             直接查询并返回集合
        2.false
        查询数据库，将数据放入缓存 并返回集合
        涉及到查询一下实时价格 ：skuinnfo.price
         */
        List<CartInfo> cartInfoList = new ArrayList<>();
        //获取redis
        Jedis jedis = redisUtil.getJedis();
        //数据类型
        String cartKey =  CartConst.USER_KEY_PREFIX+userId+CartConst.USER_CART_KEY_SUFFIX;
        //hash获取数据
        List<String> stringList = jedis.hvals(cartKey);
         if(stringList!=null&&stringList.size()>0){
             //循环遍历数据
             for (String cartInfoJson : stringList) {
                 //将缓存中的cartIfo添加到集合里
              cartInfoList.add(JSON.parseObject(cartInfoJson,CartInfo.class));
             }
               cartInfoList.sort(new Comparator<CartInfo>() {
                   @Override
                   public int compare(CartInfo o1, CartInfo o2) {
                       return o1.getId().compareTo(o2.getId());
                   }
               });
             return cartInfoList;
        }else{
             //走db放入缓存
             cartInfoList = loadCartCache(userId);
             return cartInfoList;
         }
    }
        //根据用户id查询数据库并放入缓存
    private List<CartInfo> loadCartCache(String userId) {
         //查询最新数据给缓存  商品价格 skuinfo.price
        List<CartInfo> cartInfoList =   cartInfoMapper.selectCartListWithCurPrice(userId);
        if(cartInfoList==null || cartInfoList.size()==0){
            return null;
        }
              //循环遍历数据并将数据添加到缓存
        //获取redis
        Jedis jedis = redisUtil.getJedis();
        //数据类型
        String cartKey =  CartConst.USER_KEY_PREFIX+userId+CartConst.USER_CART_KEY_SUFFIX;
        HashMap<String, String> map = new HashMap<>();
        for (CartInfo cartInfo : cartInfoList) {
              map.put(cartInfo.getSkuId(),JSON.toJSONString(cartInfo));
        }
        jedis.hmset(cartKey,map);
        jedis.close();
        return cartInfoList;
    }

    private void setCartKeyExpire(String userId, Jedis jedis, String cartKey) {
        //设置过期时间
        //获取用户的key
        String userKey = CartConst.USER_KEY_PREFIX+userId+CartConst.USERINFOKEY_SUFFIX;
        if(jedis.exists(userKey)){
            //获取用户key的过期时间
            Long ttl = jedis.ttl(userKey);
            //将用户的过期时间给购物车的过期时间
            jedis.expire(cartKey,ttl.intValue());
        }else {
            jedis.expire(cartKey,7*24*3600);
        }
    }
}
