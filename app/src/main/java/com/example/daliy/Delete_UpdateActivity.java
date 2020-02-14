package com.example.daliy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.Nullable;

public class Delete_UpdateActivity extends Activity {
    private int id;
    private CostBean costBean;
    DatabaseHelper helper;
    EditText ed_title;
    EditText ed_money;
    DatePicker dp_date;
    Button bt_delete;
    Button bt_update;
    Spinner sp_type;
    String TAG="Delete_UpdateActivity";
    String selectText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_or_update);
        helper=new DatabaseHelper(Delete_UpdateActivity.this);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",id);
        costBean=new CostBean();
        Cursor cursor=helper.getCostById(String.valueOf(id));
        Log.d(TAG, "onCreate: "+id+"sss");
        if(cursor!=null){
            cursor.moveToNext();
            costBean.costType=cursor.getString(cursor.getColumnIndex("cost_type"));
            costBean.costTitle=cursor.getString(cursor.getColumnIndex("cost_title"));
            costBean.costDate=cursor.getString(cursor.getColumnIndex("cost_date"));
            costBean.costMoney=cursor.getString(cursor.getColumnIndex("cost_money"));
        }

        initData();
        initListener();
    }

    private void initData() {
        ed_title = findViewById(R.id.et_cost1_title);
        ed_money = findViewById(R.id.et_cost1_money);
        dp_date = findViewById(R.id.dp_cost1_date);
        sp_type = findViewById(R.id.sp_1type);
        bt_delete = findViewById(R.id.bt_delete);
        bt_update = findViewById(R.id.bt_update);
        ed_title.setText(costBean.costTitle);
        ed_money.setText(costBean.costMoney);
        SpinnerAdapter apsAdapter = sp_type.getAdapter();
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectText=sp_type.getSelectedItem().toString();
                Log.d(TAG, "onItemSelected: +选择运行了");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int size = apsAdapter.getCount();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(costBean.costType, apsAdapter.getItem(i).toString())) {
                sp_type.setSelection(i,true);
                break;
            }
        }
    }

    private void initListener() {

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.deleteById(id);
                setResult(2);
                finish();
            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                costBean=new CostBean();
                costBean.id=id;
                costBean.costDate= dp_date.getYear()+"-"+(dp_date.getMonth()+1)+"-"+dp_date.getDayOfMonth();
                costBean.costTitle=ed_title.getText().toString();
                costBean.costMoney=ed_money.getText().toString();
                costBean.costType=selectText;
                helper.updateCost(costBean);
                setResult(2);
                finish();
            }
        });
    }
}
