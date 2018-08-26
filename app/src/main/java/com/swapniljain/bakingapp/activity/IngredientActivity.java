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

    public static String INGREDIENTS_EXTRA = "INGREDIENTS_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent == null) {
            // Handle error.
            finish();
        }

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
