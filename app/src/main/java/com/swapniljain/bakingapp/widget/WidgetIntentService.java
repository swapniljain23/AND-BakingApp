package com.swapniljain.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.model.Recipe;

public class WidgetIntentService extends IntentService {

    public static final String ACTION_UPDATE_LIST_VIEW = "update_app_widget_list";
    public static final String RECIPE_KEY = "recipe";

    public WidgetIntentService() {
        super("WidgetIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent !=null){
            final String action = intent.getAction();
            if (ACTION_UPDATE_LIST_VIEW.equals(action)){
                Recipe recipe = intent.getParcelableExtra(RECIPE_KEY);
                if (recipe != null) {
                    handleActionUpdateListView(recipe);
                }
            }
        }
    }

    private void handleActionUpdateListView(Recipe recipe) {
        if (recipe != null) {
            WidgetDataModel.saveRecipe(this, recipe);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                AppWidget.class));

        AppWidget.updateAppWidgets(this,appWidgetManager,appWidgetIds);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredients_list);
    }

    public static void startActionUpdateListView(Context context, Recipe recipe) {
        Intent intent = new Intent(context, WidgetIntentService.class);
        intent.setAction(WidgetIntentService.ACTION_UPDATE_LIST_VIEW);
        intent.putExtra(RECIPE_KEY, recipe);
        context.startService(intent);
    }
}
