package com.github.liuhuagui.mybatis.auxiliary.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author liuhuagui
 */
@Setter
@Getter
public abstract class Page implements Serializable {
    /**
     * 页码
     */
    private Long pageNo;
    /**
     * 页长
     */
    private Long pageSize;

    /**
     * 排序规则 —— 根据数值自定义
     */
    private Integer order;
}
