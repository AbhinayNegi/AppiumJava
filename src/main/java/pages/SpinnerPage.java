package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class SpinnerPage extends BasePage{
    private final By SPINNER_ONE = AppiumBy.id("io.appium.android.apis:id/spinner1");

    public SpinnerPage(AndroidDriver driver) {
        super(driver);
    }

    public void chooseOrangeAndWaitToast(){
        tap(SPINNER_ONE);
        tap(AppiumBy.androidUIAutomator("new UiSelector().text(\"orange\")"));
        waitForToast();
    }
}
