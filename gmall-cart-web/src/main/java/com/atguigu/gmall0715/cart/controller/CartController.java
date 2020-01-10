package com.atguigu.gmall0715.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gamll0715.bean.CartInfo;
import com.atguigu.gamll0715.bean.SkuInfo;
import com.atguigu.gmall0715.config.CookieUtil;
import com.atguigu.gmall0715.config.LoginRequire;
import com.atguigu.gmall0715.service.CartService;
import com.atguigu.gmall0715.service.ManageService;
import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lbin8521 on 2020/1/8.
 */
@Controller
public class CartController {
    @Reference
    private CartService cartService;

    @Reference
    private ManageService manageService;

    @RequestMapping("addToCart")
    @LoginRequire(autoRedirect = false)
     public String addToCart(HttpServletRequest request, HttpServletResponse response){
       //得到前台传过来的数据
        String skuNum = request.getParameter("skuNum");
        String skuId = request.getParameter("skuId");
        //获取用户id
        String userId = (String) request.getAttribute("userId");
        //如何判断用户是否登录
        if(userId==null){
            //用户未登录 存储一个临时的用户id，存储在cookie中！
           userId =  CookieUtil.getCookieValue(request,"user-key",false);

           //说明未登录的情况下，根本没有添加一件商品
         if(userId==null){
             //起一个临时userId，放入cookie中
             userId = UUID.randomUUID().toString().replace("-","");
          CookieUtil.setCookie(request,response,"user-key",userId,7*24*3600,false);
         }
        }
           //添加购物车
          cartService.addToCart(skuId,userId,Integer.parseInt(skuNum));
        //页面渲染使用！
        SkuInfo skuInfo = manageService.getSkuInfo(skuId);
        request.setAttribute("skuInfo",skuInfo);
        request.setAttribute("skuNum",skuNum);
        return "success";
    }

    @RequestMapping("cartList")
    @LoginRequire(autoRedirect = false)
    public String cartList(HttpServletRequest request){
        List<CartInfo> cartInfoList =new  ArrayList<>();
        //获取用户id
        String userId = (String) request.getAttribute("userId");
        //登录获取数据的时候，根据userid查询购物车列表
         if(userId!=null){
            cartInfoList =  cartService.getCartList(userId);
         }else{
             //未登录获取临时用户id查询数据  临时用户啊保存在cookie中
           String userTempId =   CookieUtil.getCookieValue(request,"user-key",false);
           //获取未登录购物车数据
           if(userTempId!=null){
               cartInfoList = cartService.getCartList(userTempId);
           }
         }
        request.setAttribute("cartInfoList",cartInfoList);
        return "cartList";
    }
}
