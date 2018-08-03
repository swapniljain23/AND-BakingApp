package com.swapniljain.bakingapp.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientListViewHolder> {

    final private List<Ingredient> mIngredientList;
    private static IngredientClickListener mOnClickListener;

    public IngredientListAdapter(List<Ingredient> ingredients, IngredientClickListener listener) {
        mIngredientList = ingredients;
        mOnClickListener = listener;
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
        holder.ingredientName.setText(ingredient.getIngredientName());
        holder.ingredientAmount.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }

    // On click listener.
    public interface IngredientClickListener {
        public void onListItemClick(int clickedItemPosition);
    }

    // View holder.
    public class IngredientListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ingredientName;
        TextView ingredientAmount;
        int viewHolderPosition;

        public IngredientListViewHolder (View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            ingredientName = itemView.findViewById(R.id.tv_recipe_short_desc);
            ingredientAmount = itemView.findViewById(R.id.tv_recipe_short_desc);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
