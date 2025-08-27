package pages.generalStoreApp;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import pages.BasePage;

public class ProductPage extends BasePage {

    private final By PRODUCT_PAGE_HEADING = AppiumBy.id("com.androidsample.generalstore:id/toolbar_title");

    public ProductPage(AndroidDriver driver) {
        super(driver);
    }

    public String getProductPageHeading(){
        return getTextOfTheElement(PRODUCT_PAGE_HEADING);
    }
}
