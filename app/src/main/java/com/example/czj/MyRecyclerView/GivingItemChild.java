package com.example.czj.MyRecyclerView;

public class GivingItemChild implements Comparable<GivingItemChild> {
    private String name; //随礼的人的名字
    private int year,month,day,price,id = 0; //年，月，日，金额，项目的编号
    public GivingItemChild(String name,int price,int year,int month,int day){
        this.name = name;
        this.price = price;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public int getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }
    
    //实现排序
    @Override
    public int compareTo(GivingItemChild o) {
        return this.month==o.month?this.day-o.day:this.month-o.month;
    }
}
