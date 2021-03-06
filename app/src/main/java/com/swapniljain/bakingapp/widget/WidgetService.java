package com.swapniljain.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.activity.RecipeDetailActivity;
import com.swapniljain.bakingapp.model.Ingredient;
import com.swapniljain.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AppWidgetListView(getApplicationContext());
    }
}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    List<Ingredient> ingredients = new ArrayList<>();
    Recipe recipe = null;

    public AppWidgetListView(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        recipe = WidgetDataModel.getRecipe(mContext);
        if (recipe != null) {
            ingredients = recipe.getRecipeIngredients();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item_app_widget);

        remoteViews.setTextViewText(R.id.ingredient_name_widget_tv,
                ingredients.get(position).getIngredientName());
        remoteViews.setTextViewText(R.id.ingredient_amount_widget_tv,
                mContext.getResources().getString(R.string.ingredient_amount,
                ingredients.get(position).getQuantity(),
                ingredients.get(position).getMeasure()));

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(RecipeDetailActivity.RECIPE_EXTRA, recipe);
        remoteViews.setOnClickFillInIntent(R.id.parent_view, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}