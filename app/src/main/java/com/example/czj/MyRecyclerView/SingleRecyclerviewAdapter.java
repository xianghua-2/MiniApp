package com.example.czj.MyRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.czj.R;

import java.util.List;

public class SingleRecyclerviewAdapter extends RecyclerView.Adapter<SingleRecyclerviewAdapter.MyViewHolder> {
    private List<EarningItem> myList;
    private View inflater;
    public SingleRecyclerviewAdapter(List<EarningItem> list){
        myList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_earning_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(myList.get(position).getName());
        holder.price.setText("￥ " + myList.get(position).getPrice());
        int []date = myList.get(position).getDate();
        holder.date.setText(date[0]+"/"+date[1]+"/"+date[2]);
        holder.reason.setText(myList.get(position).getReason());
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }

    @Override
    public int getItemCount() {
        return myList==null?0:myList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //这个地方加
        TextView name,date,price,reason;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.RecyViewEarningName);
            date = itemView.findViewById(R.id.RecyViewEarningDate);
            price = itemView.findViewById(R.id.RecyViewEarningPrice);
            reason = itemView.findViewById(R.id.RecyViewEarningReason);
        }
    }
}
