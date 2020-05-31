package com.github.liuhuagui.mybatis.dynamicsql.auxiliary.util;

import org.mybatis.dynamic.sql.Constant;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.StringConstant;
import org.mybatis.dynamic.sql.insert.InsertSelectDSL;
import org.mybatis.dynamic.sql.insert.render.InsertSelectStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Dynamic Sql Util
 *
 * @author KaiKang 799600902@qq.com
 */
public class DynamicSqlUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * "yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static StringConstant now() {
        return SqlBuilder.stringConstant(LocalDateTime.now().format(FORMATTER));
    }

    public static InsertSelectStatementProvider render(InsertSelectDSL insertSelectDSL) {
        return insertSelectDSL.build().render(RenderingStrategies.MYBATIS3);
    }

    public static String like(String value) {
        if (value == null)
            return null;
        return value + "%";
    }

    public static Constant aNull() {
        return SqlBuilder.constant("null");
    }

    public static SortSpecification[] sorts(SortSpecification... columns) {
        return columns;
    }
}
