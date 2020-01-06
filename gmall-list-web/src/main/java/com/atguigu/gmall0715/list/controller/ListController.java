package com.atguigu.gmall0715.list.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gamll0715.bean.SkuLsParams;
import com.atguigu.gamll0715.bean.SkuLsResult;
import com.atguigu.gmall0715.service.ListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lbin8521 on 2020/1/6.
 */
@Controller
public class ListController {
    @Reference
    private ListService listService;
    @RequestMapping("list.html")
    @ResponseBody
    public String list(SkuLsParams skuLsParams){
        SkuLsResult search = listService.search(skuLsParams);
        return JSON.toJSONString(search);
    }
}
