package tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.util.List;

public class CommonMobileActionsTest extends BaseTest{

    @Test
    public void startApp() {
        new HomePage(driver).openAccessibility();
    }

    @Test
    public void enterText() {
        HomePage home = new HomePage(driver);
        home.openPreference();
        PreferencePages pref = new PreferencePages(driver);
        pref.openDependencies();
        pref.enableWifiAndSetName("Hello");
    }

    @Test
    public void printTextOfAllElements() {
        List<WebElement> buttons =
                driver.findElements(AppiumBy.className("android.widget.TextView"));
        buttons.forEach(e -> System.out.println(e.getText()));
    }

    @Test
    public void verifyText() {
        List<org.openqa.selenium.WebElement> buttons =
                driver.findElements(AppiumBy.className("android.widget.TextView"));
        Assert.assertEquals(buttons.get(1).getText(), "Accessibility");
    }

    @Test
    public void scrollToElement() {
        HomePage home = new HomePage(driver);
        home.openViews();
        new ViewsPages(driver).openWebView2(); // just demonstrates scrollToText under the hood
    }

    @Test
    public void longPress() {
        HomePage home = new HomePage(driver);
        home.openViews();
        ViewsPages views = new ViewsPages(driver);
        views.openExpandableLists();
        new ExpandableListPage(driver).longPressPeopleNamesAndTapSampleAction();
    }

    @Test
    public void swipeLeft() {
        HomePage home = new HomePage(driver);
        home.openViews();
        ViewsPages views = new ViewsPages(driver);
        views.openGalleryPhotos();
        new BasePage(driver).swipeLeftGesture(views.firstPhoto());
    }

    @Test
    public void dragNDrop() {
        HomePage home = new HomePage(driver);
        home.openViews();
        ViewsPages views = new ViewsPages(driver);
        views.openDragAndDrop();
        new BasePage(driver).dragNDropGesture(views.dragDot1(), views.dragDot2());
    }

    @Test
    public void waitForClick() {
        HomePage home = new HomePage(driver);
        home.openViews();
        new ViewsPages(driver).openButtons();
        new ButtonsPage(driver).clickSmallWithWait();
    }

    @Test
    public void waitForActivityChange() {
        HomePage home = new HomePage(driver);
        home.openAppPage();
        new AppActivityPage(driver).openCustomTitleAndWait();
    }

    @Test
    public void waitForTextToChange() {
        HomePage home = new HomePage(driver);
        home.openViews();
        ViewsPages views = new ViewsPages(driver);
        views.openControlsLightTheme();
        new ControlsLightThemePage(driver).toggleAndWaitForOnText();
    }

    @Test
    public void waitForTextToChangeFluentWait() {
        HomePage home = new HomePage(driver);
        home.openViews();
        ViewsPages views = new ViewsPages(driver);
        views.openControlsLightTheme();
        new ControlsLightThemePage(driver).toggleAndWaitForOnTextWithFluentWait();
    }

    @Test
    public void waitForToast() {
        HomePage home = new HomePage(driver);
        home.openViews();
        new BasePage(driver).scrollToTheElementByText("Spinner").click();
        new SpinnerPage(driver).chooseOrangeAndWaitToast();
    }

    @Test
    public void waitAndSwitchToWebView() {
        HomePage home = new HomePage(driver);
        home.openViews();
        new ViewsPages(driver).openWebView2();
        new WebView2Page(driver).waitAndClickLinkInWebViewAndComeBackToNative();
    }
}
