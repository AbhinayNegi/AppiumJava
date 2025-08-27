package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WebView2Page extends BasePage{
    private final By LINK = By.id("i am a link");

    public WebView2Page(AndroidDriver driver) {
        super(driver);
    }

    public void waitAndClickLinkInWebViewAndComeBackToNative(){
        waitForWebView(30);
        switchToFirstWebView();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LINK)).click();
        switchToNative();
        goBack();
    }
}
