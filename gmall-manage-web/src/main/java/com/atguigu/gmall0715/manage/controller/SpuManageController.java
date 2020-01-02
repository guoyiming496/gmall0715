package com.atguigu.gmall0715.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gamll0715.bean.BaseSaleAttr;
import com.atguigu.gamll0715.bean.SpuImage;
import com.atguigu.gamll0715.bean.SpuInfo;
import com.atguigu.gmall0715.service.ManageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lbin8521 on 2019/12/30.
 */
@RestController
@CrossOrigin
public class SpuManageController {
    @Reference
    private ManageService manageService;

    @RequestMapping("spuList")
    public List<SpuInfo> getspuInfoList(String catalog3Id) {
        return manageService.getSpuInfoList(catalog3Id);
    }


    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return manageService.getBaseSaleAttrList();
    }

    @RequestMapping("saveSpuInfo")
    public  void saveSpuInfo(@RequestBody SpuInfo spuInfo){
        manageService.saveSpuInfo(spuInfo);
    }




}