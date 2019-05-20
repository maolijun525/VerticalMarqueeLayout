package com.verticalmarquee.maomao.verticalmarqueedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VerticalMarqueeLayout marqueeRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marqueeRoot = findViewById(R.id.marquee_root);
        initData();
    }


    private void initData() {
        int count = 5;
        List<View> views = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < count; i++) {
            views.add(inflateView(inflater, marqueeRoot, "这是标题" + i, "这里是第" + i + "条内容"));
        }
        marqueeRoot.setViewList(views);
    }

    private View inflateView(LayoutInflater inflater, VerticalMarqueeLayout marqueeRoot, String name, String desc) {
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }
        View view = inflater.inflate(R.layout.marquee_item, marqueeRoot, false);
        TextView viewName = view.findViewById(R.id.marquee_name);
        TextView viewDesc = view.findViewById(R.id.marquee_desc);
        viewName.setText(name);
        viewDesc.setText(desc);
        return view;
    }
}
