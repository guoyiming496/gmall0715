package com.atguigu.gamll0715.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuInfo implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    String id;

    @Column
    String spuId;

    @Column
    BigDecimal price;

    @Column
    String skuName;

    @Column
    BigDecimal weight;

    @Column
    String skuDesc;

    @Column
    String catalog3Id;

    @Column
    String skuDefaultImg;

    // 图片列表集合
    @Transient
    List<SkuImage> skuImageList;
    // sku与平台属性集合
    @Transient
    List<SkuAttrValue> skuAttrValueList;
    // sku与销售属性集合
    @Transient
    List<SkuSaleAttrValue> skuSaleAttrValueList;

}
