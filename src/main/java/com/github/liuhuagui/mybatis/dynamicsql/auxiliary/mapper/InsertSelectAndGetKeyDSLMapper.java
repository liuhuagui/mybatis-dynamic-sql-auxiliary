package com.github.liuhuagui.mybatis.dynamicsql.auxiliary.mapper;

import com.github.liuhuagui.mybatis.dynamicsql.auxiliary.model.GeneratedKey;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.mybatis.dynamic.sql.insert.InsertSelectDSL;
import org.mybatis.dynamic.sql.insert.render.InsertSelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import com.github.liuhuagui.mybatis.dynamicsql.auxiliary.util.DynamicSqlUtil;

/**
 * InsertSelect and get the Generated Key at the same time.
 * The *Mapper that requires the InsertSelectAndGetKey function can inherit it directly.
 *
 * @author KaiKang 799600902@qq.com
 */
public interface InsertSelectAndGetKeyDSLMapper {
    @InsertProvider(type = SqlProviderAdapter.class, method = "insertSelect")
    @Options(useGeneratedKeys = true, keyProperty = GeneratedKey.KEY_PROPERTY)
    int insertSelectAndGetKey(InsertSelectStatementProvider insertSelectStatement);

    default int insertSelectAndGetKey(InsertSelectDSL insertSelectDSL) {
        InsertSelectStatementProvider render = DynamicSqlUtil.render(insertSelectDSL);
        try {
            return insertSelectAndGetKey(render);
        } finally {
            GeneratedKey.set(render.getParameters()::get);
        }
    }
}
