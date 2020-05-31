package com.github.liuhuagui.mybatis.auxiliary.page.handler;

import com.github.liuhuagui.mybatis.auxiliary.mapper.PageMapper;
import com.github.liuhuagui.mybatis.auxiliary.model.BaseQuery;
import com.github.liuhuagui.mybatis.auxiliary.page.Limit;
import com.github.liuhuagui.mybatis.auxiliary.page.PageInfo;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.where.WhereApplier;

import java.util.List;
import java.util.Objects;

/**
 * 分页处理器
 *
 * @author liuhuagui
 */
public class PageHandler {
    public static final ThreadLocal<Limit> LIMIT = new ThreadLocal<>();

    /**
     * <ol>
     *     <li>如果pageNo为null或小于等于0，抛出异常。</li>
     *     <li>如果pageSize为null，使用maxPageSize作为pageSize。</li>
     *     <li>如果pageSize不为null，但是小于等于0或大于maxPageSize，抛出异常。</li>
     * </ol>
     *
     * @param baseQuery   查询参数
     * @param maxPageSize 作为pageSize的默认值或最大限制，该值一定不为null
     */
    public static void validate(BaseQuery baseQuery, Long maxPageSize) {
        Objects.requireNonNull(baseQuery);
        Objects.requireNonNull(maxPageSize);

        Long pageNo = baseQuery.getPageNo();
        //如果pageNo为null或小于等于0，抛出异常
        if (pageNo == null || pageNo <= 0)
            throw new IllegalArgumentException(String.format("pageNo %s is illegal.", pageNo));

        Long pageSize = baseQuery.getPageSize();
        //如果pageSize为null，使用maxPageSize作为pageSize
        if (pageSize == null) {
            baseQuery.setPageSize(maxPageSize);
            return;
        }
        //如果pageSize不为null，但是小于等于0或大于maxPageSize，抛出异常
        if (pageSize <= 0 || pageSize > maxPageSize)
            throw new IllegalArgumentException(String.format("pageSize %s is illegal.", pageSize));
    }

    /**
     * 根据《阿里巴巴开发手册》，如果totalCount为0，立即返回，不再处理后续分页逻辑，提高程序执行性能。<br>
     * 如果直接调用此方法，需要在调用前对totalCount判断处理。
     *
     * @param baseQuery
     * @param totalCount
     * @return
     */
    public static PageInfo init(BaseQuery baseQuery, Long totalCount) {
        Long pageNo = baseQuery.getPageNo();
        Long pageSize = baseQuery.getPageSize();
        //计算总页数
        Long pageCount = (totalCount % pageSize) == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        //页码总是小于等于总页数
        pageNo = pageNo > pageCount ? pageCount : pageNo;

        //存放分页信息
        Long size = pageSize;
        Long offset = (pageNo - 1) * size;
        LIMIT.set(new Limit(offset, size));
        return new PageInfo(pageNo, pageSize, totalCount, pageCount);
    }

    /**
     * 如果totalCount为0，该方法返回null，所以需要对该方法的返回值进行判空处理。<br>
     * 根据《阿里巴巴开发手册》，如果totalCount为0，立即返回，不再处理后续分页逻辑，提高程序执行性能。
     *
     * @param baseQuery
     * @param pageMapper
     * @param countDSLCompleter
     * @return
     */
    public static PageInfo init(BaseQuery baseQuery, PageMapper pageMapper, CountDSLCompleter countDSLCompleter) {
        Long totalCount = pageMapper.count(countDSLCompleter);
        if (totalCount == 0)
            return null;
        return init(baseQuery, totalCount);
    }

    /**
     * 如果totalCount为0，该方法返回null，所以需要对该方法的返回值进行判空处理。<br>
     * 根据《阿里巴巴开发手册》，如果totalCount为0，立即返回，不再处理后续分页逻辑，提高程序执行性能。
     *
     * @param baseQuery
     * @param pageMapper
     * @param whereApplier
     * @return
     */
    public static PageInfo init(BaseQuery baseQuery, PageMapper pageMapper, WhereApplier whereApplier) {
        CountDSLCompleter countDSLCompleter = selectDSL -> selectDSL.applyWhere(whereApplier);
        return init(baseQuery, pageMapper, countDSLCompleter);
    }

    public static <T> List<T> list(PageMapper<T> pageMapper, SelectDSLCompleter selectDSLCompleter) {
        return pageMapper.select(selectDSLCompleter);
    }

    public static <T> List<T> list(PageMapper<T> pageMapper, WhereApplier whereApplier, SortSpecification... orderByColumns) {
        Limit limit = PageHandler.limit();
        SelectDSLCompleter selectDSLCompleter = selectDSL ->
                selectDSL.applyWhere(whereApplier)
                        .orderBy(orderByColumns)
                        .limit(limit.getSize())
                        .offset(limit.getOffset());
        return list(pageMapper, selectDSLCompleter);
    }

    public static Limit limit() {
        return LIMIT.get();
    }
}
