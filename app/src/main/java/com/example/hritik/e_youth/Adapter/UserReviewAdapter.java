package com.example.hritik.e_youth.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hritik.e_youth.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<Integer> mImageUrls=new ArrayList<>();

    public UserReviewAdapter(Context context, ArrayList<String> mNames, ArrayList<Integer> mImageUrls) {
        this.context = context;
        this.mNames = mNames;
        this.mImageUrls = mImageUrls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_user_review,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.textView.setText(mNames.get(i));
        viewHolder.circleImageView.setImageResource(mImageUrls.get(i));

    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.name);
        }
    }
}
