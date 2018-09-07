package com.swapniljain.bakingapp.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.Testing.SimpleIdlingResource;
import com.swapniljain.bakingapp.model.Recipe;
import com.swapniljain.bakingapp.network.RecipeClient;
import com.swapniljain.bakingapp.network.RetrofitClient;
import com.swapniljain.bakingapp.utility.RecipeListAdapter;
import com.swapniljain.bakingapp.widget.WidgetDataModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeClickListener {

    public static String RECIPE_LIST_EXTRA = "recipe_list_extra";
    private List<Recipe> mRecipeList = new ArrayList<Recipe>();

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.main_activity_title);
        if (savedInstanceState != null) {
            mRecipeList = savedInstanceState.getParcelableArrayList(RECIPE_LIST_EXTRA);
            // Populate UI.
            populateUI();
        } else {
            // Request recipes.
            requestRecipes();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_LIST_EXTRA, (ArrayList<Recipe>) mRecipeList);
    }

    public void populateUI() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,
                getApplicationContext().getResources().getInteger(R.integer.no_of_columns));
        RecipeListAdapter adapter = new RecipeListAdapter(mRecipeList, this);
        RecyclerView recyclerView = findViewById(R.id.rv_recipe_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void requestRecipes() {
        Log.d("MainActivity","requestRecipes called.");
        RecipeClient client = new RetrofitClient().getClient().create(RecipeClient.class);
        Call<List<Recipe>> call = client.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipeList = response.body();
                Log.d("RECIPE","RecipeList: " + mRecipeList.toString());
                if (mRecipeList.size() > 0) {
                    WidgetDataModel.saveRecipe(MainActivity.this, mRecipeList.get(0));
                    // notify widget about data change
                }
                populateUI();
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Handle failure here.
                Log.d("MainActivity", "requestRecipes Failed");
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemPosition) {
        Log.d("RECIPE", "onListItemClick");

        Recipe clickedRecipe = mRecipeList.get(clickedItemPosition);

        Intent recipeDetailIntent = new Intent(MainActivity.this, RecipeDetailActivity.class);
        recipeDetailIntent.putExtra(RecipeDetailActivity.RECIPE_EXTRA, clickedRecipe);
        startActivity(recipeDetailIntent);
    }
}
