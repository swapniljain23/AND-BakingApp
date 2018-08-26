package com.swapniljain.bakingapp.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.model.Ingredient;
import com.swapniljain.bakingapp.utility.IngredientListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class IngredientActivityFragment extends Fragment {

    public static String INGREDIENTS_EXTRA = "INGREDIENTS_EXTRA";
    List<Ingredient> mIngredients = null;

    public IngredientActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        mIngredients = getArguments().getParcelableArrayList(INGREDIENTS_EXTRA);

        RecyclerView recyclerView = view.findViewById(R.id.rv_ingredients);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        IngredientListAdapter ingredientAdapter = new IngredientListAdapter(mIngredients);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ingredientAdapter);
        return view;
    }
}
