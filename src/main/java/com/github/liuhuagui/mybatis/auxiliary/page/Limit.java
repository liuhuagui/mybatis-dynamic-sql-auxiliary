package com.github.liuhuagui.mybatis.auxiliary.page;

import com.github.liuhuagui.mybatis.auxiliary.page.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author KK_SUN
 * @date 2020-05-25 21
 */
@Getter
@AllArgsConstructor
public class Limit extends Order {
    /**
     * offset
     */
    private Long offset;
    /**
     * limit或 fetch first
     */
    private Long size;
}
