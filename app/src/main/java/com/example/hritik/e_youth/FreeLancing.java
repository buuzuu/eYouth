package com.example.hritik.e_youth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hritik.e_youth.Adapter.FreeLancingAdapter;

public class FreeLancing extends Fragment {


    private View view;
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private FreeLancingAdapter freeLancingAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.free_lancing_list,container,false);
        rv = view.findViewById(R.id.free_lancing_rv);
        layoutManager= new LinearLayoutManager(view.getContext());
        freeLancingAdapter = new FreeLancingAdapter(view.getContext());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(freeLancingAdapter);
        return view;
    }
}
