package org.evan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.evan.pojo.Items;
import org.evan.pojo.ItemsImg;
import org.evan.pojo.ItemsParam;
import org.evan.pojo.ItemsSpec;
import org.evan.service.ItemsService;
import org.evan.util.PageGridResult;
import org.evan.util.Result;
import org.evan.vo.CommentLevelCountsVo;
import org.evan.vo.ItemInfoVo;
import org.evan.vo.ShopCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/6/1 0001
 * Time: 21:52
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
@Api(value = "商品服务相关接口",tags = "商品服务相关接口")
@RestController
@RequestMapping("items")
public class ItemController {

    @Autowired
    private ItemsService itemsService;


    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    @GetMapping("info/{itemId}")
    public Result info(@PathVariable String itemId){

        if(StringUtils.isBlank(itemId)){
            return Result.failed("参数异常");
        }

        Items items = itemsService.queryItemsById(itemId);
        List<ItemsImg> itemsImgs = itemsService.queryItemImageList(itemId);
        ItemsParam itemsParam = itemsService.queryItemsParamList(itemId);
        List<ItemsSpec> itemsSpecs = itemsService.queryItemSpecList(itemId);

        return Result.success(new ItemInfoVo(items,itemsImgs,itemsParam,itemsSpecs));
    }

    @ApiOperation(value = "查询商品评价各等级条数",notes = "查询商品评价各等级条数",httpMethod = "GET")
    @GetMapping("commentLevel")
    public Result commentLevel(
            @ApiParam(name = "itemId",value = "商品ID",required = true)
            @RequestParam String itemId){
        if(StringUtils.isBlank(itemId)){
            return Result.failed("参数异常");
        }

        CommentLevelCountsVo countsVo = itemsService.queryCommonCounts(itemId);
        return Result.success(countsVo);
    }

    @ApiOperation(value = "查询商品评价数据",notes = "查询商品评价数据",httpMethod = "GET")
    @GetMapping("comments")
    public Result comments(
            @ApiParam(name = "itemId",value = "商品ID",required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level",value = "评价等级")
            @RequestParam Integer level,
            @ApiParam(name = "page",value = "查询下一页的第几页")
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize",value = "分页每一页显示的条数")
            @RequestParam(defaultValue = "10") Integer pageSize){
        if(StringUtils.isBlank(itemId)){
            return Result.failed("参数异常");
        }

        PageGridResult result = itemsService.queryPagedComments(itemId, level, page, pageSize);
        return Result.success(result);
    }

    @ApiOperation(value = "搜索商品列表",notes = "搜索商品列表",httpMethod = "GET")
    @GetMapping("search")
    public Result search(
            @ApiParam(name = "keywords",value = "关键字",required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort",value = "排序")
            @RequestParam String sort,
            @ApiParam(name = "page",value = "查询下一页的第几页")
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize",value = "分页每一页显示的条数")
            @RequestParam(defaultValue = "20") Integer pageSize){

        PageGridResult result = itemsService.searchItems(keywords, page, pageSize,sort);
        return Result.success(result);
    }

    @ApiOperation(value = "根据分类搜索商品列表",notes = "根据分类搜索商品列表",httpMethod = "GET")
    @GetMapping("catItems")
    public Result catItems(
            @ApiParam(name = "catId",value = "分类ID",required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort",value = "排序")
            @RequestParam String sort,
            @ApiParam(name = "page",value = "查询下一页的第几页")
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize",value = "分页每一页显示的条数")
            @RequestParam(defaultValue = "20") Integer pageSize){
        if(catId == null){
            return Result.failed("分类ID不能为空");
        }

        PageGridResult result = itemsService.searchItemsByThirdCat(catId,sort,page, pageSize);
        return Result.success(result);
    }


    @ApiOperation(value = "根据规格集合查询购物车商品信息",notes = "根据规格集合查询购物车商品信息",httpMethod = "GET")
    @GetMapping("refresh")
    public Result refresh(
            @ApiParam(name = "itemSpecIds",value = "逗号分割的规格ids",example = "1,2,3",required = true)
            @RequestParam String itemSpecIds){
        if(StringUtils.isBlank(itemSpecIds)){
            return Result.success();
        }

        List<ShopCartVo> shopCartVos = itemsService.queryItemsBySpecIds(itemSpecIds);
        return Result.success(shopCartVos);
    }



}
