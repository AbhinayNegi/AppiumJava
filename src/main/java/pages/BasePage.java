package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;

public class BasePage {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;
    protected PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    protected WebElement getElement(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void tap(By locator){
        getElement(locator).click();
    }

    protected void typeText(By locator, String txt){
        WebElement txtField = getElement(locator);
        txtField.clear();
        txtField.sendKeys(txt);
    }

    protected String getTextOfTheElement(By locator){
        return getElement(locator).getText();
    }

    public WebElement scrollToTheElementByText(String text){
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\""+text+"\"))"
        ));
    }

    public void performLongPress(WebElement element) {
        /*
          PointerInput = represents an input source (like a finger, mouse, pen).
          Here: we create a touch pointer called "finger".
          All subsequent actions will use this finger.
         */
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        /*
            A Sequence = a set of actions performed in order by this pointer.
            We create a sequence called longPress, tied to "finger".
            The 1 is just the sequence ID.
         */
        Sequence longPress = new Sequence(finger, 1);

        // Moving the finger to x,y coordinate of the web element, here the finger is just hovering over the element
        longPress.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), element.getRect().getX(), element.getRect().getY()));
        // Simulate touch down of the finger on the desired coordinate
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(new Pause(finger, Duration.ofSeconds(2))); // hold for 2 sec
        // lift finger up
        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the sequence of long press
        driver.perform(Collections.singletonList(longPress));

    }

    public void swipeLeftGesture(WebElement element) {
        int startX = element.getRect().getX() + (int)(element.getSize().width * 0.9); // near right edge
        int endX   = element.getRect().getX() + (int)(element.getSize().width * 0.7); // near left edge
        int y      = element.getRect().getY() + (element.getSize().height / 2);       // middle of image

        Sequence swipeLeft = new Sequence(finger, 1);
        swipeLeft.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
                startX, y));
        swipeLeft.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipeLeft.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, 2));
        swipeLeft.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipeLeft));
    }

    public void dragNDropGesture(WebElement src, WebElement dest){
        Sequence dragDrop = new Sequence(finger, 1);
        int startX = src.getRect().getX() + (int)(src.getSize().getWidth() / 2);
        int startY = src.getRect().getY() + (int)(src.getSize().getHeight() / 2);
        int endX = dest.getRect().getX() + (int)(dest.getSize().getWidth() / 2);
        int endY = dest.getRect().getY() + (int)(dest.getSize().getHeight() / 2);

        dragDrop.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        dragDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        dragDrop.addAction(new Pause(finger, Duration.ofSeconds(3)));
        dragDrop.addAction(finger.createPointerMove(Duration.ofSeconds(1), PointerInput.Origin.viewport(), endX, endY));
        dragDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(dragDrop));
    }

    protected void waitForWebView(int sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(d -> ((AndroidDriver)d).getContextHandles().stream().anyMatch(c -> c.startsWith("WEBVIEW")));
    }

    protected void switchToFirstWebView() {
        Set<String> handles = driver.getContextHandles();
        String web = handles.stream().filter(h -> h.startsWith("WEBVIEW")).findFirst().get();
        driver.context(web);
    }

    protected void switchToNative() {
        driver.context("NATIVE_APP");
    }

    public void goBack() {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    protected void waitForToast() {
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.xpath("//android.widget.Toast")));
    }
}
