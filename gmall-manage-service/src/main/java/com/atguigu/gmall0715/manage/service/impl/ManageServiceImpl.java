package com.atguigu.gmall0715.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gamll0715.bean.*;
import com.atguigu.gmall0715.manage.mapper.*;
import com.atguigu.gmall0715.service.ManageService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lbin8521 on 2019/12/27.
 */
@Service
public class ManageServiceImpl implements ManageService {
    //调用mapper
    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Resource
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired

   private  SpuSaleAttrValueMapper spuSaleAttrValueMapper;


    @Override
    public List<BaseCatalog1> getCatalog1() {
        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        //select * from basecatalog2 where catalog1Id=?
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog2> getCatalog2(BaseCatalog2 baseCatalog2) {
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3(BaseCatalog3 baseCatalog3) {
        return baseCatalog3Mapper.select(baseCatalog3);
    }

    @Override
    public List<BaseAttrInfo> getAttrInfoList(BaseAttrInfo baseAttrInfo) {
        return baseAttrInfoMapper.select(baseAttrInfo);
    }

    @Override
    @Transactional
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //baseAttrInfo  baseAttrValue
        //判斷是否为空
        if (baseAttrInfo.getId() != null && baseAttrInfo.getId().length() > 0) {
            //修改
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        } else {
            //直接保存平台属性
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        //baseAttrValue 修改
        //先刪除 再修改
        BaseAttrValue baseAttrValue1 = new BaseAttrValue();
        baseAttrValue1.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue1);


        //保存平台属性值
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        //判断集合不为空！
        //先判断对象不为空 然后再判断集合长度
        if (attrValueList != null && attrValueList.size() > 0) {
            for (BaseAttrValue baseAttrValue : attrValueList) {
                baseAttrValue.setAttrId(baseAttrInfo.getId());//获取当前对象主键自增值
                //保存值到数据库
                baseAttrValueMapper.insertSelective(baseAttrValue);
            }
        }
    }

    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
        return baseAttrValueMapper.select(baseAttrValue);
    }

    @Override
    public BaseAttrInfo getBaseAttrInfo(String attrId) {
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);
        //查询平台属性值集合
        baseAttrInfo.setAttrValueList(getAttrValueList(attrId));
        return baseAttrInfo;
    }

    @Override
    public List<SpuInfo> getSpuInfoList(String catalog3Id) {
        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        return spuInfoMapper.select(spuInfo);
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    @Override
    @Transactional
    public void saveSpuInfo(SpuInfo spuInfo) {
 /*
            spuInfo
            spuImage
            spuSaleAttr
            spuSaleAttrValue
         */
        spuInfoMapper.insertSelective(spuInfo);
        // spuImage
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if (spuImageList!=null && spuImageList.size()>0){
            for (SpuImage spuImage : spuImageList) {
                // 赋值spuId
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insertSelective(spuImage);
            }
        }
        // 销售属性
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if (spuSaleAttrList!=null && spuSaleAttrList.size()>0){
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insertSelective(spuSaleAttr);

                // 销售属性值
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                if (spuSaleAttrValueList!=null && spuSaleAttrValueList.size()>0){
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                    }
                }
            }
        }
    }
}
