package org.evan.service;


import org.evan.Application;
import org.evan.mapper.CarouselMapper;
import org.evan.pojo.Carousel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={Application.class})
public class CarouselServiceTest {
    @Autowired
    private CarouselService carouselService;

    @Test
    public void queryAll() {
        List<Carousel> carousels = carouselService.queryAll(1);
        System.out.println(carousels);
    }
}