package com.github.liuhuagui.mybatis.dynamicsql.auxiliary.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 存放物理分页参数 —— offset、limit或 fetch first
 *
 * @author liuhuagui
 * @date 2020-05-25 21
 */
@Getter
@AllArgsConstructor
public class Limit {
    /**
     * offset
     */
    private Long offset;
    /**
     * limit或 fetch first
     */
    private Long size;
}
