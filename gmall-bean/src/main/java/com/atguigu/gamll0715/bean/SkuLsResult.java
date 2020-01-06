package com.atguigu.gamll0715.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lbin8521 on 2020/1/6.
 */
@Data
public class SkuLsResult implements Serializable {
    List<SkuLsInfo> skuLsInfoList;

    long total;

    long totalPages;

    List<String> attrValueIdList;
}
