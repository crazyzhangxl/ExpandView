package com.apple.expandview.one;

/**
 * @author crazyZhangxl on 2019/1/20.
 * Describe:
 */
public class EpOneChildBean {
    private String childName;
    private String childState;

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildState() {
        return childState;
    }

    public void setChildState(String childState) {
        this.childState = childState;
    }

    public EpOneChildBean(String childName, String childState) {
        this.childName = childName;
        this.childState = childState;
    }
}
