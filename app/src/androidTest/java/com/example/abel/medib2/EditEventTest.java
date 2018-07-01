package com.example.abel.medib2;


import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.abel.lib.Authenticator;
import com.example.abel.medib.EditEventActivity;
import com.example.abel.medib.PostEventActivity;


import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

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

public class EditEventTest {

    @Rule
    public ActivityTestRule<EditEventActivity> mActivityRule = new ActivityTestRule<>(
            EditEventActivity.class);


    boolean logged_in = false;

    protected  void startActivity(){
        Intent intent = new Intent(mActivityRule.getActivity(), EditEventActivity.class);
        ArrayList<String> items = new ArrayList<>(5);
        items.add("Man United");
        items.add("Man City");
        items.add("1.5");
        items.add("2");
        items.add("5b384011bb817618d7535933");
        Bundle bundle=new Bundle();

        bundle.putStringArrayList("Key", items);
        intent.putExtra("Match", bundle);

        mActivityRule.launchActivity(intent);
    }

    public void setUp(){
        (Authenticator.getInstance(mActivityRule.getActivity())).storeToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhbW91bnQiOjAsImlzQWRtaW4iOnRydWUsIl9pZCI6IjViMzg0MDExYmI4MTc2MThkNzUzNTkyZiIsIm5hbWUiOiJhZG1pbiIsImlhdCI6MTUzMDQyMDI0OH0.ck9TvpfWFs3WlQ86isQ5fD_niAxalwOhugDc0EwWtYA ", true);
        logged_in = true;
    }

    @Test
    public void viewAssertion() {
        if(logged_in == false){
            setUp();
            startActivity();
        }
        onView(withId(R.id.teamname1)).check(matches(isDisplayed()));
        onView(withId(R.id.teamodd1)).check(matches(isDisplayed()));
        onView(withId(R.id.teamname2)).check(matches(isDisplayed()));
        onView(withId(R.id.teamodd2)).check(matches(isDisplayed()));
        onView(withId(R.id.pickDate)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_event_button)).check(matches(isDisplayed()));
    }
    @Test
    public void viewAction()  {
        if(logged_in == false){
            setUp();
            startActivity();
        }
        onView(withId(R.id.teamname1)).perform(typeText("Man United"));
        onView(withId(R.id.teamname2)).perform(typeText("Man City"));
        onView(withId(R.id.teamodd1)).perform(typeText("2.0"));
        onView(withId(R.id.teamodd2)).perform(typeText("1.5"));
        onView(withId(R.id.edit_event_button)).perform(click());
    }
    @After
    public void tearDown() throws Exception {
        //After Test case Execution
    }
}