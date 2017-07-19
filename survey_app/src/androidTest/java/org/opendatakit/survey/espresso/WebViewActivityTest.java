package org.opendatakit.survey.espresso;

/**
 * Created by clarice on 7/19/17.
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.webkit.WebView;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.survey.activities.MainMenuActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.clearElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;

//import org.opendatakit.util.DisableAnimationsRule;

/**
 * Basic sample that shows the usage of Espresso web showcasing API.
 * <p/>
 * The sample has a simple layout which contains a single {@link WebView}. The HTML page displays
 * a form with an input tag and buttons to submit the form.
 */

@RunWith(AndroidJUnit4.class)
public class WebViewActivityTest {
  @ClassRule
  public static DisableAnimationsRule disableAnimationsRule = new DisableAnimationsRule();
  private Boolean initSuccess = null;
  private UiDevice mDevice;
  @Rule
  public ActivityTestRule<MainMenuActivity> mActivityRule = new ActivityTestRule<MainMenuActivity>(
          MainMenuActivity.class, false, true) {
    @Override
    protected void beforeActivityLaunched() {
      super.beforeActivityLaunched();

      if (initSuccess == null) {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        initSuccess = UIUtils.turnOnCustomHome(mDevice);
      }
    }

    @Override
    protected void afterActivityLaunched() {
      super.afterActivityLaunched();

      //onWebView().forceJavascriptEnabled();
    }
  };

  @Before
  public void setup() {

  }

  @Test
  public void surveyPerfTest3() {
    int setupDelayMS = 2500;

    // Click on the Survey form
    onData(ODKMatcher.withForm("surveySpeedTest")).perform(click());

    // Delay to let form load
    try {
      Thread.sleep(setupDelayMS);
    } catch (InterruptedException ie) {
      System.out.println("Error with thread sleep");
    }

    // Next we need to click on the create new instance button
    onWebView().withElement(findElement(Locator.CLASS_NAME, "createInstance"))
            .perform(webClick());

    // Delay to let form load
    try {
      Thread.sleep(setupDelayMS);
    } catch (InterruptedException ie) {
      System.out.println("Error with thread sleep");
    }

    // Next we need to click on go to next prompt
    onWebView().withElement(findElement(Locator.CLASS_NAME, "odk-next-btn"))
            .perform(webClick());

    // Delay to let form load
    boolean found = false;
    int tries = 0;
    while (!found && tries < 3) {

      try {
        //"Enter test text"
        onWebView().withElement(findElement(Locator.ID, "slider-isurvey0")).perform(clearElement());

        Thread.sleep(setupDelayMS);

        found = true;
      } catch (InterruptedException ie) {
        System.out.println("Error with thread sleep");
        found = false;
      } catch (RuntimeException re) {
        System.out.println("Couldn't find yes element");
        found = false;
      }
      tries++;
    }


    // Run through the Tables app an infinite number of times to get a
    // crash
    int numOfTimesToRun = 3;
    int numOfMsToSleep = 50;
    int i = 0;
    for (i = 0; i < numOfTimesToRun; i++) {
      try {
        // text box1
        onWebView().withElement(findElement(Locator.ID, "slider-isurvey0")).perform(DriverAtoms.webKeys("testingtesting"));

        // select_one
        onWebView().withElement(findElement(Locator.ID, "yes")).perform(webClick());
        onWebView().withElement(findElement(Locator.ID, "no")).perform(webClick());
        onWebView().withElement(findElement(Locator.ID, "maybe")).perform(webClick());

        // text box 2
        onWebView().withElement(findElement(Locator.ID, "slider-isurvey2")).perform(DriverAtoms.webKeys("testingtesting123"));

        onWebView().withElement(findElement(Locator.ID, "slider-isurvey0")).perform(DriverAtoms.webKeys("123"));

        onWebView().withElement(findElement(Locator.ID, "slider-isurvey2")).perform(DriverAtoms.webKeys("456"));
      } catch (RuntimeException e) {
        e.printStackTrace();
        System.out.println("Failed to find an element");

      }
    }

    System.out.println("Number of iterations = " + i);

  }
}
