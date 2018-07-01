package com.example.abel.medib2;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.abel.lib.Authenticator;
import com.example.abel.medib.SignupActivity;

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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SignUpTest {

    @Rule
    public ActivityTestRule<SignupActivity> mActivityRule = new ActivityTestRule<>(
            SignupActivity.class);

    boolean logged_out = false;


    public void setUp(){
        (Authenticator.getInstance(mActivityRule.getActivity())).removeToken();
        logged_out = true;
    }

    @Test
    public void viewAssertion() {
        if(logged_out == false) {
            setUp();
            mActivityRule.launchActivity(new Intent(mActivityRule.getActivity(), SignupActivity.class));
        }

        onView(withId(R.id.userName)).check(matches(isDisplayed()));
        onView(withId(R.id.userEmailId)).check(matches(isDisplayed()));
        onView(withId(R.id.mobileNumber)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.confirmPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.terms_conditions)).check(matches(isDisplayed()));
        onView(withId(R.id.signUpBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.already_user)).check(matches(isDisplayed()));

        onView(withId(R.id.terms_conditions)).check(matches(isNotChecked()));

        onView(withId(R.id.signUpBtn)).check(matches(isClickable()));

        onView(withId(R.id.userEmailId)).check(isCompletelyBelow(withId(R.id.userName)));
        onView(withId(R.id.mobileNumber)).check(isCompletelyBelow(withId(R.id.userEmailId)));
        onView(withId(R.id.password)).check(isCompletelyBelow(withId(R.id.mobileNumber)));
        onView(withId(R.id.confirmPassword)).check(isCompletelyBelow(withId(R.id.password)));
        onView(withId(R.id.terms_conditions)).check(isCompletelyBelow(withId(R.id.confirmPassword)));
        onView(withId(R.id.signUpBtn)).check(isCompletelyBelow(withId(R.id.terms_conditions)));
        onView(withId(R.id.already_user)).check(isCompletelyBelow(withId(R.id.signUpBtn)));
    }
    @Test
    public void viewAction() {
        if(logged_out == false) {
            setUp();
            mActivityRule.launchActivity(new Intent(mActivityRule.getActivity(), SignupActivity.class));
        }
        onView(withId(R.id.userName)).perform(typeText("Diye"));
        onView(withId(R.id.userEmailId)).perform(typeText("diyye101@gmail.com"));
        onView(withId(R.id.mobileNumber)).perform(typeText("+251926309646"));
        closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("123456"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPassword)).perform(typeText("123456"));
        closeSoftKeyboard();
        onView(withId(R.id.terms_conditions)).perform(click());
        onView(withId(R.id.signUpBtn)).perform(click());

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            onView(withId(R.id.login_layout)).check(matches(isDisplayed()));
        }catch(Exception except){

        }
    }

    @After
    public void tearDown(){
        //After Test case Execution
    }
}