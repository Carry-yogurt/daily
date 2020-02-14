package com.example.daliy;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private List<CostBean> costBeanList;
    private DatabaseHelper helper;
    private String selectText="oo";
    CostAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        costBeanList=new ArrayList<>();
        helper=new DatabaseHelper(this);
        final ListView costList=findViewById(R.id.lv_main);
        initCostData();
        mAdapter = new CostAdapter(this, costBeanList);
        costList.setAdapter(mAdapter);
        costList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CostBean costBean=costBeanList.get(position);
                //CostBean bean= (CostBean) mAdapter.getItem(costBean.id);
                Intent intent=new Intent(MainActivity.this,Delete_UpdateActivity.class);
                intent.putExtra("id",costBean.id);
                startActivityForResult(intent,1);

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
                View viewDialog =inflater.inflate(R.layout.new_cost,null);
                final EditText cost_title=viewDialog.findViewById(R.id.et_cost_title);
                final EditText cost_money=viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker cost_date=viewDialog.findViewById(R.id.dp_cost_date);
                final Spinner cost_type=viewDialog.findViewById(R.id.sp_type);
                cost_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectText=cost_type.getSelectedItem().toString();
                        Log.d(TAG, "onItemSelected: +选择运行了");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                builder.setTitle("新账目");
                builder.setView(viewDialog);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(cost_title.getText())){
                            Toast.makeText(MainActivity.this,"备注为空",Toast.LENGTH_SHORT).show();
                            return;
                        }else if(TextUtils.isEmpty(cost_money.getText())){
                            Toast.makeText(MainActivity.this,"金额为空",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        CostBean costBean=new CostBean();
                        costBean.costType=selectText;
                        Log.d(TAG, "onClick: "+selectText);
                        costBean.costTitle=cost_title.getText().toString();
                        costBean.costMoney=cost_money.getText().toString();
                        costBean.costDate=cost_date.getYear()+"-"+(cost_date.getMonth()+1)+"-"+cost_date.getDayOfMonth();
                        helper.insertCost(costBean);
                        costBeanList.add(costBean);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.create().show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == 1 && resultCode == 2) {
            //String result = data.getStringExtra("result");
            costBeanList.clear();
            initCostData();
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initCostData() {
//        helper.deleteAllCost();
//        for(int i=0;i<6;i++) {
//
//            CostBean cb=new CostBean();
//            cb.costDate="12-12";
//            cb.costMoney="50";
//            cb.costTitle=i+"heih";
//            helper.insertCost(cb);
//        }
        Cursor cursor = helper.getAllCost();
        if(cursor!=null){
            while (cursor.moveToNext()){
                CostBean costBean=new CostBean();
                costBean.id=cursor.getInt(cursor.getColumnIndex("id"));
                costBean.costType=cursor.getString(cursor.getColumnIndex("cost_type"));
                costBean.costTitle=cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate=cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney=cursor.getString(cursor.getColumnIndex("cost_money"));
                costBeanList.add(costBean);
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chart) {

            Intent intent = new Intent(MainActivity.this,ChartActivity.class);
            intent.putExtra("cost_list", (Serializable) costBeanList);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.delete_allData){
            helper.deleteAllCost();
            costBeanList.clear();
            mAdapter.notifyDataSetChanged();
            return true;
        }
        else if(id==R.id.query){
            Intent intent = new Intent(MainActivity.this,QueryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
