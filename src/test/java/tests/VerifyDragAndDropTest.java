package tests;

import org.testng.annotations.Test;
import pages.BasePage;
import pages.HomePage;
import pages.ViewsPages;

public class VerifyDragAndDropTest extends BaseTest{

    @Test
    public void dragNDrop() {
        HomePage home = new HomePage(driver);
        home.openViews();
        ViewsPages views = new ViewsPages(driver);
        views.openDragAndDrop();
        new BasePage(driver).dragNDropGesture(views.dragDot1(), views.dragDot2());
    }
}
