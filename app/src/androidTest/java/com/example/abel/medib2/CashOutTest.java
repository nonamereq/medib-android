package com.example.abel.medib2;


import android.content.Intent;
import android.support.test.espresso.ViewAssertion;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.abel.lib.Authenticator;
import com.example.abel.medib.BetActivity;
import com.example.abel.medib.CashoutActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.PositionAssertions.isBelow;
import static android.support.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static android.support.test.espresso.assertion.PositionAssertions.isRightOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class CashOutTest {

    @Rule
    public ActivityTestRule<CashoutActivity> mActivityRule = new ActivityTestRule<>(
            CashoutActivity.class);

    boolean logged_in;

    public void setUp(){
        //this is a user with the name mock(you have to run node seeder/index.js)
        (Authenticator.getInstance(mActivityRule.getActivity())).storeToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhbW91bnQiOjQwMCwiaXNBZG1pbiI6ZmFsc2UsIl9pZCI6IjViMzg0MDExYmI4MTc2MThkNzUzNTkyYyIsIm5hbWUiOiJtb2NrIiwiaWF0IjoxNTMwNDE0MzQxfQ.mpWpq7TnOUbF1VTmCREvrlTZWTzOOw25SgJrK7WNXFg", false);
        logged_in = true;
    }

    @Test
    public void viewAssertion() {
        if(logged_in == false){
            setUp();
            mActivityRule.launchActivity(new Intent(mActivityRule.getActivity(), CashoutActivity.class));
        }
        onView(withId(R.id.cashOut)).check(matches(isDisplayed()));
        onView(withId(R.id.current)).check(matches(isDisplayed()));
        onView(withId(R.id.current_balance_cashout)).check(matches(isDisplayed()));
        onView(withId(R.id.cashout_amount)).check(matches(isDisplayed()));
        onView(withId(R.id.cashout_button)).check(matches(isDisplayed()));

        onView(withId(R.id.current_balance_cashout)).check(isCompletelyBelow(withId(R.id.current)));
        onView(withId(R.id.cashout_amount)).check(isCompletelyBelow(withId(R.id.current_balance_cashout)));
        onView(withId(R.id.cashout_button)).check(isCompletelyBelow(withId(R.id.cashout_amount)));

        onView(withId(R.id.cashout_button)).check(matches(isClickable()));
    }
    @Test
    public void viewAction()  {
        if(logged_in == false){
            setUp();
            mActivityRule.launchActivity(new Intent(mActivityRule.getActivity(), CashoutActivity.class));
        }

        onView(withId(R.id.cashout_amount)).perform(typeText("200"));
        onView(withId(R.id.cashout_button)).perform(click());
    }
    @After
    public void tearDown() throws Exception {
        //After Test case Execution
    }
}