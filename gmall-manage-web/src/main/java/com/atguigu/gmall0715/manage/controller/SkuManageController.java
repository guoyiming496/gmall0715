package com.atguigu.gmall0715.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gamll0715.bean.SkuInfo;
import com.atguigu.gamll0715.bean.SkuLsInfo;
import com.atguigu.gamll0715.bean.SpuImage;
import com.atguigu.gamll0715.bean.SpuSaleAttr;
import com.atguigu.gmall0715.service.ListService;
import com.atguigu.gmall0715.service.ManageService;

import org.springframework.beans.BeanUtils;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lbin8521 on 2020/1/2.
 */
@RestController
@CrossOrigin
public class SkuManageController {

    @Reference
    private ManageService manageService;

    @Reference
    private ListService listService;

    // http://localhost:8082/spuImageList?spuId=60
    @RequestMapping("spuImageList")
    public List<SpuImage> getSpuImageList(String spuId,SpuImage spuImage){

        return manageService.getSpuImageList(spuImage);
    }
    // 通过spuId 查询销售属性-销售属性值
    // http://localhost:8082/spuSaleAttrList?spuId=6
    @RequestMapping("spuSaleAttrList")
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId){
        return manageService.getSpuSaleAttrList(spuId);
    }

    // http://localhost:8082/saveSkuInfo
    // json ---> javaObject
    @RequestMapping("saveSkuInfo")
    public void saveSkuInfo(@RequestBody SkuInfo skuInfo){
        manageService.saveSkuInfo(skuInfo);
        // 保存完成之后商品上架：
        // 发送消息队列异步处理！通知管理员做审核，审核成功之后，商品上架{saveSkuLsInfo}
    }
    // 如何上传？ 根据skuId 来上传
    // http://locahost:8082/onSale?skuId = 1009 单个上传！
    // http://locahost:8082/onSale?skuId = 10010 批量上传！
    @RequestMapping("onSale")
    public void onSale(String skuId){
        // 商品上架{saveSkuLsInfo}
        SkuLsInfo skuLsInfo = new SkuLsInfo();
        // 给skuLsInfo 初始化赋值
        // 根据skuId 查询skuInfo
        SkuInfo skuInfo = manageService.getSkuInfo(skuId);
        // 属性拷贝
        BeanUtils.copyProperties(skuInfo,skuLsInfo);
        listService.saveSkuLsInfo(skuLsInfo);
    }
}
