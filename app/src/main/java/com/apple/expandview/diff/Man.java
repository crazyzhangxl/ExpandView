package com.apple.expandview.diff;

/**
 * @author crazyZhangxl on 2019/1/21.
 * Describe: 含有克隆操作-----
 */
public class Man {
    private int id;
    private String name;

    public Man(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 进行克隆copy的代码
     *   for (Man bean : mDatas) {
     *     newDatas.add(bean.clone());
     *     //clone一遍旧数据 ，模拟刷新操作
     *    }
     *    进行修改等
     *  一般是从网上拉取的
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Man bean = null;
        try {
            bean = (Man) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
