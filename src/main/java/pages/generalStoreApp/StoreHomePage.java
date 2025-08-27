package pages.generalStoreApp;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import pages.BasePage;

public class StoreHomePage extends BasePage {

    private final By NAME_FIELD = AppiumBy.id("com.androidsample.generalstore:id/nameField");
    private final By LETS_SHOP_BTN = AppiumBy.id("com.androidsample.generalstore:id/btnLetsShop");

    public StoreHomePage(AndroidDriver driver) {
        super(driver);
    }

    public void enterNameAndLogin(String name) {
        typeText(NAME_FIELD, name);
        tap(LETS_SHOP_BTN);
    }
}
