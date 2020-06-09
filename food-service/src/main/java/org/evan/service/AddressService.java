package org.evan.service;

import org.evan.bo.AddressBo;
import org.evan.pojo.UserAddress;

import java.util.List;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/6/6 0006
 * Time: 0:38
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 */
public interface AddressService {

    /**
     * 根据用户查询收货地址
     * @param userId
     * @return
     */
    List<UserAddress> queryAddressListByUserId(String userId);

    /**
     * 新增收货地址
     * @param address
     */
    void add(AddressBo address);

    /**
     * 修改收货地址
     * @param addressBo
     */
    void update(AddressBo addressBo);

    /**
     * 删除收货地址
     * @param userId
     * @param addressId
     */
    void delete(String userId,String addressId);

    /**
     * 设置默认收货地址
     * @param userId
     * @param addressId
     */
    void setDefaultAddress(String userId,String addressId);

    /**
     * 根据用户ID和收货地址ID查询
     * @param userId
     * @param addressId
     * @return
     */
    UserAddress queryByUserIdAndId(String userId,String addressId);
}
