package com.github.liuhuagui.mybatis.auxiliary.page.query;

import com.github.liuhuagui.mybatis.auxiliary.model.BaseQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author liuhuagui
 */
@Getter
@Setter
public class IdentityQuery<T> extends BaseQuery {
    private T query;
}
