package com.apple.expandview.one;

import java.util.List;

/**
 * @author crazyZhangxl on 2019/1/20.
 * Describe:
 */
public class EpLvOneBean {
    private String name;
    private List<EpOneChildBean> mEpOneChildBeans;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EpOneChildBean> getEpOneChildBeans() {
        return mEpOneChildBeans;
    }

    public void setEpOneChildBeans(List<EpOneChildBean> epOneChildBeans) {
        mEpOneChildBeans = epOneChildBeans;
    }
}
