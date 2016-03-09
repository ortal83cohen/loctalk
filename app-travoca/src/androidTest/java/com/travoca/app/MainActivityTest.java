package com.travoca.app;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.travoca.app.activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * @author ortal
 * @date 2015-06-21
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);
    private MainActivity mActivity;

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void changeText_sameActivity() {
//        bookingFlowTest();

//        // Check that the text was changed.
//        onView(withId(R.id.app_bar)).check(matches(withText("Amsterdam, Netherlands")));
    }

    @Test
    public void changeText_newActivity() {
        bookingFlowTest();
    }

    private void bookingFlowTest() {
        // Type text and then press the button.
        autocomplete("Amsterdam", "Amsterdam, Netherlands");


        onView(withId(R.id.search)).perform(click());

    }


    private void autocomplete(String stringToBeTyped, String textToSelect) {
        onView(withId(R.id.autoCompleteTextView_location))
                .perform(clearText())
                .perform(typeText(stringToBeTyped), closeSoftKeyboard());

        onView(withText(textToSelect))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withText(textToSelect))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());

        onView(withId(R.id.autoCompleteTextView_location)).check(matches(withText(textToSelect)));


    }
}
