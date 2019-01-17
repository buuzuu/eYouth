package com.example.hritik.e_youth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hritik.e_youth.Adapter.OnlineStoreAdapter;

import java.util.ArrayList;
import java.util.List;

public class OnlineStore extends Fragment {


    private View view;
    private RecyclerView rv;
    List<String> names = new ArrayList<>();
    List<String> price = new ArrayList<>();
    List<String> detail = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.online_store_list,container,false);
        addData();
        rv = view.findViewById(R.id.online_store_rv);
        rv.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        rv.setAdapter(new OnlineStoreAdapter(view.getContext(),names,price,detail));



        return view;
    }

    private void addData() {

        names.add("Lamp");
        names.add("Light");
        names.add("Fan");
        names.add("Table");
        price.add("24$");
        price.add("4$");
        price.add("13$");
        price.add("15$");
        detail.add("I don't know.");
        detail.add("I don't know.");
        detail.add("I don't know.");
        detail.add("I don't know.");
    }
}
