package com.example.hritik.e_youth.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hritik.e_youth.MainActivity;
import com.example.hritik.e_youth.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import net.soulwolf.widget.materialradio.MaterialRadioGroup;
import net.soulwolf.widget.materialradio.listener.OnCheckedChangeListener;


public class FreeLancingAdapter extends RecyclerView.Adapter<FreeLancingAdapter.ViewHolder> {


    public  String[] free_lancing_title = {"Content Writer","Handricraft Designer","Stiching Work","Poetry Writer","Handicraft Designer","Pottery Maker","UI Designer"};
    public  String[] free_lancing_description = {"We want a content writer who writes the beautiful blogs. "," We want a workor who will make hand made Wood products"
            ,"We want a employee who know how to stich the antique designery.","We want a hire a  poet for our blog who knows many types of poem writing.",
            "We want a handicraft designer who know how to make handmade baskets","We want a potter for making antique pottery","Design UI/UX for website and mobile apps"};
    public int[] image = {R.drawable.content,R.drawable.handicraft,R.drawable.clothing,R.drawable.poetrywriting,R.drawable.handicraft,R.drawable.pottery,R.drawable.ui};
    private Context context;

    public FreeLancingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_free_lancing,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    viewHolder.title.setText(free_lancing_title[i]);
    viewHolder.description.setText(free_lancing_description[i]);
    viewHolder.imageView.setImageResource(image[i]);


    }

    @Override
    public int getItemCount() {
        return free_lancing_description.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
        ImageView imageView;
        MaterialRadioGroup radioGroup;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageView);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            radioGroup.clearCheck();
            radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                    switch (checkedId){

                        case R.id.radioButton1:{

                            createDialog("Your Expected Salary"," Freshers can expect around 25000\n"+"Professionals can expect around 60000",R.mipmap.money);
                            break;
                        }

                        case R.id.radioButton2:{

                            createDialog("Your Job Description","Content Writer responsibilities include conducting thorough research on industry-related topics, generating ideas for new content types and proofreading articles before publication. If you’re familiar with producing online content and have an eye for detail, we’d like to meet you. Feel free to share samples of your work or portfolio of your published articles, along with your application.\n" +
                                    "\n" +
                                    "Ultimately, you’ll deliver quality writing pieces that appeal to our audiences, attract customers and boost brand awareness.",R.mipmap.job2);
                            break;
                        }
                        case R.id.radioButton3:{
                            createDialog("Your office location ","Highlights info row image\n" +
                                    "1 Hacker Way\n" +
                                    "94025 Menlo Park, California\n"+"Highlights info row image\n" +
                                    "+1 650-543-4800",R.mipmap.location);
                            break;
                        }
                    }
                }
            });

        }

        public void createDialog(String a,String b,int c){

                            new FancyGifDialog.Builder((Activity) context)
                            .setTitle(a)
                            .setMessage(b)
                            .setNegativeBtnText("Cancel")
                            .setPositiveBtnBackground("#FF4081")
                            .setPositiveBtnText("Ok")
                            .setNegativeBtnBackground("#FFA9A7A8")
                            .setGifResource(c)
                            .isCancellable(false)
                            .OnPositiveClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {

                                }
                            })
                            .OnNegativeClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {

                                }
                            })

                            .build();

        }
    }
}
