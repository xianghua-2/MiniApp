package com.example.czj.MyToolClass;

import android.app.Application;
import android.util.SparseArray;

public class MyStaticData extends Application {
    public static int[] date = {2020,12,1}; //保存日期
    public SparseArray<Integer> givingParentId;
    public int[] getDate(){
        return date;
    }
    public MyStaticData(){
        givingParentId = new SparseArray<Integer>();
    }
    public void setDate(int year,int month,int day){
        date[0]=year;
        date[1]=month;
        date[2]=day;
    }
}
