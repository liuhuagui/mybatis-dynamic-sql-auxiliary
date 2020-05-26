package com.github.liuhuagui.mybatis.auxiliary.page.handler;

import com.github.liuhuagui.mybatis.auxiliary.modle.BaseDO;
import com.github.liuhuagui.mybatis.auxiliary.mapper.BaseMapper;
import com.github.liuhuagui.mybatis.auxiliary.page.Limit;
import com.github.liuhuagui.mybatis.auxiliary.page.PageDO;
import com.github.liuhuagui.mybatis.auxiliary.page.PageResult;

import java.util.List;
import java.util.function.Function;

/**
 * 分页处理器
 *
 * @author KaiKang 799600902@qq.com
 */
public class PageHandler {
    private static final Long MAX_PAGE_SIZE = 500L;

    /**
     * 使用@{@link BaseMapper}默认的count()，list()方法做分页查询
     *
     * @param baseMapper
     * @param pageDO
     * @param <T>
     * @return
     */
    public static <T extends BaseDO<T>> PageResult<BaseDO<T>> pageList(BaseMapper<BaseDO<T>, ?> baseMapper, PageDO<BaseDO<T>> pageDO) {
        return pageList(baseMapper::count, baseMapper::list, pageDO);
    }

    /**
     * 提供函数接口count，list做分页查询
     *
     * @param count
     * @param list
     * @param pageDO
     * @param <T>
     * @return
     */
    public static <T extends BaseDO<T>> PageResult<BaseDO<T>> pageList(Function<BaseDO<T>, Long> count, Function<BaseDO<T>, List<BaseDO<T>>> list, PageDO<BaseDO<T>> pageDO) {
        Long pageNo = pageDO.getPageNo();
        Long pageSize = pageDO.getPageSize();
        //验证参数是否合法
        validateParameters(pageNo, pageSize);
        //初始化分页结果
        BaseDO<T> dataO = pageDO.getDataO();
        PageResult<BaseDO<T>> pageResult = page(pageNo, pageSize, count.apply(dataO));
        //遵循阿里开发规约——如果totalCount为0，避免执行后面的分页语句。
        if (pageResult != null) {
            Long size = pageResult.getPageSize();
            Long offset = (pageResult.getPageNo() - 1) * size;
            dataO.limit(new Limit(offset, size));
            pageResult.setResults(list.apply(dataO));
        }
        return pageResult;
    }

    /**
     * 初始化分页结果
     *
     * @param pageNo     页码
     * @param pageSize   页长
     * @param totalCount 总行数
     * @param <T>        条件实体类型
     * @return
     */
    private static <T> PageResult<T> page(Long pageNo, Long pageSize, Long totalCount) {
        //如果总行为0，不再继续查询，返回null
        if (totalCount == 0)
            return null;
        //计算总页数
        Long pageCount = (totalCount % pageSize) == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        //页码总是小于等于总页数
        pageNo = pageNo > pageCount ? pageCount : pageNo;

        return new PageResult<>(pageNo, pageSize, totalCount, pageCount);
    }

    /**
     * 验证参数是否合法
     *
     * @param pageNo
     * @param pageSize
     */
    private static void validateParameters(Long pageNo, Long pageSize) {
        //验证pageNo
        if (pageNo == null || pageNo <= 0)
            throw new IllegalArgumentException(String.format("pageNo is %s.", pageNo));

        //验证pageSize
        if (pageSize == null || pageSize <= 0 || pageSize > MAX_PAGE_SIZE)
            throw new IllegalArgumentException(String.format("pageSize is %s.", pageSize));
    }

}
