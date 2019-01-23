package com.apple.expandview.read;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apple.expandview.R;

/**
 * @author crazyZhangxl on 2019/1/23.
 * Describe:
 */
public class SectionHeaderHolder extends RecyclerView.ViewHolder{
    public TextView mTvContent;
    public ImageView mIvArrow;

    public SectionHeaderHolder(@NonNull View itemView) {
        super(itemView);
        mTvContent = itemView.findViewById(R.id.tvContent);
        mIvArrow = itemView.findViewById(R.id.ivArrow);
    }
}
