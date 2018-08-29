package com.swapniljain.bakingapp.utility;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.model.Recipe;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    final private List<Recipe> mRecipeList;
    private static RecipeClickListener mOnClickListener;
    int[] drawableIds = {R.drawable.nutella_pie, R.drawable.brownies, R.drawable.yellow_cake, R.drawable.cheese_cake};

    public RecipeListAdapter(List<Recipe> recipes, RecipeClickListener onClickListener) {
        mRecipeList = recipes;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForRecipeItem = R.layout.list_item_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForRecipeItem, parent, shouldAttachToParentImmediately);
        RecipeListAdapter.RecipeViewHolder viewHolder = new RecipeListAdapter.RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.recipeTextView.setText(recipe.getRecipeName());
        if (position < drawableIds.length) {
            holder.recipeImageView.setImageResource(drawableIds[position]);
        }

    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public interface RecipeClickListener {
        public void onListItemClick(int clickedItemPosition);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recipeImageView;
        TextView recipeTextView;

        public RecipeViewHolder (View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            recipeImageView = itemView.findViewById(R.id.iv_recipe_image);
            recipeTextView = itemView.findViewById(R.id.tv_recipe_name);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

}
