package com.example.czj.MyRecyclerView;

import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.czj.R;

import java.util.ArrayList;
import java.util.List;

public class TwoRecyclerViewAdapter extends RecyclerView.Adapter{
    private SparseArray<GivingItemParent> firstBeanSparseArray = new SparseArray<>();//父项目
    private SparseArray<GivingItemChild> secondBeanSparseArray = new SparseArray<>();//子项目
    private static final int TYPE_FIRST = 0;//第一层布局
    private static final int TYPE_SECOND = 1;//第二层布局
    //自定义监听器
    public interface OnItemOnClickListener{
        void OnItemLongOnClick(View view, int position);
    }
    private OnItemOnClickListener myLinstener;
    public void setOnItemOnClickListener(OnItemOnClickListener listener){
        myLinstener = listener;
    }

    //init
    public TwoRecyclerViewAdapter(List<GivingItemParent> list) {
        if(list == null)
            return;
        for (int i=0;i<list.size();i++){
            firstBeanSparseArray.put(i,list.get(i));
        }
    }

    public void changeAdapterData(List<GivingItemParent> list){
        firstBeanSparseArray = new SparseArray<>();
        for(int i=0;i<list.size();i++)
            firstBeanSparseArray.put(i,list.get(i));
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据ViewType实例化布局
        switch (viewType){
            case TYPE_FIRST:
                return new FirstViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_giving_parent,parent,false));
            case TYPE_SECOND:
                return new SecondViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_giving_chilid,parent,false));
        }
        return new FirstViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_giving_parent,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FirstViewHolder){
            GivingItemParent firstBean= firstBeanSparseArray.get(position);
            if(firstBean == null)
                return;
            ((FirstViewHolder) holder).yearTextView.setText(String.valueOf(firstBean.getYear()));
            ((FirstViewHolder) holder).itemsTextView.setText(String.valueOf(firstBean.getChildNum()));
        }
        else if(holder instanceof SecondViewHolder){
            GivingItemChild secondBean = secondBeanSparseArray.get(position);
            if(secondBean == null)
                return;
            ((SecondViewHolder) holder).name.setText(secondBean.getName());
            ((SecondViewHolder) holder).month.setText(String.valueOf(secondBean.getMonth()));
            ((SecondViewHolder) holder).day.setText(String.valueOf(secondBean.getDay()));
            ((SecondViewHolder) holder).money.setText("￥ " + secondBean.getPrice());
        }

