package org.evan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.evan.bo.AddressBo;
import org.evan.enums.YesOrNo;
import org.evan.pojo.Carousel;
import org.evan.pojo.Category;
import org.evan.pojo.UserAddress;
import org.evan.service.AddressService;
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
@Api(value = "收货地址相关",tags = "收货地址相关")
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "获取用户收货地址列表", notes = "获取用户收货地址列表", httpMethod = "POST")
    @PostMapping("list")
    public Result list(
            @ApiParam(name = "userId",value ="用户ID",required = true)
            @RequestParam String userId){
        if(StringUtils.isBlank(userId)){
            return Result.failed("参数异常");
        }

        List<UserAddress> userAddresses = addressService.queryAddressListByUserId(userId);
        return Result.success(userAddresses);
    }

    @ApiOperation(value = "新增收货地址", notes = "新增收货地址", httpMethod = "POST")
    @PostMapping("add")
    public Result add(
            @ApiParam(name = "addressBo",value ="收货地址BO",required = true)
            @RequestBody AddressBo addressBo){
        //参数校验
        if(StringUtils.isBlank(addressBo.getReceiver())){
            return Result.failed("收货人不能为空");
        }
        if(addressBo.getReceiver().length() > 12){
            return Result.failed("收货人姓名不能太长");
        }
        String mobile = addressBo.getMobile();
        if(StringUtils.isBlank(mobile)){
            return Result.failed("手机号不能为空");
        }
        if(mobile.length() != 11){
            return Result.failed("手机号长度不正确");
        }
        //TODO 手机号段验证工具类
        addressService.add(addressBo);
        return Result.success();
    }

    @ApiOperation(value = "修改收货地址", notes = "修改收货地址", httpMethod = "POST")
    @PostMapping("update")
    public Result update(
            @ApiParam(name = "addressBo",value ="收货地址BO",required = true)
            @RequestBody AddressBo addressBo){
        //参数校验
        if(StringUtils.isBlank(addressBo.getAddressId())){
            return Result.failed("参数异常");
        }
        //TODO 手机号段验证工具类
        addressService.update(addressBo);
        return Result.success();
    }

    @ApiOperation(value = "删除收货地址", notes = "删除收货地址", httpMethod = "POST")
    @PostMapping("delete")
    public Result delete(
            @ApiParam(name = "userId",value ="用户ID",required = true)
            @RequestParam String userId,
            @ApiParam(name = "addressId",value ="收货地址ID",required = true)
            @RequestParam String addressId){
        //参数校验
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return Result.failed("参数异常");
        }
        addressService.delete(userId,addressId);
        return Result.success();
    }

    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址", httpMethod = "POST")
    @PostMapping("setDefalut")
    public Result setDefalut(
            @ApiParam(name = "userId",value ="用户ID",required = true)
            @RequestParam String userId,
            @ApiParam(name = "addressId",value ="收货地址ID",required = true)
            @RequestParam String addressId){
        //参数校验
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return Result.failed("参数异常");
        }
        addressService.setDefaultAddress(userId,addressId);
        return Result.success();
    }
}
