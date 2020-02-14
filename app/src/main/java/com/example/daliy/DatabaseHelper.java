package com.example.daliy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.TABLE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists daily("+
                "id integer primary key autoincrement,"+
                "cost_title varchar,"+
                "cost_date varchar,"+
                "cost_type varchar,"+
                "cost_money varchar)");
    }


    public void insertCost(CostBean costBean){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(Constants.COST_TITLE,costBean.costTitle);
        cv.put(Constants.COST_DATE,costBean.costDate);
        cv.put(Constants.COST_MONEY,costBean.costMoney);
        cv.put(Constants.COST_TYPE,costBean.costType);
        database.insert(Constants.TABLE_NAME,null,cv);
    }

    public void updateCost(CostBean costBean){
        SQLiteDatabase database=getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(Constants.COST_DATE,costBean.costDate);
        cv.put(Constants.COST_TYPE,costBean.costType);
        cv.put(Constants.COST_MONEY,costBean.costMoney);
        cv.put(Constants.COST_TITLE,costBean.costTitle);

        database.update(Constants.TABLE_NAME,cv,"id='"+costBean.id+"'",null);
    }
    public Cursor getAllCost(){

        SQLiteDatabase database=getWritableDatabase();
        return database.query(Constants.TABLE_NAME,null,null,null,null,null,"cost_date asc");
    }

    public Cursor getCost(String key){
        SQLiteDatabase database=getWritableDatabase();
        return database.query(Constants.TABLE_NAME,null,"cost_title like '%"+key+"%'",null,null,null," cost_date asc");
    }

    public Cursor getCostById(String key){
        SQLiteDatabase database=getWritableDatabase();
        return database.query(Constants.TABLE_NAME,null,"id= '"+key+"'",null,null,null,null);
    }

    public void deleteById(int id){
        SQLiteDatabase database=getWritableDatabase();
        database.delete(Constants.TABLE_NAME,"id= '"+id+"'",null);
    }

    public void deleteAllCost(){
        SQLiteDatabase database=getWritableDatabase();
        database.delete(Constants.TABLE_NAME,null,null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
