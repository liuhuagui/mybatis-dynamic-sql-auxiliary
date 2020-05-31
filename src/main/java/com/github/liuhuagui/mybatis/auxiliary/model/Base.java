package com.github.liuhuagui.mybatis.auxiliary.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author liuhuagui
 */
@Getter
@Setter
public abstract class Base implements Serializable {
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
}
