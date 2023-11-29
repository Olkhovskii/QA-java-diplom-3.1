package chrome.driver.test;

import io.qameta.allure.junit4.DisplayName;
import ru.prakticum.page.object.MainPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConstructorTest extends BaseTest {
    private MainPage mainPage;

    @Before
    public void openMainPage() {
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    @DisplayName("Проверка перехода к разделу Булки")
    public void selectBuns() {
        mainPage.clickSauceButton();
        mainPage.clickBunsButton();
        boolean expected = true;
        Assert.assertTrue(mainPage.checkoutSubtitleBunIsDisplayed());
    }

    @Test
    public void selectSauces() {
        mainPage.clickSauceButton();
        boolean expected = true;
        boolean actual = mainPage.checkoutSubtitleSaucesIsDisplayed();
        Assert.assertEquals("Переход к разделу соусы не осуществлен", expected, actual);
    }

    @Test
    public void selectFillings() {
        mainPage.clickFillingsButton();
        boolean expected = true;
        boolean actual = mainPage.checkoutSubtitleFillingIsDisplayed();
        Assert.assertEquals("Переход к разделу соусы не осуществлен", expected, actual);
    }
}
