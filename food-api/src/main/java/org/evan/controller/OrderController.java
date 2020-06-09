package org.evan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.evan.bo.AddressBo;
import org.evan.bo.SubmitOrderBo;
import org.evan.enums.PayMethod;
import org.evan.pojo.UserAddress;
import org.evan.service.AddressService;
import org.evan.service.OrderService;
import org.evan.util.CookieUtils;
import org.evan.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: food
 * @description: 订单Controller
 * @author: Evan
 * @create: 2020-05-29 15:21
 **/
@Api(value = "订单相关服务接口",tags = "订单相关服务接口")
@RestController
@RequestMapping("orders")
public class OrderController {
    public static final String SHOPCART="shopcart";

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "创建订单", notes = "创建订单", httpMethod = "POST")
    @PostMapping("create")
    public Result create(
            @ApiParam(name = "orderBo",value ="订单信息",required = true)
            @RequestBody SubmitOrderBo orderBo,
            HttpServletRequest request, HttpServletResponse response){

        if(!orderBo.getPayMethod().equals(PayMethod.WEXIN.code)
            && !orderBo.getPayMethod().equals(PayMethod.ZHIFUBAO.code)){
            return Result.failed("支付方式不支持!");
        }
        //1.创建订单
        String orderId =  orderService.create(orderBo);
        //TODO:整合redis之后,移除购物车中已结算的商品
        CookieUtils.setCookie(request,response,SHOPCART,"");
        //3.向支付中心发送当前订单,用于保存支付中心的订单数据
        return Result.success(orderId);
    }
}
