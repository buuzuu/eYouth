package com.example.hritik.e_youth.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hritik.e_youth.R;

import java.util.List;

public class OnlineStoreAdapter extends RecyclerView.Adapter<OnlineStoreAdapter.ViewHolder> {

    private Context context;
    private List<String> product_Names;
    private List<String> product_price;
    private List<String> product_detail;

    public OnlineStoreAdapter(Context context, List<String> product_Names, List<String> product_price, List<String> product_detail) {
        this.context = context;
        this.product_Names = product_Names;
        this.product_price = product_price;
        this.product_detail = product_detail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_online_store,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.productName.setText(product_Names.get(i));
        viewHolder.productPrice.setText(product_price.get(i));
        viewHolder.productDetail.setText(product_detail.get(i));
    }

    @Override
    public int getItemCount() {
        return product_Names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName,productPrice,productDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDetail = itemView.findViewById(R.id.productDescription);
        }
    }
}
