package com.swapniljain.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.model.Recipe;
import com.swapniljain.bakingapp.network.RecipeClient;
import com.swapniljain.bakingapp.network.RetrofitClient;
import com.swapniljain.bakingapp.utility.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {

    public static String RECIPE_LIST_EXTRA = "recipe_list_extra";
    private List<Recipe> mRecipeList = new ArrayList<Recipe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            savedInstanceState.getParcelableArrayList(RECIPE_LIST_EXTRA);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecipeAdapter adapter = new RecipeAdapter(mRecipeList, this);
        RecyclerView recyclerView = findViewById(R.id.rv_recipe_list);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void requestRecipes() {
        RecipeClient client = new RetrofitClient().getClient().create(RecipeClient.class);
        Call<List<Recipe>> call = client.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipeList = response.body();
                Log.d("RECIPE","RecipeList: " + mRecipeList.toString());
                populateUI();
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Handle failure here.
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemPosition) {
        // TODO.
        Log.d("RECIPE", "onListItemClick");

        Recipe clickedRecipe = mRecipeList.get(clickedItemPosition);

        Intent recipeDetailIntent = new Intent(MainActivity.this, RecipeDetailActivity.class);
        recipeDetailIntent.putExtra(RecipeDetailActivity.RECIPE_EXTRA, clickedRecipe);
        startActivity(recipeDetailIntent);
    }
}
