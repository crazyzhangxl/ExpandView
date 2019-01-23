package com.apple.expandview.read;

import android.content.Context;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.expandview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crazyZhangxl on 2019/1/20.
 * Describe:
 */
public class ReadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 多布局
    private static final int ITEM_TYPE_SECTION_HEADER = 0;
    private static final int ITEM_TYPE_SECTION_ITEM = 1;
    private static final int ITEM_TYPE_SECTION_LOADING = 2;


    private List<Section> mLastData = new ArrayList<>();
    private List<Section> mData = new ArrayList<>();

    private SparseArray<Integer> mSectionIndex = new SparseArray<>();
    private SparseArray<Integer> mItemIndex = new SparseArray<>();


    private Context mContext;

    public void setData(List<Section> mSectionList){
        mData.clear();
        mData.addAll(mSectionList);
        diff(true);
    }

    /**
     * @param retValue 是否需要重新创建
     */
    private void diff(boolean retValue){
        MydiffCallback mydiffCallback = new MydiffCallback(mLastData, mData);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(mydiffCallback, false);
        // 是有必要的
        mydiffCallback.generateIndex(mData, mSectionIndex, mItemIndex);
        diffResult.dispatchUpdatesTo(this);
        // 只是将数据更新罢了啊
        if (retValue){
            mLastData.clear();
            for (Section section:mData){
               mLastData.add(section.copy());
            }
        }else {
            for (int i=0;i<mData.size();i++){
                Section section = mData.get(i);
                section.cloneStatusTo(mLastData.get(i));
            }
        }
    }

    /**
     * 构造方法 --
     * @param context
     */
    public ReadAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == ITEM_TYPE_SECTION_HEADER){
            return new SectionHeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.item_section_header,viewGroup,false));
        }else if (i == ITEM_TYPE_SECTION_ITEM){
            return new SectionItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_section_item,viewGroup,false));
        }
        // 默认返回的是item
        return new SectionHeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.item_section_header,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        // 更具i获得类型
        int sectionIndex = mSectionIndex.get(i);
        int itemIndex = mItemIndex.get(i);
        Section section = mData.get(sectionIndex);
        switch (itemIndex){
            case MydiffCallback.ITEM_INDEX_LOAD_AFTER:
                break;
                case MydiffCallback.ITEM_INDEX_LOAD_BEFORE:
                    break;
                    case MydiffCallback.ITEM_INDEX_SECTION_HEADER:
                        SectionHeaderHolder headerHolder = (SectionHeaderHolder) viewHolder;
                        headerHolder.mIvArrow.setRotation(section.isFold()?90f:270f);
                        headerHolder.mTvContent.setText(section.getHeader().getTitle());
                        headerHolder.itemView.setOnClickListener(new HeadClickListener(headerHolder,viewHolder.getAdapterPosition()));
                        break;
                        default:
                            final SectionItemHolder itemHolder = (SectionItemHolder) viewHolder;
                            // 真的牛逼因为那个itemIndex就是对应的下标 卧槽
                            itemHolder.mTvContent.setText(section.getItemList().get(itemIndex).getContent());
                            break;

        }
    }

    private class HeadClickListener implements View.OnClickListener{
        private SectionHeaderHolder mSectionHeaderHolder;
        private int position;

        public HeadClickListener(SectionHeaderHolder sectionHeaderHolder, int position) {
            mSectionHeaderHolder = sectionHeaderHolder;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (position != RecyclerView.NO_POSITION){
                onItemClick(mSectionHeaderHolder,position);
            }
        }
    }

    private void onItemClick(SectionHeaderHolder headerHolder, int adapterPosition) {
        int itemIndex = mItemIndex.get(adapterPosition);
        if (itemIndex == MydiffCallback.ITEM_INDEX_SECTION_HEADER){
            Log.e("刷新", "onItemClick: "+adapterPosition );
            toggleFold(adapterPosition);
        }
    }

    private void toggleFold(int adapterPosition) {
        Section section = mData.get(mSectionIndex.get(adapterPosition));
        section.setFold(!section.isFold());
        diff(false);
    }

    @Override
    public int getItemCount() {
        return null == mItemIndex?0:mItemIndex.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("item", mItemIndex.size()+"  详细情况 = "+mItemIndex.toString());
        int layout = mItemIndex.get(position);
        int itemType;
        switch (layout){
            case MydiffCallback.ITEM_INDEX_LOAD_AFTER:
                itemType = ITEM_TYPE_SECTION_LOADING;
                break;
                case MydiffCallback.ITEM_INDEX_LOAD_BEFORE:
                    itemType = ITEM_TYPE_SECTION_LOADING;
                    break;
                    case MydiffCallback.ITEM_INDEX_SECTION_HEADER:
                        itemType = ITEM_TYPE_SECTION_HEADER;
                        break;
                        default:
                            itemType = ITEM_TYPE_SECTION_ITEM;
                            break;
        }
        return itemType;
    }

    public interface ActionListener {
        void loadMore(Session session, boolean loadBefore);
        void scrollToPosition(int position,boolean underSection, boolean forceInScreen);
        RecyclerView.ViewHolder findViewHolderForAdapterPosition(int position);
        void requestChildFocus(View view);
    }
}
