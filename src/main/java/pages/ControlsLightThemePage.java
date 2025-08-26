package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class ControlsLightThemePage extends BasePage{

    private final By toggle = AppiumBy.id("io.appium.android.apis:id/toggle1");

    public ControlsLightThemePage(AndroidDriver driver) {
        super(driver);
    }

    public void toggleAndWaitForOnText(){
        tap(toggle);
        new WebDriverWait(driver, java.time.Duration.ofSeconds(30))
                .until(d -> d.findElement(toggle).getText().equalsIgnoreCase("on"));
    }

    public void toggleAndWaitForOnTextWithFluentWait(){
        tap(toggle);
        Wait<AndroidDriver> fluent = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class);
        fluent.until(d -> d.findElement(toggle).getText().equalsIgnoreCase("on"));
    }


}
