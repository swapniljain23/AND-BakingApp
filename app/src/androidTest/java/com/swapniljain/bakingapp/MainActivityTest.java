package com.swapniljain.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import com.swapniljain.bakingapp.activity.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void loadRecipe() {

        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(0));

        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .check(matches(hasDescendant(withText("Nutella Pie"))));

        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(1));

        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .check(matches(hasDescendant(withText("Brownies"))));

        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(2));

        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .check(matches(hasDescendant(withText("Yellow Cake"))));

        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(3));

        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .check(matches(hasDescendant(withText("Cheesecake"))));

    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
}