/*
        //这里的position有点问题，不知道为什么
        if(myLinstener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myLinstener.OnItemLongOnClick(holder.itemView,position);
                    return false;
                }
            })
        }
*/


    }

    @Override
    public int getItemCount() {
        return firstBeanSparseArray.size()+secondBeanSparseArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (secondBeanSparseArray.get(position) != null){
            return TYPE_SECOND;
        }
        return TYPE_FIRST;
    }

    private class FirstViewHolder extends RecyclerView.ViewHolder{
        private TextView yearTextView,itemsTextView,imgTextView;
        public FirstViewHolder(final View itemView) {
            super(itemView);
            yearTextView = itemView.findViewById(R.id.RecyViewParentYear);
            itemsTextView = itemView.findViewById(R.id.RecyViewParentNumber);
            imgTextView = itemView.findViewById(R.id.RecyViewParentImg);
            //item点击事件监听
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (firstBeanSparseArray.get(getLayoutPosition()).isExpand()){
                        //设置第二级布局是否展开的flag
                        firstBeanSparseArray.get(getLayoutPosition()).setExpand(false);
                        //获取要移除的第二级布局个数
                        int addedSubNum = firstBeanSparseArray.get(getLayoutPosition()).getAddedSubNum();
                        //移除第二级布局
                        removeItems(getLayoutPosition(),addedSubNum);
                        notifyItemRangeRemoved(getLayoutPosition()+1,addedSubNum);

                        imgTextView.setText("→");
                    }else{
                        //设置第二级布局是否展开的flag
                        firstBeanSparseArray.get(getLayoutPosition()).setExpand(true);
                        //加载数据并获取载入的第二级布局个数
                        List<GivingItemChild> list = new ArrayList<>();
                        list = firstBeanSparseArray.get(getLayoutPosition()).getChildList();
                        //更新位置信息
                        int addedSubNum = setEachFlows(getLayoutPosition(),list);
                        //添加第二级布局
                        firstBeanSparseArray.get(getLayoutPosition()).setAddedSubNum(addedSubNum);
                        notifyItemRangeInserted(getLayoutPosition()+1,addedSubNum);
                        imgTextView.setText("↓");
                    }

                }
            });

            //添加长按监听
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(),"parent "+getLayoutPosition(),Toast.LENGTH_SHORT).show();
                    return false;
                }
            });



        }
    }

    private class SecondViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView name,month,day,money;
        //这里设置监听
        public SecondViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.RecyViewChildName);
            month = itemView.findViewById(R.id.RecyViewChildMonth);
            day = itemView.findViewById(R.id.RecyViewChildDay);
            money = itemView.findViewById(R.id.RecyViewChildMoney);





        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(v.getResources().getString(R.string.recyclerview_menu_title));
            menu.add(ContextMenu.NONE, 0, ContextMenu.NONE, v.getResources().getString(R.string.recyclerview_menu_delete));


        }
    }


    /**
     * 点击展开时加载相应数据
     * @param parentPosition
     * @param list
     * @return
     */
    public int setEachFlows(int parentPosition , List<GivingItemChild> list) {

        //更新position大于当前点击的position的第一级布局的item的position
        for (int i = getItemCount()-1 ; i > parentPosition ; i-- ){
            int index = firstBeanSparseArray.indexOfKey(i);
            if (index<0){
                continue;
            }
            GivingItemParent dailyFlow = firstBeanSparseArray.valueAt(index);
            firstBeanSparseArray.removeAt(index);
            firstBeanSparseArray.put(list.size()+i,dailyFlow);
        }
        //更新position大于当前点击的position的第二级布局的item的position
        for (int i = getItemCount()-1 ; i > parentPosition ; i-- ){
            int index = secondBeanSparseArray.indexOfKey(i);
            if (index<0){
                continue;
            }
            GivingItemChild eachFlow = secondBeanSparseArray.valueAt(index);
            secondBeanSparseArray.removeAt(index);
            secondBeanSparseArray.append(list.size()+i,eachFlow);
        }
        //把获取到的数据根据相应的position放入SparseArray中。
        for (int i = 0 ;i < list.size() ; i++ ){
            secondBeanSparseArray.put(parentPosition+i+1,list.get(i));
        }
        return list.size();
    }

    /**
     * 点击折叠时移除相应数据
     * @param clickPosition
     * @param addedSubNum
     */
    private void removeItems(int clickPosition,int addedSubNum){
        //更新position大于当前点击的position的第一级布局item的position
        SparseArray<GivingItemParent> temp = new SparseArray();
        for (int i = getItemCount()-1 ; i > clickPosition+addedSubNum ; i-- ){
            int index = firstBeanSparseArray.indexOfKey(i);
            if (index<0){
                continue;
            }
            GivingItemParent dailyFlow = firstBeanSparseArray.valueAt(index);
            firstBeanSparseArray.removeAt(index);
            temp.put(i-addedSubNum,dailyFlow);
        }
        for (int i=0;i<temp.size();i++ ){

            int key = temp.keyAt(i);
            firstBeanSparseArray.put(key,temp.get(key));
        }
        //更新position大于当前点击的position的第二级布局item的position
        SparseArray<GivingItemChild> temp2 = new SparseArray();
        for (int i = getItemCount()-1 ; i > clickPosition+addedSubNum ; i-- ){
            int index = secondBeanSparseArray.indexOfKey(i);
            if (index<0){
                continue;
            }
            GivingItemChild eachFlow = secondBeanSparseArray.valueAt(index);
            secondBeanSparseArray.removeAt(index);
            temp2.put(i-addedSubNum,eachFlow);
        }
        for (int i = 1; i <= addedSubNum; i++) {
            //移除被折叠的第二级布局数据
            secondBeanSparseArray.remove(clickPosition+i);
        }
        for (int i=0;i<temp2.size();i++ ){

            int key = temp2.keyAt(i);
            secondBeanSparseArray.put(key,temp2.get(key));
        }
    }


}





