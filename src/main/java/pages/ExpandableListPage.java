package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

public class ExpandableListPage extends BasePage{

    public ExpandableListPage(AndroidDriver driver) {
        super(driver);
    }

    public void longPressPeopleNamesAndTapSampleAction(){
        WebElement people = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"People Names\")"));
        performLongPress(people);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Sample action\")")).click();
    }
}
