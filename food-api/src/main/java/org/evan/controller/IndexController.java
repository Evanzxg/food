package org.evan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import org.evan.enums.YesOrNo;
import org.evan.pojo.Carousel;
import org.evan.pojo.Category;
import org.evan.service.CarouselService;
import org.evan.service.CategoryService;
import org.evan.util.Result;
import org.evan.vo.CategoryVo;
import org.evan.vo.ItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: food
 * @description: 轮播图Controller
 * @author: Evan
 * @create: 2020-05-29 15:21
 **/
@Api(value = "首页信息展示",tags = "首页信息展示")
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "轮播图", notes = "获取首页轮播图信息", httpMethod = "GET")
    @GetMapping("carousel")
    public Result queryCarousel(){
        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.code);
        return Result.success(carousels);
    }

    @ApiOperation(value = "获取一级分类", notes = "获取一级分类", httpMethod = "GET")
    @GetMapping("cats")
    public Result cats(){
        List<Category> carousels = categoryService.queryRootCategory();
        return Result.success(carousels);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("subCat/{rootCatId}")
    public Result subCat(
            @ApiParam(name = "rootCateId",value = "一级分类ID",required = true)
            @PathVariable("rootCatId") Integer rootCatId){

        if(rootCatId == null){
            Result.failed("分类不存在");
        }
        List<CategoryVo> categoryVos = categoryService.queryByParentId(rootCatId);
        return Result.success(categoryVos);
    }

    @ApiOperation(value = "根据商品分类查询推荐数据", notes = "根据商品分类查询推荐数据", httpMethod = "GET")
    @GetMapping("sixNewItems/{rootCatId}")
    public Result sixNewItems(@PathVariable Integer rootCatId){
        List<ItemVo> sixNewItemsLazy = categoryService.getSixNewItemsLazy(rootCatId);
        return Result.success(sixNewItemsLazy);
    }
}
