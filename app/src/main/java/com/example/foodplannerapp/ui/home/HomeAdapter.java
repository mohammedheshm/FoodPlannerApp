package com.example.foodplannerapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.ui.common.Utils;

import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<MealsItem> itemsList = new ArrayList<>();
    public MutableLiveData<Boolean> isHaveData = new MutableLiveData<Boolean>(false);
    private HomePresenter presenter;
    private Context context;
    private HomeInterface homeInterface;


    public HomeAdapter(Context context, HomeInterface homeInterface) {
        presenter = new HomePresenter(context, homeInterface);
        this.context = context;
        this.homeInterface = homeInterface;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meals_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        MealsItem item = itemsList.get(position);
        holder.foodNameTv.setText(item.getStrMeal());


        //Glide
        Utils.loadImage(context, item.getStrMealThumb(), holder.thumnailView);

        //holder.addToFavBtn.setVisibility(View.GONE);

        holder.addToFavBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    holder.addToFavBtn.setButtonDrawable(R.drawable.solid_favorite_24);
                    homeInterface.onSaveFavorite(item);
                } else {
                    holder.addToFavBtn.setButtonDrawable(R.drawable.ic_favorite_border_black_menu_24dp);
                }

            }
        });

        holder.itemHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeFragmentDirections.ActionNavigationHomeToNavigationDetails action = HomeFragmentDirections.actionNavigationHomeToNavigationDetails();
                action.setMealId(item.getIdMeal());
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    public void setItemsList(List<MealsItem> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
        isHaveData.postValue(itemsList.size() > 0);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox addToFavBtn;
        TextView foodNameTv;
        ImageView thumnailView;
        RelativeLayout itemHome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            foodNameTv = itemView.findViewById(R.id.tv_title);
            addToFavBtn = itemView.findViewById(R.id.fav_ceheck);
            thumnailView = itemView.findViewById(R.id.thumnail_image);
            itemHome = itemView.findViewById(R.id.itemHome);
        }
    }


}
