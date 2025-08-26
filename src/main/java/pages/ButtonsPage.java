package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ButtonsPage extends BasePage{

    public ButtonsPage(AndroidDriver driver) {
        super(driver);
    }

    private final By SMALL_BTN = AppiumBy.accessibilityId("Small");

    public void clickSmallWithWait(){
        wait.until(ExpectedConditions.elementToBeClickable(SMALL_BTN)).click();
    }
}
