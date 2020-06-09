package org.evan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.evan.enums.CommentLevel;
import org.evan.enums.YesOrNo;
import org.evan.mapper.*;
import org.evan.pojo.*;
import org.evan.service.ItemsService;
import org.evan.util.DesensitizationUtil;
import org.evan.util.PageGridResult;
import org.evan.vo.CommentLevelCountsVo;
import org.evan.vo.CommentVo;
import org.evan.vo.ItemSearchVo;
import org.evan.vo.ShopCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private ItemsCustomerMapper itemsCustomerMapper;


    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public Items queryItemsById(String id) {
        return itemsMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<ItemsImg> queryItemImageList(String id) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", id);
        return itemsImgMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<ItemsSpec> queryItemSpecList(String id) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", id);
        return itemsSpecMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public ItemsParam queryItemsParamList(String id) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", id);
        return itemsParamMapper.selectOneByExample(example);
    }


    @Override
    public void parseData(String json) {
        HashMap hashMap = JSON.parseObject(json, HashMap.class);
        System.out.println(hashMap);
        JSONObject data = (JSONObject) hashMap.get("data");

        JSONObject items = (JSONObject) data.get("item");
        Items item = new Items();
        item.setId(items.getString("id"));
        item.setContent(items.getString("content"));
        item.setCatId(items.getInteger("catId"));
        item.setRootCatId(items.getInteger("rootCatId"));
        item.setCreatedTime(items.getDate("createdTime"));
        item.setUpdatedTime(items.getDate("updatedTime"));
        item.setOnOffStatus(items.getInteger("onOffStatus"));
        item.setSellCounts(items.getInteger("sellCounts"));
        item.setItemName(items.getString("itemName"));
        itemsMapper.insert(item);

        List<JSONObject> itemImgList = (List<JSONObject>) data.get("itemImgList");
        for (JSONObject itemImg : itemImgList) {
            ItemsImg img = new ItemsImg();
            img.setId(itemImg.getString("id"));
            img.setItemId(itemImg.getString("itemId"));
            img.setIsMain(itemImg.getInteger("isMain"));
            img.setSort(itemImg.getInteger("sort"));
            img.setUrl(itemImg.getString("url"));
            img.setCreatedTime(itemImg.getDate("createdTime"));
            img.setUpdatedTime(itemImg.getDate("updatedTime"));
            System.out.println(img);
            itemsImgMapper.insert(img);
        }

        List<JSONObject> itemSpecList = (List<JSONObject>) data.get("itemSpecList");
        for (JSONObject itemSpec : itemSpecList) {
            ItemsSpec spec = new ItemsSpec();
            spec.setId(itemSpec.getString("id"));
            spec.setItemId(itemSpec.getString("itemId"));
            spec.setName(itemSpec.getString("name"));
            spec.setStock(itemSpec.getInteger("stock"));
            spec.setDiscounts(itemSpec.getBigDecimal("discounts"));
            spec.setPriceDiscount(itemSpec.getInteger("priceDiscount"));
            spec.setPriceNormal(itemSpec.getInteger("priceNormal"));
            spec.setCreatedTime(itemSpec.getDate("createdTime"));
            spec.setUpdatedTime(itemSpec.getDate("updatedTime"));
            System.out.println(spec);
            itemsSpecMapper.insert(spec);
        }

        JSONObject itemParams = (JSONObject) data.get("itemParams");
        ItemsParam itemsParam = new ItemsParam();
        itemsParam.setId(itemParams.getString("id"));
        itemsParam.setItemId(itemParams.getString("itemId"));
        itemsParam.setProducPlace(itemParams.getString("producPlace"));
        itemsParam.setFootPeriod(itemParams.getString("footPeriod"));
        itemsParam.setBrand(itemParams.getString("brand"));
        itemsParam.setFactoryName(itemParams.getString("factoryName"));
        itemsParam.setFactoryAddress(itemParams.getString("factoryAddress"));
        itemsParam.setWeight(itemParams.getString("weight"));
        itemsParam.setPackagingMethod(itemParams.getString("packagingMethod"));
        itemsParam.setStorageMethod(itemParams.getString("storageMethod"));
        itemsParam.setEatMethod(itemParams.getString("eatMethod"));
        itemsParam.setCreatedTime(itemParams.getDate("createdTime"));
        itemsParam.setUpdatedTime(itemParams.getDate("updatedTime"));
        System.out.println(itemsParam);
        itemsParamMapper.insert(itemsParam);

    }

    @Transactional(readOnly = true)
    @Override
    public CommentLevelCountsVo queryCommonCounts(String id) {
        Integer goodCounts = getCommentCounts(id, CommentLevel.GOOD.code);
        Integer normalCounts = getCommentCounts(id, CommentLevel.NORMAL.code);
        Integer badCounts = getCommentCounts(id, CommentLevel.BAD.code);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVo countsVo = new CommentLevelCountsVo();
        countsVo.setBadCounts(badCounts);
        countsVo.setGoodCounts(goodCounts);
        countsVo.setTotalCounts(totalCounts);
        countsVo.setNormalCounts(normalCounts);
        return countsVo;
    }

    @Transactional(readOnly = true)
    Integer getCommentCounts(String itemId,Integer level){
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if(level != null){
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }

    @Transactional(readOnly = true)
    @Override
    public PageGridResult queryPagedComments(String itemId, Integer level, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);

        PageHelper.startPage(pageNum, pageSize);

        List<CommentVo> commentVoList = itemsCustomerMapper.queryItemComments(map);

        //TODO : 用户信息脱敏(待优化)
        if(!CollectionUtils.isEmpty(commentVoList)){
            for (CommentVo commentVo : commentVoList) {
                String userNickName = commentVo.getUserNickName();
                String s = DesensitizationUtil.desensitization(userNickName);
                commentVo.setUserNickName(s);
            }
        }
        PageGridResult result = setterPagedGrid(commentVoList, pageNum);
        return result;
    }

    private PageGridResult setterPagedGrid(List<?> list,int page){
        PageInfo<?> pageInfo = new PageInfo<>(list);

        PageGridResult result = new PageGridResult();
        result.setPage(page);
        result.setRows(list);
        result.setRecords(pageInfo.getTotal());
        result.setTotal(pageInfo.getPages());
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public PageGridResult searchItems(String keywords, Integer page, Integer pageSize, String sort) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("keywords", keywords);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<ItemSearchVo> itemSearchVos = itemsCustomerMapper.searchItems(map);
        return setterPagedGrid(itemSearchVos, page);
    }

    @Transactional(readOnly = true)
    @Override
    public PageGridResult searchItemsByThirdCat(Integer catId,String sort,Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("catId", catId);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);
        List<ItemSearchVo> itemSearchVos = itemsCustomerMapper.searchItemsByThirdCat(map);
        return setterPagedGrid(itemSearchVos, page);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShopCartVo> queryItemsBySpecIds(String specIds) {
        String[] split = specIds.split(",");
        List<String> ids = Arrays.asList(split);
        return itemsCustomerMapper.queryItemsBySpec(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public ItemsSpec getItemSpecById(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    @Transactional(readOnly = true)
    @Override
    public String getItemMainImgByItemId(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setIsMain(YesOrNo.YES.code);
        itemsImg.setItemId(itemId);

        ItemsImg result = itemsImgMapper.selectOne(itemsImg);

        return result != null ? result.getUrl() : "";
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void deductInventory(String specId, int byCounts) {
        int i = itemsCustomerMapper.deductInventory(specId, byCounts);
        if(i != 1){
            throw new RuntimeException("订单创建失败,原因:库存不足!");
        }
    }
}
