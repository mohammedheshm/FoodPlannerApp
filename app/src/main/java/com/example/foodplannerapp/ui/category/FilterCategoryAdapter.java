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
import com.example.foodplannerapp.data.pojo.category.Category;
import com.example.foodplannerapp.ui.common.Utils;
import com.example.foodplannerapp.ui.search.SearchInterface;

import java.util.ArrayList;
import java.util.List;


public class FilterCategoryAdapter extends RecyclerView.Adapter<FilterCategoryAdapter.ViewHolder> {
    private List<Category> itemsList = new ArrayList<>();
    public MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<Boolean>(false);

    private Context context;
    CategoryInterface categoryInterface;

    public FilterCategoryAdapter(Context context, CategoryInterface categoryInterface) {
        this.context = context;
        this.categoryInterface = categoryInterface;
    }

    @NonNull
    @Override
    public FilterCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilterCategoryAdapter.ViewHolder holder, int position) {
        Category item = itemsList.get(position);
        holder.title.setText(item.getStrCategory());

        Utils.loadImage(context, item.getThumbail(), holder.thumnailView);

        holder.linearLayout.setOnClickListener(view -> {
            Utils.navigatorCategoryToSearchFragment(view, SearchInterface.CATEGORY, item.getStrCategory());

        });

    }

    public void setItemsList(List<Category> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
        mutableLiveData.postValue(itemsList.size() > 0);

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
