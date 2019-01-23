package com.apple.expandview.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.apple.expandview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crazyZhangxl on 2019-1-20 10:49:11.
 * Describe:
 */

public class EpLvOneActivity extends AppCompatActivity {
    private ExpandableListView expandListView;
    private List<EpLvOneBean> mEpLvOneBeanList = new ArrayList<>();
    private EpLvOneAdapter mEpLvOneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_lv_one);
        initData();
        initViews();
        setAdapter();
        initListener();
    }

    private void initData() {
        EpLvOneBean epLvOneBean1 = new EpLvOneBean();
        epLvOneBean1.setName("特别关心");
        mEpLvOneBeanList.add(epLvOneBean1);

        EpLvOneBean epLvOneBean2 = new EpLvOneBean();
        epLvOneBean2.setName("我的好友");
        List<EpOneChildBean> mBeanList1 = new ArrayList<>();
        mBeanList1.add(new EpOneChildBean("奔跑的杰尼龟","4G在线"));
        mBeanList1.add(new EpOneChildBean("震动","离线"));
        mBeanList1.add(new EpOneChildBean("posion","手机在线"));
        epLvOneBean2.setEpOneChildBeans(mBeanList1);
        mEpLvOneBeanList.add(epLvOneBean2);
    }

    private void initViews() {
        expandListView = findViewById(R.id.expandListView);
    }

    private void setAdapter() {
        mEpLvOneAdapter = new EpLvOneAdapter(this, mEpLvOneBeanList);
        expandListView.setAdapter(mEpLvOneAdapter);
    }

    private void initListener() {
        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                EpOneChildBean epOneChildBean = mEpLvOneBeanList.get(groupPosition).getEpOneChildBeans().get(childPosition);
                Toast.makeText(EpLvOneActivity.this, epOneChildBean.getChildName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });



        // 设置不可点击
        expandListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(groupPosition);
                if (groupExpanded) {
                    parent.collapseGroup(groupPosition);
                } else {
                    parent.expandGroup(groupPosition, true);
                }
                mEpLvOneAdapter.setIndicatorState(groupPosition, groupExpanded);

                // 上面注释掉即可 不响应点击因为返回了true了
                return true;
            }
        });

        // 设置默认展开
        int count = expandListView.getCount();
        for (int i=0;i<count;i++){
            expandListView.expandGroup(i);
        }

    }

}
