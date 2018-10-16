package com.example.hritik.e_youth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hritik.e_youth.Adapter.SchemeCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class SchemePortal extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private SchemeCategoryAdapter adapter;
    private GridLayoutManager manager;
    private List<String> schemeName = new ArrayList<>();
    private List<String> schemeImage = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.scheme_portal_layout, container, false);
        recyclerView = view.findViewById(R.id.schemeRv);
        layoutManager = new LinearLayoutManager(view.getContext());

        recyclerView.setHasFixedSize(true);
        manager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SchemeCategoryAdapter(view.getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}

