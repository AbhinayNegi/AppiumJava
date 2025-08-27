package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.generalStoreApp.ProductPage;
import pages.generalStoreApp.StoreHomePage;

public class LoginUserTest extends BaseTest {

    private StoreHomePage homePage = new StoreHomePage(driver);
    private ProductPage productPage = new ProductPage(driver);

    @Test
    public void loginUserTest() {

        homePage.enterNameAndLogin("James");
        String actualName = productPage.getProductPageHeading();

        Assert.assertEquals(actualName, "Products");
    }
}
