package com.swapniljain.bakingapp.activity;


import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    public static String INGREDIENTS_EXTRA = "ingredients_extra";
    public static String RECIPE_NAME_EXTRA = "recipe_name_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent == null) {
            // Handle error.
            finish();
        }

        toolbar.setTitle(intent.getStringExtra(RECIPE_NAME_EXTRA) + ": Ingredients");

        List<Ingredient> ingredients = intent.getParcelableArrayListExtra(INGREDIENTS_EXTRA);
        FragmentManager fragmentManager = getSupportFragmentManager();
        IngredientActivityFragment fragment = new IngredientActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IngredientActivityFragment.INGREDIENTS_EXTRA, (ArrayList<? extends Parcelable>)ingredients);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_ingredients, fragment)
                .commit();
    }

}
