package org.evan.service.impl;

import org.evan.mapper.CategoryMapper;
import org.evan.mapper.CategoryMapperCustomer;
import org.evan.pojo.Category;
import org.evan.service.CategoryService;
import org.evan.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: food
 * @description: 轮播图serviceImpl
 * @author: Evan
 * @create: 2020-05-29 14:02
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryMapperCustomer categoryMapperCustomer;

    /**
     * 查询所有一级分类
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<Category> queryRootCategory() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);

        return categoryMapper.selectByExample(example);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryVo> queryByParentId(Integer parentId) {
        return categoryMapperCustomer.querySubCatsByRootId(parentId);
    }

    @Override
    public List getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("rootCatId", rootCatId);
        return categoryMapperCustomer.getSixNewItemsLazy(map);
    }
}
