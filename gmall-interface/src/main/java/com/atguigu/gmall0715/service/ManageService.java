package com.atguigu.gmall0715.service;

import com.atguigu.gamll0715.bean.*;

import java.util.List;

/**
 * Created by lbin8521 on 2019/12/27.
 */
public interface ManageService {
    /**
     * 表示查询一级分类的数据
     * @return
     */
    List<BaseCatalog1> getCatalog1();

    /**
     * 通过二级分类id查询二级分类数据
     * @param catalog1Id
     * @return
     */
    List<BaseCatalog2> getCatalog2(String catalog1Id);

    /**
     * 通过二级分类对象来查询数据
     * @param baseCatalog2
     * @return
     */
    List<BaseCatalog2> getCatalog2(BaseCatalog2 baseCatalog2);

    /**
     * 通过三级分类属性来查询诗句
     * @param baseCatalog3
     * @return
     */

    List<BaseCatalog3> getCatalog3(BaseCatalog3 baseCatalog3);


    /**
     * 通过三级分类id查询数据
     * @param baseAttrInfo
     * @return
     */
    List<BaseAttrInfo>getAttrInfoList(BaseAttrInfo baseAttrInfo);

    /**
     * 保存平台属性  平台属性值
     * @param baseAttrInfo
     */
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    /**
     * 根据attrid  回显数据
     * @param attrId
     * @return
     */
    List<BaseAttrValue> getAttrValueList(String attrId);

    /**
     * 通过attrid 查询baseattr info
     * @param attrId
     * @return
     */
    BaseAttrInfo getBaseAttrInfo(String attrId);

}
