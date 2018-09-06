package com.swapniljain.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.swapniljain.bakingapp.R;
import com.swapniljain.bakingapp.activity.RecipeDetailActivity;
import com.swapniljain.bakingapp.model.Recipe;

public class AppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = "Recipe Name";
        Recipe recipe = WidgetDataModel.getRecipe(context);
        if (recipe !=null) {
            widgetText = recipe.getRecipeName();
        }
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.recipe_list_name, widgetText);

        Intent intentService = new Intent(context, WidgetService.class);
        views.setRemoteAdapter(R.id.ingredients_list,intentService);

        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.RECIPE_EXTRA, recipe);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (!widgetText.equals("Recipe Name")) {
            views.setOnClickPendingIntent(R.id.recipe_list_name, pendingIntent);
        }
        Intent appIntent = new Intent(context, RecipeDetailActivity.class);
        PendingIntent appPendingIntent =
                PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.ingredients_list, appPendingIntent);

        views.setEmptyView(R.id.ingredients_list,R.id.empty_view);

        // Instruct the widget manager to update the widget.
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        WidgetIntentService.startActionUpdateListView(context,null);
    }

    public static void updateAppWidgets (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}
