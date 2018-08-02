package com.swapniljain.bakingapp.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.utility.RecipeShortDescListAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeDetailActivityFragment extends Fragment implements RecipeShortDescListAdapter.RecipeShortDescClickListener{

    public static String RECIPE_SHORT_DESC_LIST_EXTRA = "recipe_short_desc_list_extra";

    private ArrayList<String> mRecipeShortDescList;

    public RecipeDetailActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mRecipeShortDescList = getArguments().getStringArrayList(RECIPE_SHORT_DESC_LIST_EXTRA);
        } else {
            mRecipeShortDescList = savedInstanceState.getStringArrayList(RECIPE_SHORT_DESC_LIST_EXTRA);
        }

        final View mainView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        RecyclerView recyclerView = mainView.findViewById(R.id.rv_recipe_short_desc_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecipeShortDescListAdapter adapter = new RecipeShortDescListAdapter(mRecipeShortDescList, this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return mainView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(RECIPE_SHORT_DESC_LIST_EXTRA, mRecipeShortDescList);
    }

    @Override
    public void onListItemClick(int clickedItemPosition) {
        // Handle the click here.

    }
}
