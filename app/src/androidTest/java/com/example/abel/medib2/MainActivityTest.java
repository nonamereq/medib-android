package com.example.abel.medib2;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.abel.lib.Authenticator;
import com.example.abel.medib.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Haile on 6/23/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule=new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp(){
        (Authenticator.getInstance(mActivityTestRule.getActivity())).removeToken();
        onView(withId(R.id.already_user)).perform(click());
        onView(withId(R.id.userName)).perform(typeText("mock"));
        onView(withId(R.id.login_password)).perform(typeText("123456"));
        onView(withId(R.id.loginBtn)).perform(click());

    }
    @Test
    public void checkItemClickable(){
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withText("Select Team")).check(matches(isDisplayed()));
    }
    @Test
    public void testNavigateToMatchActivity() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_matches));
        onView(withId(R.id.list)).check(matches(isDisplayed()));
    }
    @Test
    public void testNavigateToUpdateActivity() throws Exception {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_update));
        onView(withText("Your Current Balance:")).check(matches(isDisplayed()));
    }
    @Test
    public void testNavigateToCashoutActivity() throws Exception {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_cashout));
        onView(withText("Available Amount")).check(matches(isDisplayed()));
    }@Test
    public void testNavigateToLogoutActivity() throws Exception {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_logout));
        onView(withId(R.id.login_password)).check(matches(isDisplayed()));
    }

}
