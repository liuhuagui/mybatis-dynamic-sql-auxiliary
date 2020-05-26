package com.github.liuhuagui.mybatis.auxiliary.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author KK_SUN
 * @date 2020-05-25 21
 */
@Setter
@Getter
public class Order implements Serializable {
    /**
     * 排序规则 —— 根据数值自定义
     */
    private Integer order;
}
