package org.evan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.evan.bo.ShopCartBo;
import org.evan.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: food
 * @description: 购物车Controller
 * @author: Evan
 * @create: 2020-05-29 15:21
 **/
@Api(value = "购物车相关接口",tags = "购物车相关接口")
@RestController
@RequestMapping("shopcart")
public class ShopCartController {

    @ApiOperation(value = "加入购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("add")
    public Result add(@RequestParam String userId,
                      @RequestBody ShopCartBo shopCartBo,
                      HttpServletRequest request,
                      HttpServletResponse response){
        //TODO 判断用户是否登录
        if(StringUtils.isBlank(userId)){
            return Result.failed("未登录!");
        }

        //TODO :前端用户在登录的情况下,添加商品到购物车,会同事在后端同步购物车到redis缓存中
        return Result.success();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("del")
    public Result del(@RequestParam String userId,
                      @RequestBody String itemSpecId){
        //TODO 判断用户是否登录
        if(StringUtils.isBlank(userId)){
            return Result.failed("未登录!");
        }
        if(StringUtils.isBlank(itemSpecId)){
            return Result.failed("商品ID不能为空!");
        }

        //TODO :用户在页面删除购物车中的商品数据,
        // 如果此时用户已经登录,则需要同步删除后端购物车中的数据
        return Result.success();
    }


}
