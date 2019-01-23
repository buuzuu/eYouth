package com.example.hritik.e_youth;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestOptions;
import com.example.hritik.e_youth.Adapter.UserReviewAdapter;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

import it.chengdazhi.decentbanner.DecentBanner;

public class CurrentOffer extends Fragment {


    private View view;
    private DecentBanner decentBanner;
    private List<View> views;
    private List<String> titles;
    private SliderLayout mDemoSlider;
    ArrayList<Integer> listImage = new ArrayList<>();
    ArrayList<String> listName = new ArrayList<>();
    ArrayList<Integer> mImage = new ArrayList<>();
    ArrayList<String> mNames = new ArrayList<>();
    private RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.current_offer,container,false);
        setUpData();
        decentBanner =view.findViewById(R.id.decent_banner);
        mDemoSlider = view.findViewById(R.id.slider);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new UserReviewAdapter(view.getContext(),mNames,mImage));



        decentBanner.start(views, titles, 2, 500, R.drawable.logo);


        //SLider
        RequestOptions requestOptions =new RequestOptions();

        requestOptions.centerCrop();

        for (int i=0;i<listName.size();i++){
            TextSliderView sliderView = new TextSliderView(view.getContext());
            sliderView.image(listImage.get(i))
                        .description(listName.get(i))
                        .setRequestOption(requestOptions)
                    .setBackgroundColor(Color.WHITE)
                    //.setOnSliderClickListener((BaseSliderView.OnSliderClickListener) view.getContext())
                    .setProgressBarVisible(true);

            mDemoSlider.addSlider(sliderView);
        }
        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
      //  mDemoSlider.addOnPageChangeListener((ViewPagerEx.OnPageChangeListener) view.getContext());


        return view;
    }

    private void setUpData() {
        listImage.add(R.drawable.banner2);
        listImage.add(R.drawable.skillindia);
        listImage.add(R.drawable.banner);
        listName.add("Recommended");
        listName.add("Popular");
        listName.add("Daily");
        View view1 = getLayoutInflater().inflate(R.layout.popular_layout, null);
        View view2 = getLayoutInflater().inflate(R.layout.daily_layout, null);
        View view3 = getLayoutInflater().inflate(R.layout.recommend_layout, null);
        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);

        titles = new ArrayList<>();
        titles.add("POPULAR");
        titles.add("IMAGE");
        titles.add("RECOMMEND");



        mNames.add("Hurray ! I got a job.");
        mNames.add("Prabal");
        mNames.add("Prabal");
        mNames.add("Mahima");
        mNames.add("Lovish");
        mImage.add(R.mipmap.boy1);
        mImage.add(R.mipmap.boy);
        mImage.add(R.mipmap.boy);
        mImage.add(R.mipmap.girl);
        mImage.add(R.mipmap.man);



    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
}
