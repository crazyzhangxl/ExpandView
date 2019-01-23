package com.apple.expandview.read;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.apple.expandview.R;

/**
 * @author crazyZhangxl on 2019/1/23.
 * Describe:
 */
public class SectionItemHolder extends RecyclerView.ViewHolder {
    public TextView mTvContent;
    public SectionItemHolder(@NonNull View itemView) {
        super(itemView);
        mTvContent = itemView.findViewById(R.id.tvContent);
    }
}
