package com.apple.expandview.diff;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

/**
 * @author crazyZhangxl on 2019/1/21.
 * Describe:
 */
public class ManDifCallback extends DiffUtil.Callback {
    private List<Man> mNewManList,mOldMainList;

    public ManDifCallback( List<Man> oldMainList,List<Man> newManList) {
        mNewManList = newManList;
        mOldMainList = oldMainList;
    }

    @Override
    public int getOldListSize() {
        return mOldMainList != null?mOldMainList.size():0;
    }

    @Override
    public int getNewListSize() {
        return mNewManList != null?mNewManList.size():0;
    }

    /**
     *
     * @param i   old
     * @param i1  new
     * @return
     */
    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return mOldMainList.get(i).getId() == mNewManList.get(i1).getId();
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return mOldMainList.get(i).getName().equals(mNewManList.get(i1).getName());
    }
}
