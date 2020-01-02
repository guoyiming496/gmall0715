package com.atguigu.gmall0715.manage.mapper;

import com.atguigu.gamll0715.bean.SpuSaleAttr;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by lbin8521 on 2019/12/30.
 */
public interface SpuSaleAttrMapper  extends Mapper<SpuSaleAttr> {
    public List<SpuSaleAttr> selectSpuSaleAttrList(String spuId);

    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(String id, String spuId);
}
