package com.example.csit314_project;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;


import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.VerificationModes.times;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.csit314_project", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void healthOrgSuccessfulLogin() {

        Intents.init();

        onView(withId(R.id.txt_username)).perform(typeText("admin"));
        onView(withId(R.id.txt_password)).perform(typeText("123")).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        activityTestRule.launchActivity(new Intent());
        intended(hasComponent(HealthOrgMainActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void healthOrgWrongPasswordLogin() {

        Intents.init();

        onView(withId(R.id.txt_username)).perform(typeText("admin"));
        onView(withId(R.id.txt_password)).perform(typeText("123a")).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        activityTestRule.launchActivity(new Intent());
        intended(hasComponent(LoginActivity.class.getName()));
        intended(hasComponent(HealthOrgMainActivity.class.getName()), times(0));

        Intents.release();

    }

    @Test
    public void healthOrgWrongUsernameLogin() {

        Intents.init();

        onView(withId(R.id.txt_username)).perform(typeText("admina"));
        onView(withId(R.id.txt_password)).perform(typeText("123")).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        activityTestRule.launchActivity(new Intent());
        intended(hasComponent(LoginActivity.class.getName()));
        intended(hasComponent(HealthOrgMainActivity.class.getName()), times(0));

        Intents.release();

    }

    @Test
    public void healthOrgNoValuesLogin() {

        Intents.init();

        onView(withId(R.id.txt_username)).perform(typeText(""));
        onView(withId(R.id.txt_password)).perform(typeText("")).perform(closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        activityTestRule.launchActivity(new Intent());
        intended(hasComponent(LoginActivity.class.getName()));
        intended(hasComponent(HealthOrgMainActivity.class.getName()), times(0));

        Intents.release();

    }
}