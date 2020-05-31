package com.github.liuhuagui.mybatis.dynamicsql.auxiliary.page;

import lombok.Getter;

/**
 * @author liuhuagui
 */
@Getter
public class PageInfo extends Page {
    /**
     * 总行数
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Long pageCount;

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

    public PageInfo(Long pageNo, Long pageSize, Long totalCount, Long pageCount) {
        super.setPageNo(pageNo);
        super.setPageSize(pageSize);
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.hasFirst = pageNo > 1 ? true : false;
        this.hasLast = pageNo < pageCount ? true : false;
        this.hasPre = this.hasFirst;
        this.hasNext = this.hasLast;
    }
}
