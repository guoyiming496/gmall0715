package com.atguigu.gamll0715.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lbin8521 on 2020/1/6.
 */
@Data
public class SkuLsParams implements Serializable {
    String  keyword;

    String catalog3Id;

    String[] valueId;

    int pageNo=1;

    int pageSize=20;
}
