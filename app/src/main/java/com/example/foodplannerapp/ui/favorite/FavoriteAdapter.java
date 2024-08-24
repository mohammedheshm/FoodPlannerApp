package com.example.foodplannerapp.ui.favorite;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;

import java.util.List;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<MealsItem> itemsList;
    FavoriteAdapter.FavoriteAdapterActions favoriteAdapterActions;
    public MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<Boolean>(false);
    private Context context;


    public FavoriteAdapter(Context context, List<MealsItem> itemsList, FavoriteAdapter.FavoriteAdapterActions favoriteAdapterActions) {
        this.itemsList = itemsList;
        this.context = context;
        this.favoriteAdapterActions = favoriteAdapterActions;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorit_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        MealsItem item = itemsList.get(position);
        holder.tvName.setText(item.getStrMeal());
        holder.tvCategory.setText(item.getStrCategory());
        holder.tvArea.setText(item.getStrArea());
        Glide.with(context).load(item.getStrMealThumb()).into(holder.imagefav);
        holder.removeBtn.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(context.getResources().getString(R.string.delete_favorite));
            alertDialog.setMessage(context.getResources().getString(R.string.delete_title));
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton(context.getResources().getString(R.string.dialog_positive_button), (dialog, which) -> favoriteAdapterActions.onCardClicked(item, position));
            alertDialog.setNegativeButton(context.getResources().getString(R.string.dialog_negative_button), (dialog, which) -> dialog.cancel());
            AlertDialog dialog = alertDialog.create();
            dialog.show();

        });

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCategory, tvArea;
        ImageView removeBtn;
        ImageView imagefav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.nameDetailsTextView);
            tvArea = itemView.findViewById(R.id.countryTextView);
            imagefav = itemView.findViewById(R.id.mealImageView);
            tvCategory = itemView.findViewById(R.id.categoryTextView);
            removeBtn = itemView.findViewById(R.id.addToFavoriteButton);

        }
    }

    public interface FavoriteAdapterActions {
        public void onCardClicked(MealsItem item, int position);
    }


}
