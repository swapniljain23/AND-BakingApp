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
import com.swapniljain.bakingapp.model.Step;
import com.swapniljain.bakingapp.utility.RecipeShortDescListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeShortDescListAdapter.RecipeShortDescClickListener {

    public static String RECIPE_EXTRA = "recipe_extra";

    private Recipe mRecipe;
    private android.support.v4.app.FragmentManager mFragmentManager;
    private boolean isTablet = false;

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

        if (findViewById(R.id.fragment_step) != null) {
            isTablet = true;
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
        if (isTablet) {
            if (clickedItemPosition == 0) {
                IngredientActivityFragment ingredientsFragment = new IngredientActivityFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(IngredientActivityFragment.INGREDIENTS_EXTRA,
                        (ArrayList<? extends Parcelable>) mRecipe.getRecipeIngredients());
                ingredientsFragment.setArguments(bundle);

                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_step, ingredientsFragment)
                        .commit();
            } else {
                List<Step> steps = mRecipe.getRecipeSteps();
                int stepPosition = clickedItemPosition - 1;
                String url0 = steps.get(stepPosition).getVideoUrl();
                String url1 = steps.get(stepPosition).getThumbnailUrl();
                String videoUrl = (url1.equals("")) ? url0 : url1;
                String stepDescription = steps.get(stepPosition).getDescription();

                Bundle bundle = new Bundle();
                bundle.putString(StepActivityFragment.STEP_DESCRIPTION_EXTRA, stepDescription);
                bundle.putString(StepActivityFragment.STEP_VIDEO_URL_EXTRA,videoUrl);
                StepActivityFragment stepFragment = new StepActivityFragment();
                stepFragment.setArguments(bundle);

                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_step, stepFragment)
                        .commit();
            }
        } else {
            if (clickedItemPosition == 0) {
                // Show ingredients.
                Intent ingredientIntent = new Intent(RecipeDetailActivity.this, IngredientActivity.class);
                ingredientIntent.putParcelableArrayListExtra(IngredientActivity.INGREDIENTS_EXTRA, (ArrayList<? extends Parcelable>) mRecipe.getRecipeIngredients());
                ingredientIntent.putExtra(IngredientActivity.RECIPE_NAME_EXTRA, mRecipe.getRecipeName());
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
}
