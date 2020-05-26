package com.github.liuhuagui.mybatis.auxiliary.modle;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DO（与数据表交互的对象）的基类
 *
 * @author KaiKang 799600902@qq.com
 */
@Getter
@Setter
public abstract class Base<T extends Base<T, S>, S extends T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 自增主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    protected abstract S _this();

    public S id(Long id) {
        this.setId(id);
        return _this();
    }

    public S gmtCreate(LocalDateTime gmtCreate) {
        this.setGmtCreate(gmtCreate);
        return _this();
    }

    public S gmtModified(LocalDateTime gmtModified) {
        this.setGmtModified(gmtModified);
        return _this();
    }
}
