package com.apple.expandview.read;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apple.expandview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crazyZhangxl on 2019-1-20 22:34:43.
 * Describe:
 *
 * step 1: 实现多布局 牛逼的是那个索引
 */

public class ReadActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ReadAdapter mReadAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initViews();
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mReadAdapter = new ReadAdapter(this);
        mRecyclerView.setAdapter(mReadAdapter);
        mReadAdapter.setData(getData());
    }

    // 数据
    private List<Section> getData(){
        ArrayList<Section> sections = new ArrayList<>();
        for (int i =0 ;i<10;i++){
            Header header = new Header("selection "+i);
            List<Item> items = new ArrayList<>();
            for (int j =0 ;j<8;j++){
                items.add(new Item("selection "+i+" ,item "+j));
            }
            sections.add(new Section(header,items,false,false,true,false));
        }
        return sections;
    }
}
