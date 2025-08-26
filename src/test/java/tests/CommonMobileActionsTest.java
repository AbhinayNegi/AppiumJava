package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class CommonMobileActionsTest extends BaseTest{

    @Test
    public void startApp() throws InterruptedException {
//        driver.findElement(AppiumBy.accessibilityId("Accessibility")).click();
//        driver.findElement(AppiumBy.id("android:id/text1")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Accessibility']")).click();
    }

    @Test
    public void enterText() {
        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
        driver.findElement(AppiumBy.accessibilityId("3. Preference dependencies")).click();
        driver.findElement(AppiumBy.id("android:id/checkbox")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.RelativeLayout\").instance(1)")).click();
        driver.findElement(AppiumBy.id("android:id/edit")).sendKeys("Hello");
        driver.findElement(AppiumBy.id("android:id/button1")).click();
    }

    @Test
    public void printTextOfAllElements() {
        List<WebElement> elements = driver.findElements(AppiumBy.className("android.widget.TextView"));
        for(WebElement e : elements){
            System.out.println(e.getText());
        }
    }

    @Test
    public void verifyText() {
        List<WebElement> elements = driver.findElements(AppiumBy.className("android.widget.TextView"));
        String actualBtnText = elements.get(1).getText();
        Assert.assertEquals(actualBtnText, "Accessibility");
        System.out.println("First button text verified!");
    }

    @Test
    public void scrollToElement() {
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        scrollToTheElementByText("WebView").click();
    }

    @Test
    public void longPress() {
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Expandable Lists']")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='1. Custom Adapter']")).click();
        WebElement e = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"People Names\")"));
        performLongPress(e);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Sample action\")")).click();
    }

    @Test
    public void swipeLeft() {
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Gallery']")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='1. Photos']")).click();
        WebElement e = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)"));
        swipeLeftImageGesture(e);
    }

    @Test
    public void dragNDrop() {
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Drag and Drop']")).click();
        WebElement srcElement = driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement destElement = driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_dot_2"));
        dragNDropGesture(srcElement, destElement);
    }

    @Test
    public void switchToWebView() {
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        scrollToTheElementByText("WebView2").click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Views/WebView2\")")).isDisplayed();
        Set<String> contexts = driver.getContextHandles(); //WEBVIEW_io.appium.android.apis
        System.out.println(contexts);
        driver.context("WEBVIEW_io.appium.android.apis");
        driver.findElement(By.id("i am a link")).click();
    }

    @Test
    public void waitForClick() {
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        driver.findElement(AppiumBy.accessibilityId("Buttons")).click();
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Small"))).click();
    }

    @Test
    public void waitForActivityChange() {
        driver.findElement(AppiumBy.androidUIAutomator("text(\"App\")")).click();
        driver.findElement(AppiumBy.androidUIAutomator("text(\"Activity\")")).click();
        driver.findElement(AppiumBy.androidUIAutomator("text(\"Custom Title\")")).click();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(d -> ((AndroidDriver) d).currentActivity().contains("CustomTitle"));

        }catch(TimeoutException e){
            System.err.println("Does not contain expected activity name, the current activity is: " + driver.currentActivity());
            throw e;
        }
    }

    @Test
    public void waitForTextToChange() {
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        driver.findElement(AppiumBy.accessibilityId("Controls")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Light Theme")).click();
        By toggleBtn = AppiumBy.id("io.appium.android.apis:id/toggle1");
        driver.findElement(toggleBtn).click();
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> d.findElement(toggleBtn).getText().equalsIgnoreCase("on"));

    }

    @Test
    public void waitForToast() {
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        scrollToTheElementByText("Spinner").click();
        driver.findElement(AppiumBy.id("io.appium.android.apis:id/spinner1")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"orange\")")).click();

        By toast = AppiumBy.xpath("//android.widget.Toast");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(toast));

    }

    @Test
    public void waitAndSwitchToWebView() {
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        scrollToTheElementByText("WebView2").click();
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> ((AndroidDriver)d).getContextHandles().stream().anyMatch(c -> c.startsWith("WEBVIEW")));
        Set<String> contexts = driver.getContextHandles(); //WEBVIEW_io.appium.android.apis
        System.out.println(contexts);
        String webView = driver.getContextHandles().stream().filter(c -> c.startsWith("WEBVIEW")).findFirst().get();
        driver.context(webView);
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("i am a link"))).click();
        driver.context("NATIVE_APP");
        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    @Test
    public void fluentWait() {
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Views']")).click();
        driver.findElement(AppiumBy.accessibilityId("Controls")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Light Theme")).click();
        By toggleBtn = AppiumBy.id("io.appium.android.apis:id/toggle1");
        driver.findElement(toggleBtn).click();

        Wait<AndroidDriver> fluent = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(10))
                .ignoring(NoSuchElementException.class);
        fluent.until(d -> d.findElement(toggleBtn).getText().equalsIgnoreCase("on"));
    }
}
