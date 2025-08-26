package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ViewsPages extends BasePage{
    private final By EXPANDABLE_LISTS = AppiumBy.accessibilityId("Expandable Lists");
    private final By CUSTOM_ADAPTER   = AppiumBy.accessibilityId("1. Custom Adapter");
    private final By GALLERY          = AppiumBy.accessibilityId("Gallery");
    private final By PHOTOS           = AppiumBy.accessibilityId("1. Photos");
    private final By DRAG_AND_DROP    = AppiumBy.accessibilityId("Drag and Drop");
    private final By BUTTONS          = AppiumBy.accessibilityId("Buttons");
    private final By CONTROLS         = AppiumBy.accessibilityId("Controls");

    public ViewsPages(AndroidDriver driver) {
        super(driver);
    }

    public void openExpandableLists() {
        tap(EXPANDABLE_LISTS);
        tap(CUSTOM_ADAPTER);
    }

    public void openGalleryPhotos() {
        tap(GALLERY);
        tap(PHOTOS);
    }

    public void openDragAndDrop() {
        tap(DRAG_AND_DROP);
    }

    public void openButtons() {
        tap(BUTTONS);
    }

    public void openControlsLightTheme() {
        tap(CONTROLS);
        tap(AppiumBy.accessibilityId("1. Light Theme"));
    }

    public void openWebView2() {
        scrollToTheElementByText("WebView2").click();
    }

    public WebElement firstPhoto() {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().className(\"android.widget.ImageView\").instance(0)"
        ));
    }

    public WebElement dragDot1() {
        return driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_dot_1"));
    }

    public WebElement dragDot2() {
        return driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_dot_2"));
    }
}
