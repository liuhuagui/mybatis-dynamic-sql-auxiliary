package com.github.liuhuagui.mybatis.auxiliary.page;

import lombok.Getter;
import lombok.Setter;

/**
 * @author KK_SUN
 * @date 2020-05-25 20
 */
@Getter
@Setter
public class PageDO<T> extends Page {
    private T dataO;
}
