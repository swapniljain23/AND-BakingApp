package com.swapniljain.bakingapp.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientListViewHolder> {

    final private List<Ingredient> mIngredientList;

    public IngredientListAdapter(List<Ingredient> ingredients) {
        mIngredientList = ingredients;
    }

    @NonNull
    @Override
    public IngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForIngredientListItem = R.layout.ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForIngredientListItem, parent, shouldAttachToParentImmediately);
        IngredientListAdapter.IngredientListViewHolder viewHolder = new IngredientListAdapter.IngredientListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientListViewHolder holder, int position) {
        Ingredient ingredient = mIngredientList.get(position);
        Log.d("Ingredient", ingredient.toString());
        holder.ingredientName.setText(ingredient.getIngredientName());
        holder.ingredientAmount.setText(ingredient.getQuantity() + " " + ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }

    // View holder.
    public class IngredientListViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName;
        TextView ingredientAmount;
        int viewHolderPosition;

        public IngredientListViewHolder (View itemView){
            super(itemView);
            ingredientName = itemView.findViewById(R.id.tv_ingredient_name);
            ingredientAmount = itemView.findViewById(R.id.tv_ingredient_amount);
        }
    }
}
