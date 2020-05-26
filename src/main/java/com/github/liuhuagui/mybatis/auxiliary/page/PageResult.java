package com.github.liuhuagui.mybatis.auxiliary.page;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果
 *
 * @param <T> DO
 * @author KaiKang 799600902@qq.com
 */
@Getter
public class PageResult<T> extends Page {
    /**
     * 总行数
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Long pageCount;

    /**
     * 当前页的显示结果
     */
    private List<T> results = new ArrayList<>();

    /**
     * 是否有首页
     */
    private Boolean hasFirst;

    /**
     * 是否有上一页
     */
    private Boolean hasPre;

    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    /**
     * 是否有最后一页
     */
    private Boolean hasLast;

    public PageResult(Long pageNo, Long pageSize, Long totalCount, Long pageCount) {
        super.setPageNo(pageNo);
        super.setPageSize(pageSize);
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.hasFirst = pageNo > 1 ? true : false;
        this.hasLast = pageNo < pageCount ? true : false;
        this.hasPre = this.hasFirst;
        this.hasNext = this.hasLast;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
