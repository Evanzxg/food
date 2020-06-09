package org.evan.service.impl;

import org.evan.bo.AddressBo;
import org.evan.enums.YesOrNo;
import org.evan.mapper.UserAddressMapper;
import org.evan.pojo.UserAddress;
import org.evan.service.AddressService;
import org.evan.util.KeyGenerateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/6/1 0001
 * Time: 21:58
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper addressMapper;


    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<UserAddress> queryAddressListByUserId(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);

        return addressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void add(AddressBo bo) {
        Integer isDef = YesOrNo.NO.code;
        //1.判断当前用户是否存在地址,如果没有,则新增为默认地址
        List<UserAddress> userAddresses = this.queryAddressListByUserId(bo.getUserId());
        if(CollectionUtils.isEmpty(userAddresses)){
            isDef =YesOrNo.YES.code;
        }
        //2.保存地址到数据库
        UserAddress address = new UserAddress();
        BeanUtils.copyProperties(bo,address);

        String id = KeyGenerateUtil.get16LetterAndNum();
        address.setIsDefault(isDef);
        address.setId(id);
        address.setCreatedTime(new Date());
        address.setUpdatedTime(new Date());

        addressMapper.insert(address);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void update(AddressBo addressBo) {
        String addressId = addressBo.getAddressId();

        UserAddress updateAddress = new UserAddress();
        BeanUtils.copyProperties(addressBo,updateAddress);

        updateAddress.setId(addressId);
        updateAddress.setUpdatedTime(new Date());
        addressMapper.updateByPrimaryKeySelective(updateAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void delete(String userId, String addressId) {
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);

        addressMapper.delete(address);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void setDefaultAddress(String userId, String addressId) {
        //1.查找默认地址,设置为不默认
        UserAddress userAddress = new UserAddress();
        userAddress.setIsDefault(YesOrNo.NO.code);

        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        addressMapper.updateByExampleSelective(userAddress, example);

        //2.根据地址ID修改为默认地址
        userAddress.setIsDefault(YesOrNo.YES.code);
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        addressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Transactional(readOnly = true)
    @Override
    public UserAddress queryByUserIdAndId(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressId);
        userAddress.setUserId(userId);

        return addressMapper.selectOne(userAddress);
    }
}
