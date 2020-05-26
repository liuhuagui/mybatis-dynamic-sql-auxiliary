package com.github.liuhuagui.mybatis.auxiliary.mapper;


import java.util.List;

/**
 * Base Mapper with common methods and default paging methods <code>count()、list()</code>
 *
 * @param <R> record type
 * @param <I> primary key type
 * @author KaiKang 799600902@qq.com
 */
public interface BaseMapper<R, I> {
    long count(R record);

    List<R> list(R record);
}
