package com.example.hritik.e_youth.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hritik.e_youth.Common.Common;
import com.example.hritik.e_youth.R;
import com.example.hritik.e_youth.SchemeDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SchemeCategoryAdapter extends RecyclerView.Adapter<SchemeCategoryAdapter.ViewHolder> {

    private int schemeNumber=0;
    private Context context;
    private static final String TAG = "SchemeCategoryAdapter";

    int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};

    public SchemeCategoryAdapter(Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public SchemeCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scheme_category_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemeCategoryAdapter.ViewHolder viewHolder, int i) {

        viewHolder.schemeName.setText(Common.schemeNames[i]);
        viewHolder.schemeImage.setImageResource(images[i]);


    }

    @Override
    public int getItemCount() {
        return Common.schemeNames.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView schemeImage;
        public TextView schemeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            schemeImage = itemView.findViewById(R.id.schemeImage);
            schemeName = itemView.findViewById(R.id.schemeName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    setUpDialog(position);

                }
            });
        }
    }

    private void setUpDialog(final int position) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Scheme");
        builder.setCancelable(true);

        int checkedItem = 0; // cow

        if (position == 1) {
            builder.setSingleChoiceItems(Common.kisan_schemes, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // user checked an item
                    schemeNumber=which;
                    Log.d(TAG, "onClick: "+which);

                }
            });
        } else if (position == 4) {
            builder.setSingleChoiceItems(Common.skill_schemes, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // user checked an item
                    schemeNumber=which;
                    Log.d(TAG, "onClick: "+which);

                }
            });
        }else if (position==3){
            builder.setSingleChoiceItems(Common.village_schemes, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // user checked an item
                    schemeNumber=which;
                    Log.d(TAG, "onClick: "+which);

                }
            });
        }
        else {
            builder.setSingleChoiceItems(Common.schemeNames, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // user checked an item
                    schemeNumber=which;
                    Log.d(TAG, "onClick: "+which);

                }
            });
        }


        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
                Toast.makeText(context, "Got it.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,SchemeDetail.class);
                intent.putExtra("schemePosition",position);
                intent.putExtra("schemeNumber",schemeNumber);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
