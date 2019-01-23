package com.apple.expandview.read;

import com.apple.expandview.diff.Man;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crazyZhangxl on 2019/1/20.
 * Describe:每一个具体的section
 *          通过 list.get(i)来获取具体的section
 *          header+item即为展开的数据  position-1为每一个item
 *
 *          item对应某个position信息
 */
public class Section {
    private Header mHeader;
    private List<Item> mItemList;
    // 上加载
    private boolean hasBefore;
    // 下加载
    private boolean hasAfter;
    // 是否折叠
    private boolean isFold;
    // 重要
    private boolean isLocked;

    private boolean isLoadBeforeError = false;
    private boolean isLoadAfterError = false;

    public boolean isLoadBeforeError() {
        return isLoadBeforeError;
    }

    public void setLoadBeforeError(boolean loadBeforeError) {
        isLoadBeforeError = loadBeforeError;
    }

    public boolean isLoadAfterError() {
        return isLoadAfterError;
    }

    public void setLoadAfterError(boolean loadAfterError) {
        isLoadAfterError = loadAfterError;
    }

    public Section(Header header, List<Item> itemList, boolean hasBefore, boolean hasAfter, boolean isFold, boolean isLocked) {
        mHeader = header;
        mItemList = itemList;
        this.hasBefore = hasBefore;
        this.hasAfter = hasAfter;
        this.isFold = isFold;
        this.isLocked = isLocked;
    }

    public int getChildAccount(){
        if (mItemList != null){
            return mItemList.size();
        }
        return 0;
    }

    public Header getHeader() {
        return mHeader;
    }

    public void setHeader(Header header) {
        mHeader = header;
    }

    public List<Item> getItemList() {
        return mItemList;
    }

    public void setItemList(List<Item> itemList) {
        mItemList = itemList;
    }

    public boolean isHasBefore() {
        return hasBefore;
    }

    public void setHasBefore(boolean hasBefore) {
        this.hasBefore = hasBefore;
    }

    public boolean isHasAfter() {
        return hasAfter;
    }

    public void setHasAfter(boolean hasAfter) {
        this.hasAfter = hasAfter;
    }

    public boolean isFold() {
        return isFold;
    }

    public void setFold(boolean fold) {
        isFold = fold;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Section copy(){
        List<Item> mNewList = new ArrayList<>();
        for (Item item:mItemList){
            mNewList.add(item.copy());
        }
        Section section = new Section(mHeader.copy(),mNewList,hasBefore,hasAfter,isFold,isLocked);
        section.setLoadAfterError(isLoadAfterError);
        section.setLoadBeforeError(isLoadBeforeError);
        return section;
    }

    public void cloneStatusTo(Section section){
        section.hasBefore = hasBefore;
        section.hasAfter = hasAfter;
        section.isFold = isFold;
        section.isLocked = isLocked;
        section.isLoadAfterError = isLoadAfterError;
        section.isLoadBeforeError = isLoadBeforeError;
    }

}
