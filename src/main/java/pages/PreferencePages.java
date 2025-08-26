package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class PreferencePages extends BasePage{

    private final By PREFERENCE_DEPENDENCIES = AppiumBy.accessibilityId("3. Preference dependencies");
    private final By WIFI_CHECK_BOX = AppiumBy.id("android:id/checkbox");
    private final By WIFI_SETTING_ROW  = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.RelativeLayout\").instance(1)");
    private final By WIFI_EDIT = AppiumBy.id("android:id/edit");
    private final By OK_BTN = AppiumBy.id("android:id/button1");

    public PreferencePages(AndroidDriver driver) {
        super(driver);
    }

    public void openDependencies(){
        tap(PREFERENCE_DEPENDENCIES);
    }

    public void enableWifiAndSetName(String name){
        tap(WIFI_CHECK_BOX);
        tap(WIFI_SETTING_ROW);
        typeText(WIFI_EDIT, name);
        tap(OK_BTN);
    }
}
