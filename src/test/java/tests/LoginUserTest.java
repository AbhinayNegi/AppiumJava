package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.generalStoreApp.ProductPage;
import pages.generalStoreApp.StoreHomePage;

public class LoginUserTest extends BaseTest {

    @Test(dataProvider = "loginUsernames")
    public void loginUserTest(String username) {
        StoreHomePage homePage = new StoreHomePage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.enterNameAndLogin(username);
        String actualName = productPage.getProductPageHeading();

        Assert.assertEquals(actualName, "Products");
        homePage.goBack();
    }

    @DataProvider(name = "loginUsernames")
    public Object[][] loginData() {
        return new Object[][] {
                {"James"},
                {"Alex"},
                {"Mia"}
        };
    }
}
