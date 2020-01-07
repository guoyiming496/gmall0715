package com.atguigu.gmall0715.service;

import com.atguigu.gamll0715.bean.SkuLsInfo;
import com.atguigu.gamll0715.bean.SkuLsParams;
import com.atguigu.gamll0715.bean.SkuLsResult;

/**
 * Created by lbin8521 on 2020/1/6.
 */
public interface ListService {
    /**
     * 商品上架
     * @param skuLsInfo
     */
    void saveSkuLsInfo(SkuLsInfo skuLsInfo);

    /**
     * 根据用户输入的检索条件查询数据
     * @param skuLsParams
     * @return
     */
    SkuLsResult search(SkuLsParams skuLsParams);

    /**
     * 更新es的hotScore
     * @param skuId
     */
    void incrHotScore(String skuId);
}
