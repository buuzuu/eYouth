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
    List<Integer> image = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.online_store_list,container,false);
        addData();
        rv = view.findViewById(R.id.online_store_rv);
        rv.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        rv.setAdapter(new OnlineStoreAdapter(view.getContext(),names,price,detail,image));



        return view;
    }

    private void addData() {

        names.add("Hand Bag");
        names.add("Flower Pot");
        names.add("God Sculpture");
        names.add("Lamp");
        names.add("Pottery");
        names.add("Pickle");



        price.add("₹ 100");
        price.add("₹ 60");
        price.add("₹ 50");
        price.add("₹ 150");
        price.add("₹ 75");
        price.add("₹ 150");




        detail.add("A simple carry bag made from jute");
        detail.add("Beautiful flower stand");
        detail.add("Lord Ganesha sculpture.");
        detail.add("Celling lamp with multi colors. ");
        detail.add("Colorful potts");
        detail.add("One Kilogram Vegetable pickle");


        image.add(R.drawable.weaving);
        image.add(R.drawable.basket);
        image.add(R.drawable.sclupture);
        image.add(R.drawable.lamp);
        image.add(R.drawable.pottery1);
        image.add(R.drawable.pickle);

    }
}
