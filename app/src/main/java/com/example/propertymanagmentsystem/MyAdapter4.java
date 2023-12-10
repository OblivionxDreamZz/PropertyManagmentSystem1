package com.example.propertymanagmentsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.annotation.Nonnull;

public class MyAdapter4  extends RecyclerView.Adapter<MyViewHolder4>{

    private Context context;
    private List<DataClass3> dataList;

    public MyAdapter4(Context context, List<DataClass3> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item3, parent, false);
        return new MyViewHolder4(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder4 holder, int position) {

        holder.name.setText(dataList.get(position).getName());
        holder.client.setText(dataList.get(position).getClient());
        holder.contact.setText(dataList.get(position).getContact());

        holder.recCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactClient.class);
                intent.putExtra("client", dataList.get(holder.getAdapterPosition()).getClient());
                intent.putExtra("contact", dataList.get(holder.getAdapterPosition()).getContact());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class MyViewHolder4 extends RecyclerView.ViewHolder{


    TextView client, name, contact;
    CardView recCard3;

    public MyViewHolder4(@Nonnull View itemView){
        super(itemView);

        recCard3 = itemView.findViewById(R.id.recCard3);
        client = itemView.findViewById(R.id.client);
        name = itemView.findViewById(R.id.name);
        contact = itemView.findViewById(R.id.contact);

    }

}


