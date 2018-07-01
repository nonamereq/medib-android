package com.example.abel.medib2;


import android.content.Intent;
import android.support.test.espresso.ViewAssertion;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.abel.lib.Authenticator;
import com.example.abel.lib.Request.PostEventRequest;
import com.example.abel.medib.PostEventActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.PositionAssertions.isBelow;
import static android.support.test.espresso.assertion.PositionAssertions.isRightOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PostEventTest {

    @Rule
    public ActivityTestRule<PostEventActivity> mActivityRule = new ActivityTestRule<>(
            PostEventActivity.class);

    boolean logged_in = false;

    public void setUp(){
        (Authenticator.getInstance(mActivityRule.getActivity())).storeToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhbW91bnQiOjAsImlzQWRtaW4iOnRydWUsIl9pZCI6IjViMzg0MDExYmI4MTc2MThkNzUzNTkyZiIsIm5hbWUiOiJhZG1pbiIsImlhdCI6MTUzMDQyMDI0OH0.ck9TvpfWFs3WlQ86isQ5fD_niAxalwOhugDc0EwWtYA ", true);
        logged_in = true;
    }

    @Test
    public void viewAssertion() {
        if(logged_in == false){
            setUp();
            mActivityRule.launchActivity(new Intent(mActivityRule.getActivity(), PostEventActivity.class));
        }
        onView(withId(R.id.postEvent)).check(matches(isDisplayed()));
        onView(withId(R.id.teamName1)).check(matches(isDisplayed()));
        onView(withId(R.id.teamOdd1)).check(matches(isDisplayed()));
        onView(withId(R.id.teamName2)).check(matches(isDisplayed()));
        onView(withId(R.id.teamOdd2)).check(matches(isDisplayed()));
        onView(withId(R.id.post_event_button)).check(matches(isDisplayed()));
    }
    @Test
    public void viewAction()  {
        if(logged_in == false){
            setUp();
            mActivityRule.launchActivity(new Intent(mActivityRule.getActivity(), PostEventActivity.class));
        }
        onView(withId(R.id.teamName1)).perform(typeText("Man United"));
        onView(withId(R.id.teamName2)).perform(typeText("Man City"));
        onView(withId(R.id.teamOdd1)).perform(typeText("2.0"));
        onView(withId(R.id.teamOdd2)).perform(typeText("1.5"));
        onView(withId(R.id.post_event_button)).perform(click());
    }
    @After
    public void tearDown() throws Exception {
        //After Test case Execution
    }
}