package com.atguigu.gmall0715.manage.mapper;

import com.atguigu.gamll0715.bean.SkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by lbin8521 on 2020/1/2.
 */
public interface SkuSaleAttrValueMapper  extends Mapper<SkuSaleAttrValue> {
    List<SkuSaleAttrValue> selectSkuSaleAttrValueListBySpu(String spuId);
}
