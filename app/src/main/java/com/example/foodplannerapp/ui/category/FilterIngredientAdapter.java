package com.example.foodplannerapp.ui.category;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.pojo.ingredient.Ingredient;
import com.example.foodplannerapp.ui.common.Utils;
import com.example.foodplannerapp.ui.search.SearchInterface;

import java.util.ArrayList;
import java.util.List;


public class FilterIngredientAdapter extends RecyclerView.Adapter<FilterIngredientAdapter.ViewHolder> {
    private List<Ingredient> itemsList = new ArrayList<>();
    public MutableLiveData<Boolean> isHaveData = new MutableLiveData<Boolean>(false);

    private Context context;
    CategoryInterface categoryInterface;

    public FilterIngredientAdapter(Context context, CategoryInterface categoryInterface) {
        this.context = context;
        this.categoryInterface = categoryInterface;
    }

    @NonNull
    @Override
    public FilterIngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_ingredient,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilterIngredientAdapter.ViewHolder holder, int position) {
        Ingredient item = itemsList.get(position);
        holder.title.setText(item.getStrIngredient());


        Utils.loadImage(context,item.getThumnail(),holder.thumnailView);
        holder.linearLayout.setOnClickListener(view -> {
            Utils.navigatorCategoryToSearchFragment(view, SearchInterface.INGREDIENT,item.getStrIngredient());

        });



    }

    public void setItemsList(List<Ingredient> itemsList){
        this.itemsList = itemsList;
        notifyDataSetChanged();
        isHaveData.postValue(itemsList.size()>0);

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView thumnailView;

        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            thumnailView = itemView.findViewById(R.id.profile_image);
            linearLayout = itemView.findViewById(R.id.item_holder);
        }
    }


}
