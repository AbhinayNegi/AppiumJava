Appium Java learning framework.

Set ANDROID_HOME and JAVA_HOME in the environment path. Also, set the platform-tools of Android in the path, which is only available if Android Studio is installed

After switching to web view we need chrome driver to automate on web view. We can do this by running appium server with command appium --allow-insecure='*:chromedriver_autodownload' and then adding these capabilities in the code 
options.setCapability("appium:chromedriverAutodownload", true);
options.setCapability("appium:ensureWebviewsHavePages", true);
options.setCapability("appium:nativeWebScreenshot", true);

if the above does not work and throws error like io.appium.java_client.NoSuchContextException: No Chromedriver found that can automate Chrome '109.0.5414'. You could also try to enable automated chromedrivers downloads as a possible workaround. When switching context, try manually downloading the correct driver version and setting its path like this 
options.setCapability("appium:chromedriverExecutable", "C:\\Users\\negia\\AppData\\Roaming\\npm\\node_modules\\appium-chromedriver\\chromedriver\\win\\chromedriver.exe");
options.setCapability("appium:ensureWebviewsHavePages", true);
options.setCapability("appium:nativeWebScreenshot", true);
