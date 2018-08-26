package com.swapniljain.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.model.Recipe;
import com.swapniljain.bakingapp.utility.RecipeShortDescListAdapter;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeShortDescListAdapter.RecipeShortDescClickListener {

    public static String RECIPE_EXTRA = "recipe_extra";

    private Recipe mRecipe;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("RecipeDetailActivity","RecipeDetailActivity: savedInstanceState");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            if(intent == null) {
                // Handle error.
            }

            mRecipe = intent.getParcelableExtra(RECIPE_EXTRA);
            Bundle bundle = new Bundle();
            Log.d("RecipeDetailActivity", "ShortDesc: " + mRecipe.getShortDescriptionsFromSteps());
            bundle.putStringArrayList(RecipeDetailActivityFragment.RECIPE_SHORT_DESC_LIST_EXTRA, mRecipe.getShortDescriptionsFromSteps());
            RecipeDetailActivityFragment fragment = new RecipeDetailActivityFragment();
            fragment.setArguments(bundle);

            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_recipe_detail, fragment)
                    .commit();
        } else {
            mRecipe = savedInstanceState.getParcelable(RECIPE_EXTRA);
        }

        setTitle(mRecipe.getRecipeName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_EXTRA, mRecipe);
    }

    @Override
    public void onListItemClick(int clickedItemPosition) {
        // Handle the click here.
        Log.d("RecipeDetailActivityFragment","onListItemClick");

        if (clickedItemPosition == 0) {
            // Show ingredients.
            Intent ingredientIntent = new Intent(RecipeDetailActivity.this, IngredientActivity.class);
            ingredientIntent.putParcelableArrayListExtra(IngredientActivity.INGREDIENTS_EXTRA, (ArrayList<? extends Parcelable>) mRecipe.getRecipeIngredients());
            startActivity(ingredientIntent);
        } else {
            // Show steps.
            Intent stepIntent = new Intent(RecipeDetailActivity.this, StepActivity.class);
            stepIntent.putParcelableArrayListExtra(StepActivity.STEPS_EXTRA, (ArrayList<? extends Parcelable>) mRecipe.getRecipeSteps());
            stepIntent.putExtra(StepActivity.POSITION_EXTRA, clickedItemPosition - 1);
            stepIntent.putExtra(StepActivity.RECIPE_NAME_EXTRA, mRecipe.getRecipeName());
            startActivity(stepIntent);
        }
    }
}
