package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    @BeforeClass(alwaysRun = true)
    @Parameters({
            "deviceName", "udid", "systemPort", "chromeDriverPort",
            "app", "appPackage", "appActivity",
            "bsDevice", "bsOsVersion", "bsApp" // BS-only params
    })
    public void setUp(
            @Optional("emulator-5554") String deviceName,
            @Optional("emulator-5554") String udid,
            @Optional("8201") String systemPort,
            @Optional("9515") String chromeDriverPort,
            @Optional("C:\\Work\\Appium automation\\src\\main\\resources\\apk\\ApiDemos-debug.apk") String app,
            @Optional("io.appium.android.apis") String appPackage,
            @Optional(".ApiDemos") String appActivity,

            // BrowserStack params
            @Optional("Google Pixel 7") String bsDevice,
            @Optional("13.0") String bsOsVersion,
            @Optional("bs://<your-app-id>") String bsApp
    ) throws MalformedURLException {

        String runEnv = System.getProperty("runEnv", "local").trim().toLowerCase();

        if (runEnv.equals("bs") || runEnv.equals("browserstack")) {
            // ================
            // BROWSERSTACK HUB
            // ================

            String bsUser = System.getenv("BROWSERSTACK_USERNAME");
            String bsKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
            if (bsUser == null || bsKey == null) {
                throw new IllegalStateException("Set BROWSERSTACK_USERNAME and BROWSERSTACK_ACCESS_KEY env vars.");
            }

            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName("Android");
            options.setApp(bsApp);

            Map<String, Object> bstackOptions = new HashMap<>();
            bstackOptions.put("userName", bsUser);
            bstackOptions.put("accessKey", bsKey);
            bstackOptions.put("deviceName", bsDevice);
            bstackOptions.put("osVersion", bsOsVersion);

            // metadata
            bstackOptions.put("projectName", "Appium Java – Local + BrowserStack");
            bstackOptions.put("buildName", "Build #" + System.currentTimeMillis());
            bstackOptions.put("sessionName", this.getClass().getSimpleName());

            // debugging
            bstackOptions.put("debug", true);
            bstackOptions.put("networkLogs", true);
            bstackOptions.put("appiumVersion", "2.0.0");

            options.setCapability("bstack:options", bstackOptions);

            driver = new AndroidDriver(
                    new URL("https://hub-cloud.browserstack.com/wd/hub"),
                    options
            );

        } else {
            // =======
            // LOCAL
            // =======

            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setAutomationName("UiAutomator2")
                    .setDeviceName(deviceName)
                    .setUdid(udid)
                    .setSystemPort(Integer.parseInt(systemPort))
                    .setApp(app)
                    //.setAppPackage(appPackage)
                    //.setAppActivity(appActivity)
                    .setAutoGrantPermissions(true)
                    .setNewCommandTimeout(Duration.ofSeconds(3600));

            // WebView / chromedriver settings
            options.setCapability("appium:chromedriverExecutableDir",
                    "C:\\Users\\negia\\AppData\\Roaming\\npm\\node_modules\\appium-chromedriver\\chromedriver\\win");
            options.setCapability("appium:ensureWebviewsHavePages", true);
            options.setCapability("appium:nativeWebScreenshot", true);
            options.setCapability("appium:chromedriverPort", Integer.parseInt(chromeDriverPort));

            driver = new AndroidDriver(
                    URI.create("http://127.0.0.1:4723/").toURL(),
                    options
            );
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod(alwaysRun = true)
    public void markResult(ITestResult result) {
        String runEnv = System.getProperty("runEnv", "local").trim().toLowerCase();
        if ((runEnv.equals("bs") || runEnv.equals("browserstack")) && driver != null) {
            String status = result.getStatus() == ITestResult.SUCCESS ? "passed" : "failed";
            String reason = result.getStatus() == ITestResult.SUCCESS ?
                    "All assertions passed" :
                    result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error";

            try {
                ((JavascriptExecutor) driver).executeScript(
                        "browserstack_executor: {\"action\": \"setSessionStatus\", " +
                                "\"arguments\": {\"status\":\"" + status + "\", \"reason\": \"" + reason + "\"}}"
                );
            } catch (Exception e) {
                System.out.println("Could not update BrowserStack session status: " + e.getMessage());
            }
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver quited");
        }
    }
}
