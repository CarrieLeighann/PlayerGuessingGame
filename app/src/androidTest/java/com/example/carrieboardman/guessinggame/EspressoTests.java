package com.example.carrieboardman.guessinggame;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class EspressoTests {

    @Rule
    public ActivityTestRule<GameMain> testRule=
            new ActivityTestRule<>(GameMain.class);

    @Test
    public void afterPlayerOnePress(){

        onView(withId(R.id.player_one_option))
           .perform(click());
        playerPressTests();

    }

    @Test
    public void afterPlayerTwoPress(){

        onView(withId(R.id.player_two_option))
                .perform(click());
        playerPressTests();

    }

    public void playerPressTests(){
        onView(withId(R.id.result)).check(matches(isDisplayed()));
        onView(withId(R.id.fppg_p1)).check(matches(isDisplayed()));
        onView(withId(R.id.fppg_p2)).check(matches(isDisplayed()));

        onView(withId(R.id.nextPlayer)).check(matches(isClickable()));
        onView(withId(R.id.player_one_option)).check(matches(not(isClickable())));
        onView(withId(R.id.player_two_option)).check(matches(not(isClickable())));

    }

}
