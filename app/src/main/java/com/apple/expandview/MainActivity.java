package com.apple.expandview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.apple.expandview.diff.DiffActivity;
import com.apple.expandview.one.EpLvOneActivity;
import com.apple.expandview.read.ReadActivity;

/**
 * @author crazyZhangxl on 2019-1-20 10:43:45.
 * Describe:
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnLvOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EpLvOneActivity.class));
            }
        });

        findViewById(R.id.btnDiff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DiffActivity.class));
            }
        });

        findViewById(R.id.btnRead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReadActivity.class));
            }
        });
    }
}
