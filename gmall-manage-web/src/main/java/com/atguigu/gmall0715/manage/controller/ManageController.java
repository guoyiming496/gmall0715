package com.atguigu.gmall0715.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gamll0715.bean.*;
import com.atguigu.gmall0715.service.ManageService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lbin8521 on 2019/12/27.
 */
@RestController
@CrossOrigin

public class ManageController {
    @Reference
    private ManageService manageService;

    //返回所有的一级分类数据 getCatalog1
    //http://localhost:8082/getCatalog1
    @RequestMapping("getCatalog1")
    public List<BaseCatalog1> getCatalog1(){
        //select * from basecatalog1
        return manageService.getCatalog1();
    }

    //http://localhost:8082/getCatalog2?catalog1Id=2
    @RequestMapping("getCatalog2")
    public List<BaseCatalog2> getCatalog2(String catalogId,BaseCatalog2 baseCatalog2){
        //select * from basecatalog2 where catalog1Id=?
       return manageService.getCatalog2(baseCatalog2);
    }


    //http://localhost:8082/getCatalog3?catalog2Id=13
    @RequestMapping("getCatalog3")
    public List<BaseCatalog3> getCatalog3(String catalog2Id,BaseCatalog3 baseCatalog3){
        //select * from basecatalog2 where catalog1Id=?
        return manageService.getCatalog3(baseCatalog3);
    }

    //http://localhost:8082/attrInfoList?catalog3Id=61
    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> attrInfoList(String catalog3Id,BaseAttrInfo baseAttrInfo){
    return  manageService.getAttrInfoList(baseAttrInfo);
    }


    //http://localhost:8082/saveAttrInfo
    //必须接受前台数据  1.平台属性名称  2.平台属性值名称   3.三级分类ad
    //页面传递数据是json，后台接受对象为java的 object  需要数据类型转换  json--Object @requestBody      object--json @ReponseBody
     @RequestMapping("saveAttrInfo")
      public void saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
          //调用服务层
         manageService.saveAttrInfo(baseAttrInfo);
     }

      //http://localhost:8082/getAttrValueList?attrId=102
     @RequestMapping("getAttrValueList")
    public  List<BaseAttrValue> getAttrValueList(String attrId){
        //功能来讲：
//       return manageService.getAttrValueList(attrId);
         //业务来讲 先查询baseAttrinfo
        BaseAttrInfo baseAttrInfo =  manageService.getBaseAttrInfo(attrId);
        if(baseAttrInfo==null){
            return null;
        }else {
        return baseAttrInfo.getAttrValueList();
     }
    }


}
