package org.evan.vo;

import java.util.List;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/5/30 0030
 * Time: 20:14
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 */
public class CategoryVo {

    private Integer id;
    private String name;
    private Integer type;
    private Integer fatherId;
    private List<CategoryVo> subCatList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public List<CategoryVo> getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(List<CategoryVo> subCatList) {
        this.subCatList = subCatList;
    }
}
