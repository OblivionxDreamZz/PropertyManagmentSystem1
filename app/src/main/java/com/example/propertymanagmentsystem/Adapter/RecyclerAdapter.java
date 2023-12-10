package com.example.propertymanagmentsystem.Adapter;

import android.content.Context;
import android.icu.lang.UProperty;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.propertymanagmentsystem.Model.PropertyModel1;
import com.example.propertymanagmentsystem.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>
{

    private Context nContext;
    private List<PropertyModel1> Property;
    private OnItemClickListener nListener;

    public RecyclerAdapter(Context context, List<PropertyModel1> uploads)
    {
        nContext = context;
        Property = uploads;
    }

    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(nContext).inflate(R.layout.activity_model, parent, false);
        return new RecyclerViewHolder(v);
    }

    public void onBindViewHolder(RecyclerViewHolder holder, int position)
    {
        PropertyModel1 currentProperty = Property.get(position);
        holder.nameTextView.setText(currentProperty.getName());
        holder.descriptionTextView.setText(currentProperty.getDescription());

        Picasso.with(nContext)
                .load(currentProperty.getImageURL())
                .fit()
                .centerCrop()
                .into(holder.propertyImageView);

    }


    public int getItemCount()
    {
        return Property.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener
    {
        public TextView nameTextView, descriptionTextView;
        public ImageView propertyImageView;

        public RecyclerViewHolder(View itemView)
        {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.propertyName);
            descriptionTextView = itemView.findViewById(R.id.propertyDescription);
            propertyImageView = itemView.findViewById(R.id.PropertyImage);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public  void onClick(View v)
        {
            if(nListener != null)
            {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION)
                {
                    nListener.onItemClick(position);
                }
            }
        }


        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
        {
            menu.setHeaderTitle("Select Action");
            MenuItem showItem = menu.add(Menu.NONE, 1, 2, "show");
            MenuItem deleteItem = menu.add(Menu.NONE, 1, 2, "Delete");

            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);

        }



        public boolean onMenuItemClick(MenuItem item)
        {
            if(nListener != null)
            {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION)
                {
                    switch (item.getItemId())
                    {
                        case 1:
                            nListener.OnShowItemClick(position);
                            return true;
                        case 2:
                            nListener.OnDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }

    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
        void OnShowItemClick(int position);
        void OnDeleteItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        nListener = listener;
    }

    private String getDateToday(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
        Date date = new Date();
        String today = dateFormat.format(date);
        return today;
    }
}
