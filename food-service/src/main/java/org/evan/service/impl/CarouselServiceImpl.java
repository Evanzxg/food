package org.evan.service.impl;

import org.evan.mapper.CarouselMapper;
import org.evan.pojo.Carousel;
import org.evan.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @program: food
 * @description: 轮播图serviceImpl
 * @author: Evan
 * @create: 2020-05-29 14:02
 **/
@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow",isShow);
        example.orderBy("sort").asc();

        return carouselMapper.selectByExample(example);
    }
}
