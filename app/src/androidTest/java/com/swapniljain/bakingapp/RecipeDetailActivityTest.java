package com.swapniljain.bakingapp;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.swapniljain.bakingapp.activity.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void detailActivityTest() {

        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(0));

        onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(ViewMatchers.withId(R.id.rv_recipe_short_desc_list))
                .perform(RecyclerViewActions.scrollToPosition(0));

        onView(ViewMatchers.withId(R.id.rv_recipe_short_desc_list))
                .check(matches(hasDescendant(withText("Recipe Ingredients"))));

        onView(ViewMatchers.withId(R.id.rv_recipe_short_desc_list))
                .perform(RecyclerViewActions.scrollToPosition(1));

        onView(ViewMatchers.withId(R.id.rv_recipe_short_desc_list))
                .check(matches(hasDescendant(withText("Recipe Introduction"))));

        onView(ViewMatchers.withId(R.id.rv_recipe_short_desc_list))
                .perform(RecyclerViewActions.scrollToPosition(2));

        onView(ViewMatchers.withId(R.id.rv_recipe_short_desc_list))
                .check(matches(hasDescendant(withText("Starting prep"))));

        onView(ViewMatchers.withId(R.id.rv_recipe_short_desc_list))
                .perform(RecyclerViewActions.scrollToPosition(7));

        onView(ViewMatchers.withId(R.id.rv_recipe_short_desc_list))
                .check(matches(hasDescendant(withText("Finishing Steps"))));


    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

