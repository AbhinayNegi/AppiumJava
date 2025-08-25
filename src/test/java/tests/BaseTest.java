package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities; // not used, but keeps IDE suggestions happy
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

public class BaseTest {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    @BeforeClass
    public void setUp() throws MalformedURLException {
        // --- IMPORTANT: start the Appium server separately: `appium` ---
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2") // which driver we are using to automate
                .setDeviceName("emulator-5554")// or your real-device name from `adb devices`
                // ---- OPTION 1: provide an APK path (ensure it targets SDK >= 24)
                //.setApp("C:\\Users\\<you>\\Downloads\\YourModernSampleApp.apk")
                // ---- OPTION 2: launch an already-installed app using package/activity:
                .setAppPackage("io.appium.android.apis") // the name of the already installed app
                .setAppActivity(".ApiDemos")        // adjust to your app’s launch activity or a particular screen of the app
                .setAutoGrantPermissions(true) // automatically grant all permission
                .setNewCommandTimeout(Duration.ofSeconds(3600)); // how long to keep connection active if tests does not send command
        options.setCapability("appium:chromedriverExecutableDir", "C:\\Users\\negia\\AppData\\Roaming\\npm\\node_modules\\appium-chromedriver\\chromedriver\\win");
        options.setCapability("appium:ensureWebviewsHavePages", true);
        options.setCapability("appium:nativeWebScreenshot", true);
        driver = new AndroidDriver(
                URI.create("http://127.0.0.1:4723/").toURL(),
                options
        );

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // small implicit
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
        driver.perform(Arrays.asList(longPress));

    }

    public void swipeLeftImageGesture(WebElement element) {
        int startX = element.getRect().getX() + (int)(element.getSize().width * 0.9); // near right edge
        int endX   = element.getRect().getX() + (int)(element.getSize().width * 0.6); // near left edge
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

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver quited");
        }
    }
}
