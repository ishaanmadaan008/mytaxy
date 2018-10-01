package com.mytaxi.android_demo;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.suitebuilder.annotation.LargeTest;
import android.support.test.espresso.*;
import android.util.Log;
import android.view.KeyEvent;
import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExpressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);




    public static void allwCurrentPermission(UiDevice device) throws UiObjectNotFoundException {
        UiObject allowButton = device.findObject(new UiSelector().text("Allow"));
        try {
            allowButton.click();
        }
        catch (UiObjectNotFoundException e) {
        }
    }

    @Before
    public void registerIdlingResource() throws UiObjectNotFoundException {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
        allwCurrentPermission(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()));
        try {
            Espresso.onView(withId(R.id.btn_login))
                    .check(matches(isDisplayed()));
        } catch (NoMatchingViewException e) {
            onView(withContentDescription("Open navigation drawer")).perform(click());
            onView(allOf(withId(R.id.design_menu_item_text), withText("Logout"))).perform(click());
        }
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }


    @Test
    public void login_fail() throws InterruptedException {

        onView(withId(R.id.edt_username)).perform(typeText("Ishaan"), closeSoftKeyboard());

        onView(withId(R.id.edt_password)).perform(typeText("Madaan"), closeSoftKeyboard());

        onView(withId(R.id.btn_login)).perform(click());

       Thread.sleep(1000);

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Login failed")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void login_sucess() throws InterruptedException {


        onView(withId(R.id.edt_username)).perform(typeText("crazydog335"), closeSoftKeyboard());

        onView(withId(R.id.edt_password)).perform(typeText("venture"), closeSoftKeyboard());

        onView(withId(R.id.btn_login)).perform(click());

        Thread.sleep(1000);


        onView(withText("mytaxi demo"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.searchContainer)).perform(click());
        onView(withId(R.id.textSearch)).perform(typeText("sa"), closeSoftKeyboard(), pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withText("Sarah Scott")).inRoot(RootMatchers.isPlatformPopup()).perform(click());


        onView(allOf(withId(R.id.textViewDriverName),withText("Sarah Scott"))).check(matches(isDisplayed()));

        onView(withId(R.id.fab)).perform(click());

    }
}
