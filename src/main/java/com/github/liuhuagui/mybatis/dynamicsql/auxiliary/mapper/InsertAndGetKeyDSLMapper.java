package com.github.liuhuagui.mybatis.dynamicsql.auxiliary.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

/**
 * Insert and get generated key at the same time.
 * The *Mapper that requires the InsertAndGetKey function can inherit it directly.
 *
 * @author KaiKang 799600902@qq.com
 */
public interface InsertAndGetKeyDSLMapper<R> {
    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "record.id")
    int insertAndGetKey(InsertStatementProvider<R> insertStatement);
}
