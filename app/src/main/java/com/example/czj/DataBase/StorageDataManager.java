package com.example.czj.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.example.czj.MyRecyclerView.EarningItem;
import com.example.czj.MyRecyclerView.GivingItemChild;
import com.example.czj.MyRecyclerView.GivingItemParent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//主要是存放送礼和收礼的
/*

收礼保存：年月日+姓名+金额

 */
public class StorageDataManager {
    SQLiteDatabase db;
    public StorageDataManager(){
        //假如文件夹不存在，直接openorcreate会报错
        File file = new File("/data/data/com.example.czj/databases");
        if(!file.exists()){
            file.mkdirs();
        }
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.czj/databases/DataBase.db",null);
        //创建两个表
        String sqlCommand= "create table GivingTabel(_id integer primary key autoincrement," +
                "name text," +
                "amount INTEGER," +
                "year INTEGER," +
                "month INTEGER," +
                "day INTEGER)";
        try{
            db.rawQuery("select count(1) from GivingTabel",null);
        }catch (Exception e){
            //利用查询不到就抛出错误检查表是否存在
            db.execSQL(sqlCommand);//执行创建表命令
        }


        sqlCommand= "create table EarningTabel(_id integer primary key autoincrement," +
                "name text," +
                "amount INTEGER," +
                "year INTEGER," +
                "month INTEGER," +
                "day INTEGER," +
                "reason text)";
        try{
            db.rawQuery("select count(1) from EarningTabel",null);
        }catch (Exception e){
            //利用查询不到就抛出错误检查表是否存在
            db.execSQL(sqlCommand);//执行创建表命令
        }
    }
    public void insertGiving(String name,int amount,int year,int month,int day){
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.czj/databases/DataBase.db",null);
//        sqlCommand = "insert into GivingTabel(name,amount,year,month,day) values("
//                +name
//                +amount
//                +year
//                +month
//                +day
//                +")";
//         db.execSQL(sqlCommand);//插入数据
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("amount",amount);
        values.put("year",year);
        values.put("month",month);
        values.put("day",day);
        db.insert("GivingTabel",null,values);
    }

    public void insertEarning(String name,int amount,int year,int month,int day,String reason){
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.czj/databases/DataBase.db",null);
//        sqlCommand = "insert into EarningTabel(name,amount,year,month,day,reason) values("
//                +name
//                +amount
//                +year
//                +month
//                +day
//                +reason
//                +")";
//        db.execSQL(sqlCommand);//插入数据
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("amount",amount);
        values.put("year",year);
        values.put("month",month);
        values.put("day",day);
        values.put("reason",reason);
        db.insert("EarningTabel",null,values);
    }

    public void getGivingParent(SparseArray<GivingItemParent> sparseArray){
        Cursor cursor = null;
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.czj/databases/DataBase.db",null);
        cursor = db.query("GivingTabel",null,null,null,null,null,null,null);
        String name;
        int amount,year,month,day,id;
        GivingItemParent givingItemParent;
        while(cursor.moveToNext()){
            id = cursor.getInt(0);
            name = cursor.getString(1);
            amount = cursor.getInt(2);
            year = cursor.getInt(3);
            month = cursor.getInt(4);
            day = cursor.getInt(5);
            givingItemParent = sparseArray.get(year);
            if(givingItemParent == null) {
                givingItemParent = new GivingItemParent(year);
                sparseArray.append(year,givingItemParent);
            }
            givingItemParent.append(new GivingItemChild(name,amount,year,month,day)); //?这一句没有复制一份放在上面if的地方不知道会不会出bug
        }
    }


    public List<EarningItem> getEarningItemList(){
        List<EarningItem> list = new ArrayList<>();
        Cursor cursor = null;
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.czj/databases/DataBase.db",null);
        cursor = db.query("EarningTabel",null,null,null,null,null,null,null);
        String name,reason;
        int amount,year,month,day,id;
        while(cursor.moveToNext()){
            name = cursor.getString(1);
            amount = cursor.getInt(2);
            year = cursor.getInt(3);
            month = cursor.getInt(4);
            day = cursor.getInt(5);
            reason = cursor.getString(6);
            list.add(new EarningItem(name,year,month,day,amount,reason));
        }
        return list;
    }


    public void deleteGivingTable(){
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.czj/databases/DataBase.db",null);
        String sqlCommand = "drop table GivingTabel";
        try {
            db.execSQL(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEarningTable(){
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.czj/databases/DataBase.db",null);
        String sqlCommand = "drop table EarningTabel";
        try {
            db.execSQL(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }








}
