package com.example.daliy;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QueryActivity extends Activity {
    EditText queryKey;
    Button doResearch;
    DatabaseHelper helper;
    ListView listView;
    List<CostBean> list;
    //private String queryKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_layout);
        listView=findViewById(R.id.lv_query);
        queryKey = findViewById(R.id.et_queryKey);
        doResearch = findViewById(R.id.bt_do_research);
        helper=new DatabaseHelper(this);
        list=new ArrayList<>();
        initListener();
    }

    private void initListener() {
        doResearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(queryKey.getText())){
                    Toast.makeText(QueryActivity.this,"没有目标",Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor cursor = helper.getCost(queryKey.getText().toString());
                if(cursor!=null){
                    while (cursor.moveToNext()){
                        CostBean costBean=new CostBean();
                        costBean.costType=cursor.getString(cursor.getColumnIndex("cost_type"));
                        costBean.costTitle=cursor.getString(cursor.getColumnIndex("cost_title"));
                        costBean.costDate=cursor.getString(cursor.getColumnIndex("cost_date"));
                        costBean.costMoney=cursor.getString(cursor.getColumnIndex("cost_money"));
                        list.add(costBean);
                    }
                    cursor.close();
                }

                listView.setAdapter(new CostAdapter(QueryActivity.this,list));
            }
        });
    }
}
