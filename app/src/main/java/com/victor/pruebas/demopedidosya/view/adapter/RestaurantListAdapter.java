package com.victor.pruebas.demopedidosya.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.victor.pruebas.data.entity.RestaurantEntity;
import com.victor.pruebas.demopedidosya.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter  extends RecyclerView.Adapter {

    private List<RestaurantEntity> restaurantEntityList;
    private Context context;

    public RestaurantListAdapter(List<RestaurantEntity> restaurantEntityList, Context context) {
        this.restaurantEntityList = restaurantEntityList;
        this.context = context;
    }

    public void bindList(@NonNull List<RestaurantEntity> list) {
        if (restaurantEntityList == null) restaurantEntityList = new ArrayList<>();
        restaurantEntityList.addAll(list);
        notifyItemInserted(list.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.restaurant_item, viewGroup, false);
        return new RestaurantViewHolder(view);
    }

    private class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;

        RestaurantViewHolder(View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.et_restaurant_name);
        }

        void bind(RestaurantEntity restaurantEntity) {
            restaurantName.setText(restaurantEntity.getName());

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RestaurantEntity restaurantEntity = restaurantEntityList.get(position);
        ((RestaurantViewHolder) viewHolder).bind(restaurantEntity);
    }

    @Override
    public int getItemCount() {
        if (restaurantEntityList != null)
            return restaurantEntityList.size();
        return 0;
    }
}
