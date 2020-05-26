package com.github.liuhuagui.mybatis.auxiliary.modle;

import com.github.liuhuagui.mybatis.auxiliary.page.Limit;
import lombok.Getter;
import lombok.Setter;

/**
 * @author KK_SUN
 * @date 2020-05-25 21
 */
@Setter
@Getter
public abstract class BaseDO<T extends BaseDO<T>> extends Base<BaseDO<T>, T> {
    private Limit limit;

    public T limit(Limit limit) {
        this.setLimit(limit);
        return _this();
    }
}
