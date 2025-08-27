package tests;

import org.testng.annotations.Test;
import pages.HomePage;
import pages.ViewsPages;
import pages.WebView2Page;

public class CheckWebViewTest extends BaseTest{

    @Test
    public void switchToWebView() {
        HomePage home = new HomePage(driver);
        home.openViews();
        new ViewsPages(driver).openWebView2();
        new WebView2Page(driver).waitAndClickLinkInWebViewAndComeBackToNative();
    }
}
