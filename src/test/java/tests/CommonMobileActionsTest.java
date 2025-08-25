package tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
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

}
