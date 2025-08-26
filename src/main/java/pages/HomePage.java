package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class HomePage extends BasePage{

    private final By ACCESSIBILITY = AppiumBy.accessibilityId("Accessibility");
    private final By PREFERENCE = AppiumBy.accessibilityId("Preference");
    private final By VIEWS = AppiumBy.accessibilityId("Views");
    private final By APP_PAGE = AppiumBy.androidUIAutomator("text(\"App\")");

    public HomePage(AndroidDriver driver) {
        super(driver);
    }

    public void openAccessibility(){
        tap(ACCESSIBILITY);
    }
    public void openPreference(){
        tap(PREFERENCE);
    }
    public void openViews(){
        tap(VIEWS);
    }
    public void openAppPage(){
        tap(APP_PAGE);
    }
}
