package com.example.czj.DataBase;

import android.util.SparseArray;

import com.example.czj.MyRecyclerView.EarningItem;
import com.example.czj.MyRecyclerView.GivingItemChild;
import com.example.czj.MyRecyclerView.GivingItemParent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetDataManager {

    final int DEFAULT_BEGIN_YEAR = 2000, DEFAULT_END_YEAR = 2100;
    public List<GivingItemParent> getGivingItemParentTest(){
        List<GivingItemParent> list = new ArrayList<GivingItemParent>();
        ArrayList<GivingItemChild> childlist = new ArrayList<GivingItemChild>();
        childlist.add(new GivingItemChild("蔡泽炬",1111,2000,3,12));
        list.add(new GivingItemParent(2010,childlist));
        childlist.add(new GivingItemChild("蔡泽炬",1111,2020,3,12));
        list.add(new GivingItemParent(2012,childlist));
        childlist.add(new GivingItemChild("蔡泽炬",1111,2010,3,12));
        list.add(new GivingItemParent(2014,childlist));
        childlist.add(new GivingItemChild("蔡泽炬",1111,2040,3,12));
        list.add(new GivingItemParent(2016,childlist));
        return list;
    }

    public List<GivingItemParent> getGivingItemParent(){
        List<GivingItemParent> list = new ArrayList<GivingItemParent>();
        SparseArray<GivingItemParent> sparseArray = new SparseArray<>();

        StorageDataManager storageDataManager = new StorageDataManager();
        storageDataManager.getGivingParent(sparseArray);
        GivingItemParent givingItemParent;
        for(int i=DEFAULT_BEGIN_YEAR;i<=DEFAULT_END_YEAR;i++){
            givingItemParent = sparseArray.get(i);
            if(givingItemParent==null)
                continue;
            givingItemParent.sort();
            list.add(givingItemParent);
        }
        return list;
    }

    public List<EarningItem> getEarningitemTest(){
        List<EarningItem> list = new ArrayList<>();
        list.add(new EarningItem("czj",2020,3,12,77));
        list.add(new EarningItem("czj",2019,3,17,177));
        list.add(new EarningItem("czj",2020,3,12,767));
        list.add(new EarningItem("czj",2018,1,12,99));
        list.add(new EarningItem("czj",2020,6,2,12));
        Collections.sort(list);
        return list;
    }

    public List<EarningItem> getEarningItem(){
        StorageDataManager storageDataManager = new StorageDataManager();
        List<EarningItem> list = storageDataManager.getEarningItemList();
        Collections.sort(list);
        return list;
    }




}
