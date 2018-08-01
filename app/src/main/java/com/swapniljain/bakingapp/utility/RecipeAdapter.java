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

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    final private List<Recipe> mRecipeList;
    private static RecipeClickListener mOnClickListener;

    public RecipeAdapter(List<Recipe> recipes, RecipeClickListener onClickListener) {
        mRecipeList = recipes;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForRecipeItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForRecipeItem, parent, shouldAttachToParentImmediately);
        RecipeAdapter.RecipeViewHolder viewHolder = new RecipeAdapter.RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.recipeTextView.setText(recipe.getRecipeName());
        //holder.recipeImageView.setImageResource(recipe.getRecipeImage());
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
        int viewHolderPosition;

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
