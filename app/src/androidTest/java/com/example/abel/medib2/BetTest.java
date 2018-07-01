package com.example.abel.medib2;


import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.abel.lib.Authenticator;
import com.example.abel.medib.BetActivity;


import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class BetTest {

    @Rule
    public ActivityTestRule<BetActivity> mActivityRule = new ActivityTestRule<>(
            BetActivity.class);

    boolean logged_in = false;

    public void setUp(){
        //this is a user with the name mock(you have to run node seeder/index.js)
        (Authenticator.getInstance(mActivityRule.getActivity())).storeToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhbW91bnQiOjQwMCwiaXNBZG1pbiI6ZmFsc2UsIl9pZCI6IjViMzg0MDExYmI4MTc2MThkNzUzNTkyYyIsIm5hbWUiOiJtb2NrIiwiaWF0IjoxNTMwNDE0MzQxfQ.mpWpq7TnOUbF1VTmCREvrlTZWTzOOw25SgJrK7WNXFg", false);
        logged_in = true;
    }

    @Test
    public void viewAssertion() {
        if(logged_in == false){
            setUp();
            mActivityRule.launchActivity(new Intent(mActivityRule.getActivity(), BetActivity.class));
        }
        onView(withId(R.id.selectTeam)).check(matches(isDisplayed()));
        onView(withId(R.id.team1_icon)).check(matches(isDisplayed()));
        onView(withId(R.id.team_name_1_bet)).check(matches(isDisplayed()));
        onView(withId(R.id.team_1_button)).check(matches(isDisplayed()));
        onView(withId(R.id.team_2_button)).check(matches(isDisplayed()));
        onView(withId(R.id.team_name_2_bet)).check(matches(isDisplayed()));
        onView(withId(R.id.team2_icon)).check(matches(isDisplayed()));
        onView(withId(R.id.team_odd_1_bet)).check(matches(isDisplayed()));
        onView(withId(R.id.team_odd_2_bet)).check(matches(isDisplayed()));
        onView(withId(R.id.bet_amount)).check(matches(isDisplayed()));
        onView(withId(R.id.bet_profit)).check(matches(isDisplayed()));
        onView(withId(R.id.submit_bet_button)).check(matches(isDisplayed()));

    }
    @Test
    public void viewAction()  {
        if(logged_in == false){
            setUp();
            mActivityRule.launchActivity(new Intent(mActivityRule.getActivity(), BetActivity.class));
        }
        onView(withId(R.id.team_1_button)).perform(click());
        onView(withId(R.id.team_2_button)).perform(click());
        onView(withId(R.id.bet_amount)).perform(typeText("200"));
        pressBack();
        onView(withId(R.id.submit_bet_button)).perform(click());
    }
    @After
    public void tearDown() throws Exception {
        //After Test case Execution
    }
}