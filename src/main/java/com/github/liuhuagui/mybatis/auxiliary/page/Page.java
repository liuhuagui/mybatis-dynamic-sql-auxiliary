package com.github.liuhuagui.mybatis.auxiliary.page;

import lombok.Getter;
import lombok.Setter;

/**
 * 存放物理分页参数 —— offset、limit或 fetch first
 *
 * @author KaiKang 799600902@qq.com
 */
@Setter
@Getter
public class Page extends Order {
    /**
     * 页码
     */
    private Long pageNo;
    /**
     * 页长
     */
    private Long pageSize;
}
