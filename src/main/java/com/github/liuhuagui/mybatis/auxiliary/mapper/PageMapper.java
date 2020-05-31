package com.github.liuhuagui.mybatis.auxiliary.mapper;


import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;

import java.util.List;

/**
 * Base Mapper with common methods and default paging methods <code>count()、list()</code>
 *
 * @param <R> record type
 * @author KaiKang 799600902@qq.com
 */
public interface PageMapper<R> {
    long count(CountDSLCompleter completer);

    List<R> select(SelectDSLCompleter completer);
}
