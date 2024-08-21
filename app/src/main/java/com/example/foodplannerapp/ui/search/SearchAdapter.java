package com.example.foodplannerapp.ui.search;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.ui.common.Utils;

import java.util.ArrayList;
import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<MealsItem> itemsList = new ArrayList<>();
    private SearchInterface searchInterface;
    private Context context;

    public SearchAdapter(Context context, SearchInterface searchInterface) {
        this.context = context;
        this.searchInterface = searchInterface;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        MealsItem item = itemsList.get(position);
        holder.foodNameTv.setText(item.getStrMeal());

        Utils.loadImage(context,item.getStrMealThumb(),holder.thumnailView);

        holder.btn_AddToPlane.setOnClickListener(view -> searchInterface.onSavePlane(item));

        holder.btn_addToFav.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                searchInterface.onSaveFavorite(item);
        });

        holder.itemHome.setOnClickListener(v -> {
            SearchFragmentDirections.ActionNavigationSearchToNavigationDetails action = SearchFragmentDirections.actionNavigationSearchToNavigationDetails();
            action.setMealId(item.getIdMeal());
            Navigation.findNavController(v).navigate(action);
        });
    }

    public void setItemsList(List<MealsItem> itemsList){
        this.itemsList.clear();
        this.itemsList.addAll(itemsList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatButton btn_AddToPlane;
        CheckBox btn_addToFav;
        TextView foodNameTv;
        ImageView thumnailView;
        RelativeLayout itemHome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           // btn_AddToPlane = itemView.findViewById(R.id.addTOPlanButton);
            foodNameTv = itemView.findViewById(R.id.tv_title);
            btn_addToFav = itemView.findViewById(R.id.fav_ceheck);
            thumnailView = itemView.findViewById(R.id.thumnail_image);
            itemHome=itemView.findViewById(R.id.itemHome);
        }
    }


}
