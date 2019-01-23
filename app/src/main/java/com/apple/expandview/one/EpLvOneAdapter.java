package com.apple.expandview.one;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apple.expandview.R;

import java.util.List;

/**
 * @author crazyZhangxl on 2019/1/20.
 * Describe:
 */
public class EpLvOneAdapter extends BaseExpandableListAdapter{
    private List<EpLvOneBean> mEpLvOneBeanList;
    private Context mContext;
    private SparseArray<ImageView> mIndicators = new SparseArray<>();

    public EpLvOneAdapter(Context context,List<EpLvOneBean> epLvOneBeanList) {
        mEpLvOneBeanList = epLvOneBeanList;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        if (mEpLvOneBeanList != null){
            return mEpLvOneBeanList.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mEpLvOneBeanList != null){
            EpLvOneBean epLvOneBean = mEpLvOneBeanList.get(groupPosition);
            if (null != epLvOneBean &&  null != epLvOneBean.getEpOneChildBeans()){
                return epLvOneBean.getEpOneChildBeans().size();
            }
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mEpLvOneBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if ( null != mEpLvOneBeanList.get(groupPosition).getEpOneChildBeans()){
            return mEpLvOneBeanList.get(groupPosition).getEpOneChildBeans().get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //每个item的id是否是固定？一般为true
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (null == convertView){
            groupViewHolder = new GroupViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_one_group,parent,false);
            groupViewHolder.tvGroupName = convertView.findViewById(R.id.tvGroupName);
            groupViewHolder.tvGroupNum = convertView.findViewById(R.id.tvGroupNum);
            groupViewHolder.ivIndicator = convertView.findViewById(R.id.ivIndicator);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        EpLvOneBean epLvOneBean = mEpLvOneBeanList.get(groupPosition);
        groupViewHolder.tvGroupName.setText(epLvOneBean.getName());
        if (null != epLvOneBean.getEpOneChildBeans()) {
            groupViewHolder.tvGroupNum.setText(String.valueOf(epLvOneBean.getEpOneChildBeans().size()));
        }else {
            groupViewHolder.tvGroupNum.setText("0");
        }
        if (mIndicators.get(groupPosition) == null) {
            mIndicators.put(groupPosition, groupViewHolder.ivIndicator);
        }
        setIndicatorState(groupPosition, isExpanded);
        return convertView;
    }

    public void setIndicatorState(int groupPosition, boolean isExpanded) {
        if (isExpanded) {
            mIndicators.get(groupPosition).setImageResource(R.drawable.shape_bottom);
        } else {
            mIndicators.get(groupPosition).setImageResource(R.drawable.shape_right);
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        EpOneChildBean epOneChildBean = mEpLvOneBeanList.get(groupPosition).getEpOneChildBeans().get(childPosition);
        ChildViewHolder childViewHolder;
        if (null == convertView){
            childViewHolder = new ChildViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_one_child,parent,false);
            childViewHolder.tvChildName = convertView.findViewById(R.id.tvChildName);
            childViewHolder.tvChildState = convertView.findViewById(R.id.tvChildState);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        childViewHolder.tvChildName.setText(epOneChildBean.getChildName());
        childViewHolder.tvChildState.setText(epOneChildBean.getChildState());
        return convertView;
    }

    //二级列表中的item是否能够被选中？可以改为true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder{
        TextView tvGroupName;
        TextView tvGroupNum;
        ImageView ivIndicator;
    }

    static class ChildViewHolder{
        TextView tvChildName;
        TextView tvChildState;
    }
}
