package com.atguigu.gmall0715.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gamll0715.bean.SkuInfo;
import com.atguigu.gamll0715.bean.SkuLsInfo;
import com.atguigu.gamll0715.bean.SpuImage;
import com.atguigu.gamll0715.bean.SpuSaleAttr;
import com.atguigu.gmall0715.service.ListService;
import com.atguigu.gmall0715.service.ManageService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lbin8521 on 2020/1/2.
 */
@RestController
@CrossOrigin
public class SkuManageController {
    @Reference
    private ListService listService;

    @Reference
    private ManageService manageService;
    // http://localhost:8082/spuImageList?spuId=60
    @RequestMapping("spuImageList")
    public List<SpuImage> getSpuImageList(String spuId,SpuImage spuImage){

        return manageService.getSpuImageList(spuImage);
    }
    // 通过spuId 查询销售属性-销售属性值
    // http://localhost:8082/spuSaleAttrList?spuId=6
    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<SpuSaleAttr> getspuSaleAttrList(String spuId){
        List<SpuSaleAttr> spuSaleAttrList = manageService.getSpuSaleAttrList(spuId);
        return  spuSaleAttrList;
    }
    // http://localhost:8082/saveSkuInfo
    // json ---> javaObject
    @RequestMapping("saveSkuInfo")
    public void saveSkuInfo(@RequestBody SkuInfo skuInfo){
        manageService.saveSkuInfo(skuInfo);
    }

    @RequestMapping("onSale")
    public  void onSale(String skuId){
        //商品上架
        SkuLsInfo skuLsInfo = new SkuLsInfo();
        //给skuLsinfo 初始化赋值
        //根据skuId查询skuinfo
        SkuInfo skuInfo = manageService.getSkuInfo(skuId);
        BeanUtils.copyProperties(skuInfo,skuLsInfo);
        listService.saveSkuInfo(skuLsInfo);
    }
}
