package org.evan.service.impl;

import org.evan.bo.SubmitOrderBo;
import org.evan.enums.OrderStatusEnum;
import org.evan.enums.YesOrNo;
import org.evan.mapper.OrderItemsMapper;
import org.evan.mapper.OrderStatusMapper;
import org.evan.mapper.OrdersMapper;
import org.evan.pojo.*;
import org.evan.service.AddressService;
import org.evan.service.ItemsService;
import org.evan.service.OrderService;
import org.evan.util.KeyGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/6/6 0006
 * Time: 20:20
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public String create(SubmitOrderBo submitOrderBo) {
        String orderId = KeyGenerateUtil.get16LetterAndNum();
        //1.保存订单数据
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUserId(submitOrderBo.getUserId());
        UserAddress userAddress = addressService.queryByUserIdAndId(submitOrderBo.getUserId(), submitOrderBo.getAddressId());
        orders.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " " + userAddress.getDistrict() + " " + userAddress.getDetail());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setReceiverName(userAddress.getReceiver());
        //邮费
        orders.setPostAmount(0);
        orders.setPayMethod(submitOrderBo.getPayMethod());
        orders.setLeftMsg(submitOrderBo.getLeftMsg());
        orders.setIsComment(YesOrNo.NO.code);
        orders.setIsDelete(YesOrNo.NO.code);
        orders.setUpdatedTime(new Date());
        orders.setCreatedTime(new Date());
        //2.根据itemSpecIds保存订单商品信息表
        String itemSpecIdsStr = submitOrderBo.getItemSpecIds();
        String[] itemSpecIds = itemSpecIdsStr.split(",");
        //商品原价累计
        Integer totalAmount = 0;
        //优惠后的实际支付价格累计
        Integer realPayAmount = 0;

        for (String itemSpecId : itemSpecIds) {
            //TODO 整合redis之后,商品购买的数量从redis中的购物车获取数量
            int byCount = 1;

            //2.1根据规格id查询规格的具体信息,主要获取价格
            ItemsSpec spec = itemsService.getItemSpecById(itemSpecId);
            totalAmount += spec.getPriceNormal() * byCount;
            realPayAmount += spec.getPriceDiscount() * byCount;

            //2.2根据商品id,获取商品信息
            String itemId = spec.getItemId();
            Items item = itemsService.queryItemsById(itemId);
            String url = itemsService.getItemMainImgByItemId(itemId);

            //2.3循环保存子订单数据
            String subOrderId = KeyGenerateUtil.get16LetterAndNum();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemImg(url);
            subOrderItem.setBuyCounts(byCount);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(spec.getName());
            subOrderItem.setPrice(spec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);

            //2.4扣库存
            itemsService.deductInventory(itemSpecId, byCount);
        }

        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        ordersMapper.insert(orders);

        //4.保存订单状态
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.code);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        return orderId;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Orders get(String id) {
        return ordersMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改订单状态
     * @param oid       订单ID
     * @param status    订单状态
     */
    @Override
    public void updateOrderStatus(String oid, Integer status) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(oid);
        orderStatus.setOrderStatus(status);
        orderStatus.setPayTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }
}
