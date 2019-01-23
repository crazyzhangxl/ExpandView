package com.apple.expandview.read;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crazyZhangxl on 2019/1/20.
 * Describe: diff学习 https://www.cnblogs.com/Fndroid/p/5789470.html
 *
 * 牛逼了啊 还可以做局部刷新的嘛  可以的
 */
public class MydiffCallback extends DiffUtil.Callback {
    /**
     * 头布局
     */
    public static final int ITEM_INDEX_SECTION_HEADER = -1;
    /**
     * 上加载
     */
    public static final int ITEM_INDEX_LOAD_BEFORE = -2;
    /**
     * 下加载
     */
    public static final int ITEM_INDEX_LOAD_AFTER = -3;

    private SparseArray<Integer> mOldSectionIndex = new SparseArray<>();
    private SparseArray<Integer> mOldItemIndex = new SparseArray<>();

    /**
     *  section的键值对
     */
    private SparseArray<Integer> mNewSectionIndex = new SparseArray<>();
    /**
     *  section中item的键值对
     */
    private SparseArray<Integer> mNewItemIndex = new SparseArray<>();

    private List<Section> mOldList;
    private List<Section> mNewList;

    /**
     * 构造函数传入老的以及新的list
     * @param oldList
     * @param newList
     */
    public MydiffCallback(List<Section> oldList, List<Section> newList) {
        mOldList = oldList;
        mNewList = newList;
        generateIndex(oldList,mOldSectionIndex,mOldItemIndex);
        generateIndex(newList,mNewSectionIndex,mNewItemIndex);
    }


    /**
     * 获得老数据的长度
     * @return
     */
    @Override
    public int getOldListSize() {
        return mOldSectionIndex.size();
    }

    /**
     * 获得新数据的长度
     * @return
     */
    @Override
    public int getNewListSize() {
        return mNewSectionIndex.size();
    }

    /**
     * 规则是判断同一个item 然后判断内容是否会一致呢
     *
     * 判断Item是否存在
     * @param i   oldItemPosition
     * @param i1  newItemPosition
     * @return 这里返回false直接刷了
     */
    @Override
    public boolean areItemsTheSame(int i, int i1) {
        int oldSectionIndex = mOldSectionIndex.get(i);
        int oldItemIndex = mOldItemIndex.get(i);
        Section section = mOldList.get(oldSectionIndex);

        int newSectionIndex = mNewSectionIndex.get(i1);
        int newItemIndex = mNewItemIndex.get(i1);
        Section newSection = mNewList.get(newSectionIndex);
        if (!section.getHeader().equals(newSection.getHeader())){
            return false;
        }
        if (oldItemIndex < 0 && oldItemIndex == newItemIndex) {
            return true;
        }

        if (oldItemIndex < 0 || newItemIndex < 0) {
            return false;
        }
        return section.getItemList().get(oldItemIndex).equals(newSection.getItemList().get(newItemIndex));
    }

    /**
     * 如果item存在会调用此方法 判断item的内容是否一致
     * @param i
     * @param i1
     * @return
     */
    @Override
    public boolean areContentsTheSame(int i, int i1) {

        int oldSectionIndex = mOldSectionIndex.get(i);
        int oldItemIndex = mOldItemIndex.get(i);
        Section section = mOldList.get(oldSectionIndex);

        int newSectionIndex = mNewSectionIndex.get(i1);
        Section newSection = mNewList.get(newSectionIndex);

        if (oldItemIndex == ITEM_INDEX_SECTION_HEADER){
            Log.e("刷新", (section.isFold() == newSection.isFold())+ "应该是true才对啊");
            return section.isFold() == newSection.isFold();
        }

        if (oldItemIndex == ITEM_INDEX_LOAD_BEFORE || oldItemIndex == ITEM_INDEX_LOAD_AFTER) {
            // load more 强制返回 false，这样可以通过 FolderAdapter.onViewAttachedToWindow 触发 load more
            // 强制刷新
            return false;
        }

        return true;
    }

    /**
     * 用于局部刷新-----
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

    /**
     * @param list
     * @param sectionIndex
     * @param itemIndex
     */
    public void generateIndex(List<Section> list,SparseArray<Integer> sectionIndex,SparseArray<Integer> itemIndex){
        // 填充list前先进行清除
        sectionIndex.clear();
        itemIndex.clear();


        int i =0;
        for (int j=0;j<list.size();j++){
            // 获取某一个section Bean
            Section section = list.get(j);
            // 如果该item未被锁定
            if (!section.isLocked()){
                //{0}
                sectionIndex.append(i,j);
                // 首先添加头
                // {-1}
                itemIndex.append(i,ITEM_INDEX_SECTION_HEADER);
                i++;
                // 如果未被折叠而且含有item的话
                if (!section.isFold() && section.getChildAccount() > 0){
                    // 如果有加载前部分
                    if (section.isHasBefore()){
                        //{0,0}
                        sectionIndex.append(i, j);
                        // 进行加载
                        //{-1,-2}
                        itemIndex.append(i, ITEM_INDEX_LOAD_BEFORE);
                        i++;
                    }
                    // 循环添加普通的item即可
                    for (int k=0;k<section.getChildAccount();k++) {
                        //{0,0,0}
                        sectionIndex.append(i, j);
                        //{-1,-2,0}  普通item传入的是正数
                        itemIndex.append(i, k);
                        i++;
                    }

                    // 有尾巴就添加尾巴咯
                    if (section.isHasAfter()) {
                        sectionIndex.append(i, j);
                        itemIndex.append(i, ITEM_INDEX_LOAD_AFTER);
                        i++;
                    }
                }

            }
        }

    }
}
