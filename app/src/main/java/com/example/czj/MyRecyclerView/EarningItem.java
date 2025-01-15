package com.example.czj.MyRecyclerView;

public class EarningItem implements Comparable<EarningItem>{
    private String name,reason;
    private int year,month,day,price;

    public EarningItem(String name,int year,int month,int day,int price){
        this.name = name;
        this.month = month;
        this.day = day;
        this.year = year;
        this.price = price;
    }

    public EarningItem(String name,int year,int month,int day,int price,String reason){
        this.name = name;
        this.month = month;
        this.day = day;
        this.year = year;
        this.price = price;
        this.reason = reason;
    }

    public void setDate(int year,int month,int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int[] getDate() {
        int date[] = {year,month,day};
        return date;
    }

    public String getReason(){
        return reason;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }


    @Override
    public int compareTo(EarningItem o) {
        return this.year==o.year?(this.month==o.month?this.day-o.day:this.month-o.month):this.year-o.year;
    }
}
