package com.apple.expandview.diff;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apple.expandview.R;

import java.util.List;

/**
 * @author crazyZhangxl on 2019/1/21.
 * Describe:
 */
public class DifAdapter extends RecyclerView.Adapter<DifAdapter.ViewHolder>{
    private List<Man> mManList;
    private Context mContext;

    public DifAdapter(List<Man> manList, Context context) {
        mManList = manList;
        mContext = context;
    }

    public List<Man> getManList() {
        return mManList;
    }

    public void setManList(List<Man> manList) {
        mManList.clear();
        mManList.addAll(manList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_one_group, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Man man = mManList.get(i);
        viewHolder.mTextName.setText(man.getName());
    }

    @Override
    public int getItemCount() {
        if (null != mManList){
            return mManList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextName =  itemView.findViewById(R.id.tvGroupName);
        }
    }
}
