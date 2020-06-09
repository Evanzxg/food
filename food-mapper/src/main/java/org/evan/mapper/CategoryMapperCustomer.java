package org.evan.mapper;


import org.apache.ibatis.annotations.Param;
import org.evan.vo.CategoryVo;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustomer {

    /**
     * 根据rootCatId查询分类信息
     * @param rootCatId
     * @return
     */
    List<CategoryVo> querySubCatsByRootId(Integer rootCatId);

    /**
     * 查询首页推荐数据
     * @param map
     * @return
     */
    List getSixNewItemsLazy(@Param("paramsMap") Map<String,Object> map);

}