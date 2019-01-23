package com.apple.expandview.diff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.apple.expandview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crazyZhangxl on 2019-1-21 9:52:36.
 * Describe: 给我的启发就是数据集合的处理
 *
 * 不错的Dif比较案例
 *
 * 因为你改原数据集那么匹配的数据也会改变的.那获取应该在之前比较好吧
 * https://www.jianshu.com/p/fee2fb3c27d6
 */

public class DiffActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private List<Man> mManList = new ArrayList<>();
    private List<Man> mManCloneList = new ArrayList<>();
    private List<Man> mManListOrigin = new ArrayList<>();
    private String[] names = {"张三","李四","王二","麻子","小王","小李","小白","小黑","小红","小徐","啧啧","哈哈"};
    private DifAdapter mDifAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff);
        initData();
        initViews();
        initListener();
    }

    private void initListener() {
        findViewById(R.id.btnChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DiffActivity.this, "执行了修改了啊", Toast.LENGTH_SHORT).show();
                doChanged();
            }
        });

        findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReturn();
            }
        });
    }

    private void doReturn() {
        // 这个貌似也改变了------- 没错 妹的,这里确实是改变了的 哈哈
        Log.e("dif", "doChanged-----------------: "+mManList.get(1).getName() );
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ManDifCallback(mManList, mManCloneList), true);
        diffResult.dispatchUpdatesTo(mDifAdapter);
        mDifAdapter.setManList(mManCloneList);

    }

    /**
     * 也就是不能对原来的那个数据再进行修改了的
     *
     * 好吧,那说明我不能对原数据源进行修改了哦;只能克隆咯
     */
    private void doChanged() {
        // 进行修改
        mManListOrigin.clear();
        mManListOrigin.addAll(mManCloneList);
        mManListOrigin.set(1,new Man(1,"哈哈哈哈哈哈"));
        mManListOrigin.add(new Man(12,"我已经在末尾了"));
        mManListOrigin.remove(5);
        // 调用DiffUtils帮助工具
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ManDifCallback(mManList, mManListOrigin), true);
        diffResult.dispatchUpdatesTo(mDifAdapter);
        mDifAdapter.setManList(mManListOrigin);
    }

    private void initData() {
        for (int i=0;i<10;i++){
            mManList.add(new Man(i,names[i]));
        }
        mManCloneList.addAll(mManList);
    }

    private void initViews() {
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mDifAdapter = new DifAdapter(mManList, this);
        recyclerview.setAdapter(mDifAdapter);
    }
}
