package com.example.android.jokesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.android_lib.JokeActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest{

    public static String DOG_JOKES = "DOG";
    public static String CAT_JOKES = "CAT";
    public static String MED_JOKES = "MED";
    public static String JOKE_EXTRA = "joke";

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule=
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertThat(appContext.getPackageName(), anyOf(is("com.example.android.jokesapp.free"), is("com.example.android.jokesapp.full")));
    }

    @Test
    public void seeJokeCategories(){
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(0));
        onView(withText("Dog Jokes")).check(matches(isDisplayed()));

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Cat Jokes")).check(matches(isDisplayed()));

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(2));
        onView(withText("Medicine Jokes")).check(matches(isDisplayed()));

    }

    @Test
    public void activityStarted(){
        Intents.init();
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(0, click()));
        intended(hasComponent(JokeActivity.class.getName()));
        Espresso.pressBack();
    }

    @Test
    public void seeDogJokes(){
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_view))
                .check(matches(atPosition(0, hasDescendant(not(withText(""))))));
        Espresso.pressBack();

    }

    @Test
    public void seeCatJokes(){
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recycler_view))
                .check(matches(atPosition(0, hasDescendant(not(withText(""))))));
        Espresso.pressBack();

    }

    @Test
    public void seeMedJokes(){
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(2, click()));
        onView(withId(R.id.recycler_view))
                .check(matches(atPosition(0, hasDescendant(not(withText(""))))));
        Espresso.pressBack();
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                // has no item on such position
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
            }
        };
    }




}
