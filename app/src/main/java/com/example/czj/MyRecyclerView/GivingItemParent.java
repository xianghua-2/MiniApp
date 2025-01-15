package com.example.czj.MyRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//第一层数据
public class GivingItemParent {
    private boolean isExpand = false; //是否展开
    private int addedSubNum,year,childNum; //已插入recyclerView的子项目数量，年份，子项目的数量
    private List<GivingItemChild> childList; //子项目列表

    public GivingItemParent(int year,List<GivingItemChild> list) {
        this.year = year;
        this.childList = list;
        this.childNum = list.size();
    }
    public GivingItemParent(int year){
        childList = new ArrayList<GivingItemChild>();
        this.year = year;
        this.childNum = 0;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public int getYear() {
        return year;
    }

    public void setAddedSubNum(int addedSubNum) {
        this.addedSubNum = addedSubNum;
    }

    public boolean isExpand() {

        return isExpand;
    }

    public int getAddedSubNum() {
        return addedSubNum;
    }

    public int getChildNum() {
        return childNum;
    }

    public void setChildNum(int childNum) {
        this.childNum = childNum;
    }

    public void append(GivingItemChild givingItemChild){
        childList.add(givingItemChild);
        childNum++;
    }
    public void sort(){
        if(childList!=null){
            Collections.sort(childList);
        }
    }


    public void delete(int positon){
        if(positon>=childList.size())
            return;
        childList.remove(positon);
        childNum--;
    }

    public List<GivingItemChild> getChildList() {
        return childList;
    }
}
