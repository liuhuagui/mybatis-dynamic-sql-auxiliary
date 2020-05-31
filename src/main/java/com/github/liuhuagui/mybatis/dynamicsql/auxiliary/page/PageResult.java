package com.github.liuhuagui.mybatis.dynamicsql.auxiliary.page;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页结果
 *
 * @param <T> DO
 * @author liuhuagui
 */
@Getter
public class PageResult<T> extends PageInfo implements Serializable {
    /**
     * 当前页的显示结果
     */
    private List<T> results = new ArrayList<>();

    public PageResult() {
        super(0L, 0L, 0L, 0L);
    }

    public PageResult(PageInfo pageInfo, List<T> results) {
        super(pageInfo.getPageNo(), pageInfo.getPageSize(), pageInfo.getTotalCount(), pageInfo.getPageCount());
        this.results = results;
    }

    public static <T, D> PageResult<D> transform(PageInfo pageInfo, List<T> tList, Function<T, D> function) {
        if (tList == null || tList.isEmpty())
            return new PageResult<>();
        List<D> dList = tList.stream().map(function).collect(Collectors.toList());
        return new PageResult<>(pageInfo, dList);
    }
}
