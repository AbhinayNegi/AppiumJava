package tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.Test;
import pages.generalStoreApp.StoreHomePage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.util.Arrays;

public class AppManagementTest extends BaseTest {

    @Test
    public void appManagement() throws InterruptedException {
        if(!driver.isAppInstalled("com.androidsample.generalstore"))
            driver.installApp("C:\\Work\\Appium automation\\src\\main\\resources\\apk\\General-Store.apk");
        driver.activateApp("com.androidsample.generalstore");
        Thread.sleep(4000);
        driver.terminateApp("com.androidsample.generalstore");
        if(driver.isAppInstalled("com.androidsample.generalstore"))
            driver.removeApp("com.androidsample.generalstore");
    }

    @Test
    public void backgroundApp() {
        driver.runAppInBackground(Duration.ofSeconds(5));
    }

    @Test
    public void rotateApp() throws InterruptedException {
        driver.rotate(ScreenOrientation.LANDSCAPE);
        Thread.sleep(2000);
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @Test
    public void setTextOnClipboard() {
        if(!driver.isAppInstalled("com.androidsample.generalstore"))
            driver.installApp("C:\\Work\\Appium automation\\src\\main\\resources\\apk\\General-Store.apk");
        driver.activateApp("com.androidsample.generalstore");
        driver.setClipboardText("James");
        new StoreHomePage(driver).enterNameAndLogin(driver.getClipboardText());
    }

    @Test
    public void notificationAndSettings() throws InterruptedException {
        driver.openNotifications();
        Thread.sleep(5000);
        driver.toggleLocationServices();
        Thread.sleep(5000);
        driver.toggleWifi();
        Thread.sleep(5000);
        driver.toggleAirplaneMode();
    }

    @Test
    public void fileAndScreenshot() throws IOException {
        File localToPush = new File("C:\\Work\\Appium automation\\fileToPush.txt");
        if (!localToPush.exists()) {
            throw new FileNotFoundException("Missing file: " + localToPush.getAbsolutePath());
        }
        String remotePath = "/sdcard/Download/fileToPush.txt"; // Android side
        driver.pushFile(remotePath, localToPush);
        byte[] fileData = driver.pullFile(remotePath);
        Path outDir = Paths.get("C:\\local");
        Files.createDirectories(outDir); // <-- creates C:\local if missing
        Path outFile = outDir.resolve("pulled.txt");
        Files.write(outFile, fileData, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Wrote " + fileData.length + " bytes to " + outFile);

        File shot = driver.getScreenshotAs(OutputType.FILE);
        Path shotOut = outDir.resolve("screen.png");
        Files.copy(shot.toPath(), shotOut, StandardCopyOption.REPLACE_EXISTING);
//        Files.copy(shot.toPath(), Paths.get("C:\\local\\screenshot.png"));
    }

    @Test
    public void getDeviceInfo() {
        System.out.println(driver.getBatteryInfo().getLevel());
        System.out.println(driver.getOrientation());
        System.out.println(driver.getDeviceTime());
    }
}
