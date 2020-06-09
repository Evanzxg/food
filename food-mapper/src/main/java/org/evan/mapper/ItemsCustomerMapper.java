package org.evan.mapper;


import org.apache.ibatis.annotations.Param;
import org.evan.vo.CommentVo;
import org.evan.vo.ItemSearchVo;
import org.evan.vo.ShopCartVo;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface ItemsCustomerMapper{

    /**
     * 根据是商品ID和评价等级查询评价信息
     * @param map
     * @return
     */
    List<CommentVo> queryItemComments(Map<String,Object> map);

    /**
     * 搜索商品
     * @param map
     * @return
     */
    List<ItemSearchVo> searchItems(Map<String,Object> map);

    /**
     * 根据分类查询商品列表
     * @param map
     * @return
     */
    List<ItemSearchVo> searchItemsByThirdCat(Map<String,Object> map);

    /**
     * 根据规格ID集合查询商品
     * @param specIds
     * @return
     */
    List<ShopCartVo> queryItemsBySpec(@Param("ids") List<String> specIds);

    /**
     * 扣除库存
     * @param specId
     * @param byCounts
     * @return
     */
    int deductInventory(@Param("specId") String specId,@Param("byCounts") int byCounts);
}