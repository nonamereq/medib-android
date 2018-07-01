package com.example.abel.medib2;


import com.example.abel.lib.Authenticator;
import com.example.abel.medib.LoginActivity;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.InputType;

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
import static android.support.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static android.support.test.espresso.assertion.PositionAssertions.isCompletelyRightOf;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class LogInTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    boolean logged_out = false;

    public void setUp(){
        (Authenticator.getInstance(mActivityRule.getActivity())).removeToken();
        logged_out = true;
    }

    @Test
    public void viewAssertion() {
        if(logged_out == false) {
            setUp();
            mActivityRule.launchActivity(new Intent(mActivityRule.getActivity(), LoginActivity.class));
        }
        onView(withId(R.id.login_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.userName)).check(matches(isDisplayed()));
        onView(withId(R.id.login_password)).check(matches(isDisplayed()));
        onView(withId(R.id.show_hide_password)).check(matches(isDisplayed()));
        onView(withId(R.id.forgot_password)).check(matches(isDisplayed()));
        onView(withId(R.id.loginBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.createAccount)).check(matches(isDisplayed()));

        onView(withId(R.id.show_hide_password)).check(matches(isNotChecked()));

        onView(withId(R.id.login_password)).check(isCompletelyBelow(withId(R.id.userName)));
        onView(withId(R.id.show_hide_password)).check(isCompletelyBelow(withId(R.id.login_password)));
        onView(withId(R.id.forgot_password)).check(isCompletelyRightOf(withId(R.id.show_hide_password)));
        onView(withId(R.id.loginBtn)).check(isCompletelyBelow(withId(R.id.show_hide_password)));
        onView(withId(R.id.createAccount)).check(isCompletelyBelow(withId(R.id.loginBtn)));


    }
    @Test
    public void viewAction()  {
        if(logged_out == false) {
            setUp();
            mActivityRule.launchActivity(new Intent(mActivityRule.getActivity(), LoginActivity.class));
        }
        onView(withId(R.id.show_hide_password)).perform(click());
        onView(withId(R.id.userName)).perform(typeText("Diye"));
        onView(withId(R.id.login_password)).perform(typeText("123456"));
        onView(withId(R.id.login_password)).check(matches(withInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)));
        closeSoftKeyboard();
        onView(withId(R.id.show_hide_password)).perform(click());
        onView(withId(R.id.login_password)).check(matches(withInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)));
        onView(withId(R.id.loginBtn)).perform(click());

        logged_out = false;

    }
    @After
    public void tearDown() throws Exception {
        //After Test case Execution
    }
}