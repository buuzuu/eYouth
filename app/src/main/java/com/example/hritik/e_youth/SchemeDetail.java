package com.example.hritik.e_youth;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hritik.e_youth.Common.Common;

public class SchemeDetail extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton bookMark;
    private ImageView scheme_img;
    private TextView scheme_description,scheme_name;
    private static final String TAG = "SchemeDetail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_detail);
        collapsingToolbarLayout=findViewById(R.id.collapsing);
        scheme_img=findViewById(R.id.scheme_img);
        scheme_name=findViewById(R.id.scheme_name);
        scheme_description=findViewById(R.id.scheme_description);
        bookMark=findViewById(R.id.bookMark);

        //Bundle data
        int schemePosition= getIntent().getIntExtra("schemePosition",0);
        Log.d(TAG, "onClick: --schemePosition "+schemePosition);
        int schemeNumber=getIntent().getIntExtra("schemeNumber",0);
        Log.d(TAG, "onClick: --schemeNumber "+schemeNumber);



        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        bookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view,"Bookmarked !",Snackbar.LENGTH_SHORT).show();
            //    Snackbar.make(view, Html.fromHtml("<font color=\"#1e871c\">Bookmarked ! </font>"),Snackbar.LENGTH_SHORT).show();


                Snackbar snack = Snackbar.make(view,"Bookmarked !", Snackbar.LENGTH_SHORT);
                View view2 = snack.getView();
                TextView tv = view2.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(getResources().getColor(R.color.colorAccent));
                tv.setTextSize(15);
                snack.show();
            }
        });
        scheme_img.setImageResource(R.drawable.image2);

        if (schemePosition==1){
            collapsingToolbarLayout.setTitle(Common.kisan_schemes[schemeNumber]);
            scheme_description.setText(Common.kisan_schemes_details[schemeNumber]);
            scheme_name.setText(Common.kisan_schemes[schemeNumber]);
        }
        if (schemePosition==4){
            collapsingToolbarLayout.setTitle(Common.skill_schemes[schemeNumber]);
            scheme_description.setText(Common.skill_schemes_details[schemeNumber]);
            scheme_name.setText(Common.skill_schemes[schemeNumber]);
        }
        if (schemePosition==3){
            collapsingToolbarLayout.setTitle(Common.village_schemes[schemeNumber]);
            scheme_description.setText(Common.village_schemes_details[schemeNumber]);
            scheme_name.setText(Common.village_schemes[schemeNumber]);
        }
}}
