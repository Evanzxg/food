package org.evan.service;

import org.evan.pojo.Items;
import org.evan.pojo.ItemsImg;
import org.evan.pojo.ItemsParam;
import org.evan.pojo.ItemsSpec;
import org.evan.util.PageGridResult;
import org.evan.vo.CommentLevelCountsVo;
import org.evan.vo.ShopCartVo;

import java.util.List;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/6/1 0001
 * Time: 21:57
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 */
public interface ItemsService {

    void parseData(String json);

    /**
     * 根据商品ID查询商品详情
     * @param id
     * @return
     */
    Items queryItemsById(String id);

    /**
     * 根据商品ID查询商品图片列表
     * @param id
     * @return
     */
    List<ItemsImg> queryItemImageList(String id);

    /**
     * 根据商品ID查询商品规格
     * @param id
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String id);

    /**
     * 根据商品ID查询商品参数
     * @param id
     * @return
     */
    ItemsParam queryItemsParamList(String id);

    /**
     * 根据商品ID查询商品评价等级数量
     * @param id
     */
    CommentLevelCountsVo queryCommonCounts(String id);

    /**
     * 根据商品ID查询商品评价(分页)
     * @param itemId
     * @param level
     * @return
     */
    PageGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 根据关键字查询商品列表
     * @param keywords
     * @param page
     * @param pageSize
     * @param sort
     * @return
     */
    PageGridResult searchItems(String keywords,Integer page,Integer pageSize,String sort);

    /**
     * 根据分类查询商品列表
     * @param page
     * @param pageSize
     * @param catId
     * @param sort
     * @return
     */
    PageGridResult searchItemsByThirdCat(Integer catId,String sort,Integer page,Integer pageSize);

    /**
     * 根据商品规格ids查询购物车商品集合
     * @param specIds
     * @return
     */
    List<ShopCartVo> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品规格ID查询规格
     * @param specId
     * @return
     */
    ItemsSpec getItemSpecById(String specId);

    /**
     * 根据商品id获取商品主图
     * @param itemId
     * @return
     */
    String getItemMainImgByItemId(String itemId);

    /**
     * 扣除库存
     * @param specId
     * @param byCounts
     */
    void deductInventory(String specId,int byCounts);
}

