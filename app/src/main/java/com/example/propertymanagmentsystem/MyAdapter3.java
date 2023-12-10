package com.example.propertymanagmentsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.annotation.Nonnull;

public class MyAdapter3  extends RecyclerView.Adapter<MyViewHolder3>{

    private Context context;
    private List<DataClass2> dataList;

    public MyAdapter3(Context context, List<DataClass2> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item2, parent, false);
        return new MyViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.address.setText(dataList.get(position).getAddress());
        holder.reference.setText(dataList.get(position).getReference());

        holder.recCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity3.class);
                intent.putExtra("name", dataList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("address", dataList.get(holder.getAdapterPosition()).getAddress());
                intent.putExtra("reference", dataList.get(holder.getAdapterPosition()).getReference());
                intent.putExtra("reporting", dataList.get(holder.getAdapterPosition()).getReporting());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class MyViewHolder3 extends RecyclerView.ViewHolder{


    TextView name, reference, address;
    CardView recCard2;

    public MyViewHolder3(@Nonnull View itemView){
        super(itemView);

        recCard2 = itemView.findViewById(R.id.recCard2);
        reference = itemView.findViewById(R.id.reference);
        name = itemView.findViewById(R.id.name);
        address = itemView.findViewById(R.id.address);

    }

}
