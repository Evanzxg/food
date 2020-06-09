package org.evan.service;

import org.evan.pojo.Carousel;

import java.util.List;

/**
 * @description 轮播图service
 * @date 2020/5/29
 * @author evan
 */
public interface CarouselService {

    /**
     * 查询所有轮播图
     * @param isShow    是否显示
     * @return
     */
    public List<Carousel> queryAll(Integer isShow);
}
