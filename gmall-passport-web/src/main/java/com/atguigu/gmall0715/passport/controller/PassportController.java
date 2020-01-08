package com.atguigu.gmall0715.passport.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gamll0715.bean.UserInfo;
import com.atguigu.gmall0715.passport.config.JwtUtil;
import com.atguigu.gmall0715.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lbin8521 on 2020/1/7.
 */
@Controller
public class PassportController {
    @Value("${token.key}")
    private String key;

    @Reference
    private UserService userService;
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        String originUrl = request.getParameter("originUrl");
        // 保存上
        request.setAttribute("originUrl",originUrl);
        return "index";
    }
     //如何得到表单提交的数据
    @RequestMapping("login")
    @ResponseBody
    public String login (UserInfo userInfo,HttpServletRequest request){
        //调用服务层
      UserInfo info =   userService.login(userInfo);
        if(info!=null){
            //data == token
            //参数
//             String key = "token.key";
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId",info.getId());
            map.put("nickName",info.getNickName());
            //服务器ip地址
            String salt = request.getHeader("X-forwarded-for");
//            String salt = "192.168.67.123";
             String token = JwtUtil.encode(key,map,salt);
            return token;
        }
     return "fail";
    }

    @RequestMapping("verify")
    @ResponseBody
    public String verify(HttpServletRequest request){
        //从token中获取userid
        String token = request.getParameter("token");
        String salt = request.getParameter("salt");
        Map<String, Object> map = JwtUtil.decode(token, key, salt);
        //判断map
        if(map!=null&&map.size()>0){
            //从token中解密出来userid
            String userId = (String) map.get("userId");
            //调用服务层
            UserInfo userInfo = userService.verfiy(userId);
            if(userInfo!=null){
                return "success";
            }
        }
        return "fail";
    }
}
