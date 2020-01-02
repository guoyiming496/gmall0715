package com.atguigu.gmall0715.manage.mapper;

import com.atguigu.gamll0715.bean.BaseAttrInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by lbin8521 on 2019/12/27.
 */
public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {
    // 通过三级分类Id 查询
    List<BaseAttrInfo> selectBaseAttrInfoListByCatalog3Id(String catalog3Id);
}
