package com.example.mappicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Data;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {

    private Context ctx;
    private List<Data> placeList;
    private ClickListener click;

    public Adapter(Context ctx, List<Data> placesList,ClickListener clickListener) {
        this.ctx = ctx;
        this.placeList = placesList;
        this.click=clickListener;

    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.places_data,null);
        return new AdapterViewHolder(view,click);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Data data = placeList.get(position);
        holder.nm.setText(data.getName());
        holder.xi.setText(data.getX());
        holder.yi.setText(data.getY());
        Picasso.get().load(data.getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(data.getImage()).into(holder.imageView);

            }
        });


    }

    @Override
    public int getItemCount() {
        return placeList.size();

    }

    class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView nm,xi,yi;
        ClickListener clickListener;
        public AdapterViewHolder(@NonNull View itemView,ClickListener clickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nm = itemView.findViewById(R.id.name);
            xi=itemView.findViewById(R.id.xloc);
            yi=itemView.findViewById(R.id.yloc);
            itemView.setOnClickListener(this);
            this.clickListener=clickListener;
        }


        @Override
        public void onClick(View v) {
            clickListener.onPlaceClick(getAdapterPosition());
        }
    }

    public interface ClickListener
    {
        void onPlaceClick(int position);
    }

}
