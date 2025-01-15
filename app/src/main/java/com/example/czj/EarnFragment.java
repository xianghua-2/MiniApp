package com.example.czj;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.czj.DataBase.GetDataManager;
import com.example.czj.MyRecyclerView.EarningItem;
import com.example.czj.MyRecyclerView.SingleRecyclerviewAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EarnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EarnFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EarnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EarnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EarnFragment newInstance(String param1, String param2) {
        EarnFragment fragment = new EarnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_earn, container, false);
        initView(view);
        return view;
    }

    void initView(View view){
        recyclerView = view.findViewById(R.id.EarnFragmentRecyclerView);
        GetDataManager getDataManager = new GetDataManager();
        List<EarningItem> list = getDataManager.getEarningItem();
        SingleRecyclerviewAdapter myAdapter = new SingleRecyclerviewAdapter(list);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }


}