package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class AppActivityPage extends BasePage{

    public AppActivityPage(AndroidDriver driver) {
        super(driver);
    }

    public void openCustomTitleAndWait(){
        tap(AppiumBy.androidUIAutomator("text(\"Activity\")"));
        tap(AppiumBy.androidUIAutomator("text(\"Custom Title\")"));
        new WebDriverWait(driver, java.time.Duration.ofSeconds(30))
                .until(d -> Objects.requireNonNull(driver.currentActivity()).contains("CustomTitle"));
    }
}
