package org.evan.service;

import org.evan.bo.SubmitOrderBo;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/6/6 0006
 * Time: 20:19
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 */
public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderBo
     */
    String create(SubmitOrderBo submitOrderBo);
}

