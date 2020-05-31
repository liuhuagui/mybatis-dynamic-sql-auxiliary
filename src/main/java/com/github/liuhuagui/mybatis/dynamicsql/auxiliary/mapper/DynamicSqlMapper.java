package com.github.liuhuagui.mybatis.dynamicsql.auxiliary.mapper;


import com.github.liuhuagui.mybatis.dynamicsql.auxiliary.util.DynamicSqlUtil;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.InsertSelectDSL;
import org.mybatis.dynamic.sql.insert.render.InsertSelectStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

/**
 * Base Mapper is used to support dynamic sql.
 *
 * @param <R> record type
 * @author KaiKang 799600902@qq.com
 */
public interface DynamicSqlMapper<R> extends PageMapper<R> {
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    long count(SelectStatementProvider selectStatement);

    @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
    int delete(DeleteStatementProvider deleteStatement);

    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    int insert(InsertStatementProvider<R> insertStatement);

    @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    int update(UpdateStatementProvider updateStatement);

    @InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<R> multipleInsertStatement);

    @InsertProvider(type = SqlProviderAdapter.class, method = "insertSelect")
    int insertSelect(InsertSelectStatementProvider insertSelectStatement);

    default int insertSelect(InsertSelectDSL insertSelectDSL) {
        return insertSelect(DynamicSqlUtil.render(insertSelectDSL));
    }
}
