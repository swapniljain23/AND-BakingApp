package com.swapniljain.bakingapp.utility;

import com.swapniljain.bakingapp.R;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeShortDescListAdapter extends RecyclerView.Adapter<RecipeShortDescListAdapter.RecipeShortDescListViewHolder> {

    final private ArrayList<String> mRecipeShortDescList;
    private static RecipeShortDescClickListener mOnClickListener;

    public RecipeShortDescListAdapter(ArrayList<String> recipeShortDesc, RecipeShortDescClickListener onClickListener) {
        mRecipeShortDescList = recipeShortDesc;
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecipeShortDescListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForRecipeItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForRecipeItem, parent, shouldAttachToParentImmediately);
        RecipeShortDescListAdapter.RecipeShortDescListViewHolder viewHolder =
                new RecipeShortDescListAdapter.RecipeShortDescListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeShortDescListViewHolder holder, int position) {
        String shortDesc = mRecipeShortDescList.get(position);
        holder.recipeShortDescTextView.setText(shortDesc);
    }

    @Override
    public int getItemCount() {
        return mRecipeShortDescList.size();
    }

    public interface RecipeShortDescClickListener {
        public void onListItemClick(int clickedItemPosition);
    }

    public class RecipeShortDescListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeShortDescTextView;
        int viewHolderPosition;

        public RecipeShortDescListViewHolder (View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            recipeShortDescTextView = itemView.findViewById(R.id.tv_recipe_short_desc);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

}
