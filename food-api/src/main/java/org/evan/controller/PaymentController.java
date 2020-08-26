package org.evan.controller;

import com.alibaba.fastjson.JSONObject;
import io.goeasy.GoEasy;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.evan.enums.OrderStatusEnum;
import org.evan.pojo.Orders;
import org.evan.service.OrderService;
import org.evan.util.Result;
import org.evan.utils.WxPayDto;
import org.evan.utils.WxchatPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/8/19 0019
 * Time: 0:08
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
@RestController
@RequestMapping("payment")
public class PaymentController {

    GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "你的APP-key");
    @Autowired
    private OrderService orderService;
    /**
     * @param merchantUserId
     * @param merchantOrderId
     * @return
     */
    @ApiOperation(value = "生成支付信息",notes = "生成支付信息",httpMethod = "GET")
    @PostMapping("getWXPayQRCode")
    public Result getWXPayQRCode(String merchantUserId,String merchantOrderId){

        if(StringUtils.isBlank(merchantUserId) || StringUtils.isBlank(merchantOrderId)){
            return Result.failed("参数异常");
        }

        WxPayDto dto=new WxPayDto();
        dto.setBody("订单支付");
        dto.setOut_trade_no(merchantOrderId);
        dto.setTotal_fee(1);
        dto.setOrderId(merchantOrderId);
        dto.setUserId(merchantUserId);
        String pay = WxchatPayUtil.createPay(dto);
        System.out.println(pay);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", dto.getTotal_fee());
        jsonObject.put("merchantOrderId", merchantOrderId);
        jsonObject.put("merchantUserId", merchantUserId);
        jsonObject.put("qrCodeUrl", pay);
        return Result.success(jsonObject);
    }

    @PostMapping("notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //1.接收消息
        String xml=new String(IOUtils.toByteArray(request.getInputStream()));
        HashMap<String,Object> map=WxchatPayUtil.parseXml(xml);
        //2.更改数据库对应得到订单的支付状态
        if(map.get("result_code").equals("SUCCESS")){
            String oid=map.get("out_trade_no").toString();
            int money=Integer.parseInt(map.get("total_fee").toString());
            //根据查询订单 校验金额是否一致
            Orders orders = orderService.get(oid);
            Integer realPayAmount = orders.getRealPayAmount();
//            if(money != realPayAmount){
//                throw new RuntimeException("支付金额不一致...!");
//            }
            //一致 更改订单状态--->未支付--->已支付,代发货
            orderService.updateOrderStatus(oid, OrderStatusEnum.WAIT_DELIVERY.code);
            System.out.println(oid);
            System.out.println(money);
        }
        //3.通知前端 支付结果
        goEasy.publish("my_channel", "{" +
                                                        "\"msg\":\""+map.get("result_code")+"\"," +
                                                        "\"amount\":\""+ map.get("total_fee") +"\"," +
                                                        "\"oid\":\""+map.get("out_trade_no")+"\"" +
                                                      "}");
        //4.返回消息 固定
        response.getWriter().write("<xml>"+
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>");
    }

}
