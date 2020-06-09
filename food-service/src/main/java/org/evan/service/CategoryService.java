package org.evan.service;

import org.evan.pojo.Category;
import org.evan.vo.CategoryVo;
import org.evan.vo.ItemVo;

import java.util.List;

/**
 * @description 分类service
 * @date 2020/5/29
 * @author evan
 */
public interface CategoryService {

    /**
     * 查询所有一级分类
     * @return
     */
    List<Category> queryRootCategory();

    /**
     * 根据父级ID查询子分类
     * @param parentId
     * @return
     */
    List<CategoryVo> queryByParentId(Integer parentId);

    /**
     * 根据商品分类查询推荐数据
     * @param rootCatId
     * @return
     */
    List<ItemVo> getSixNewItemsLazy(Integer rootCatId);

}
