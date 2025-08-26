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

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver quited");
        }
    }
}
