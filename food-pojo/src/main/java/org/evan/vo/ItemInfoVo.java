package org.evan.vo;

import org.evan.pojo.Items;
import org.evan.pojo.ItemsImg;
import org.evan.pojo.ItemsParam;
import org.evan.pojo.ItemsSpec;

import java.util.List;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/6/3 0003
 * Time: 21:29
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
public class ItemInfoVo {
    private Items item;
    private List<ItemsImg> itemImgList;
    private ItemsParam itemParams;
    private List<ItemsSpec> itemSpecList;

    public ItemInfoVo() {
    }

    public ItemInfoVo(Items item, List<ItemsImg> itemImgList, ItemsParam itemParams, List<ItemsSpec> itemSpecList) {
        this.item = item;
        this.itemImgList = itemImgList;
        this.itemParams = itemParams;
        this.itemSpecList = itemSpecList;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }
}
