package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class BaseTest {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    @BeforeClass
    @Parameters({"deviceName", "udid", "systemPort", "chromeDriverPort", "app", "appPackage", "appActivity"})
    public void setUp(@Optional("emulator-5554") String deviceName,
                      @Optional("emulator-5554") String udid,
                      @Optional("8201") String systemPort,
                      @Optional("9515") String chromeDriverPort,
                      @Optional("C:\\Work\\Appium automation\\src\\main\\resources\\apk\\ApiDemos-debug.apk") String app,
                      @Optional("io.appium.android.apis") String appPackage,
                      @Optional(".ApiDemos") String appActivity
    ) throws MalformedURLException {
        // --- IMPORTANT: start the Appium server separately: `appium` ---
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2") // which driver we are using to automate
                .setDeviceName(deviceName)// or your real-device name from `adb devices`
                .setUdid(udid)
                .setSystemPort(Integer.parseInt(systemPort))
                // ---- OPTION 1: provide an APK path (ensure it targets SDK >= 24)
                //.setApp("C:\\Users\\<you>\\Downloads\\YourModernSampleApp.apk")
                // ---- OPTION 2: launch an already-installed app using package/activity:
                .setApp(app)
//                .setAppPackage(appPackage) // the name of the already installed app
//                .setAppActivity(appActivity)        // adjust to your app’s launch activity or a particular screen of the app
                .setAutoGrantPermissions(true) // automatically grant all permission
                .setNewCommandTimeout(Duration.ofSeconds(3600)); // how long to keep connection active if tests does not send command

        options.setCapability("appium:chromedriverExecutableDir", "C:\\Users\\negia\\AppData\\Roaming\\npm\\node_modules\\appium-chromedriver\\chromedriver\\win");
        options.setCapability("appium:ensureWebviewsHavePages", true);
        options.setCapability("appium:nativeWebScreenshot", true);
        options.setCapability("appium:chromedriverPort", Integer.parseInt(chromeDriverPort));

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
