package com.github.liuhuagui.mybatis.auxiliary.mapper;


import com.github.liuhuagui.mybatis.auxiliary.modle.BaseDO;
import com.github.liuhuagui.mybatis.auxiliary.page.Limit;
import com.github.liuhuagui.mybatis.auxiliary.util.DynamicSqlUtil;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.InsertSelectDSL;
import org.mybatis.dynamic.sql.insert.render.InsertSelectStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.where.WhereApplier;

import java.util.List;
import java.util.function.Supplier;

/**
 * Base Mapper is used to support dynamic sql.
 *
 * @param <R> record type
 * @param <I> primary key type
 * @author KaiKang 799600902@qq.com
 */
public interface DynamicSqlMapper<R extends BaseDO<R>, I> extends BaseMapper<R, I> {
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

    default WhereApplier where(R record) {
        return whereDSL -> whereDSL;
    }

    long count(CountDSLCompleter completer);

    List<R> select(SelectDSLCompleter completer);

    @Override
    default long count(R record) {
        return count(countDSL -> countDSL.applyWhere(where(record)));
    }

    @Override
    default List<R> list(R record) {
        return list(record, () -> new SortSpecification[]{});
    }

    default List<R> list(R record, Supplier<SortSpecification[]> orderByConditionSupplier) {
        Limit limit = record.getLimit();
        return select(queryDSL -> queryDSL.applyWhere(where(record))
                .orderBy(orderByConditionSupplier.get())
                .limit(limit.getSize())
                .offset(limit.getOffset()));
    }
}
