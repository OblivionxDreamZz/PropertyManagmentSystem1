package com.example.propertymanagmentsystem;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.annotation.Nonnull;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDataImage()).into(holder.dataImage);
        holder.name.setText(dataList.get(position).getName());
        holder.listingPrice.setText(dataList.get(position).getListingPrice());
        holder.additionalInfo.setText(dataList.get(position).getAdditionalInfo());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("name", dataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getPropertyDescription());
                intent.putExtra("AdditionalInfo", dataList.get(holder.getAdapterPosition()).getAdditionalInfo());
                intent.putExtra("Address", dataList.get(holder.getAdapterPosition()).getAddress());
                intent.putExtra("Price", dataList.get(holder.getAdapterPosition()).getListingPrice());
                intent.putExtra("Key", dataList.get(holder.getAdapterPosition()).getKey());


                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}



class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView dataImage;
    TextView name, listingPrice, additionalInfo;
    CardView recCard;

    public MyViewHolder(@Nonnull View itemView){
        super(itemView);

        dataImage = itemView.findViewById(R.id.dataImage);
        recCard = itemView.findViewById(R.id.recCard);
        listingPrice = itemView.findViewById(R.id.listingPrice);
        name = itemView.findViewById(R.id.name);
        additionalInfo = itemView.findViewById(R.id.additionalInfo);

    }

}
