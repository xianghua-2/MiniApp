package com.example.czj;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.czj.DataBase.GetDataManager;
import com.example.czj.MyRecyclerView.GivingItemParent;
import com.example.czj.MyRecyclerView.TwoRecyclerViewAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GiveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GiveFragment newInstance(String param1, String param2) {
        GiveFragment fragment = new GiveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_give, container, false);
        initView(view);
        return view;
    }

    RecyclerView recyclerView;
    void initView(View view){
        recyclerView = view.findViewById(R.id.GiveRecyclerView);
        GetDataManager getDataManager = new GetDataManager();
        //假如数据为空这里会秒退
        List<GivingItemParent> list;
        try{
            list = getDataManager.getGivingItemParent();
        }catch (Exception e){
            list = null;
        }
        //init
        recyclerView.setAdapter(new TwoRecyclerViewAdapter(list));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //注册上下文菜单
//        registerForContextMenu(recyclerView);


    }
}