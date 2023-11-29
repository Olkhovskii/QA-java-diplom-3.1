package chrome.driver.test;

import io.qameta.allure.junit4.DisplayName;
import ru.prakticum.User;
import ru.prakticum.UserClient;
import ru.prakticum.UserGenerator;
import ru.prakticum.page.object.LoginPage;
import ru.prakticum.page.object.MainPage;
import ru.prakticum.page.object.ProfilePage;
import ru.prakticum.page.object.RegisterPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogoutTest extends BaseTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private User user;
    private String accessToken;

    @Before
    @DisplayName("Успешная регистрация")
    public void accountRegistration() {
        user = UserGenerator.getDefault();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register(user.getName(), user.getEmail(), user.getPassword());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        String expected = "Вход";
        String actual = loginPage.getTitleTextInput();
        Assert.assertEquals("Что-то не так", expected, actual);
        loginPage.login(user.getEmail(), user.getPassword());
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAreaButton();
    }

    @Test
    @DisplayName("выход по кнопке «Выйти» в личном кабинете.")
    public void constructorButtonFromPersonalArea() {
        ProfilePage accountProfilePage = new ProfilePage(driver);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(accountProfilePage.getProfileSection()));
        accountProfilePage.clickLogOutButton();
        LoginPage loginPage = new LoginPage(driver);
        String expected = "Вход";
        String actual = loginPage.getTitleTextInput();
        Assert.assertEquals("Что-то не так", expected, actual);
    }

    @After
    public void accountDelete() {
        accessToken = userClient.getAccessTokenOnLogin(user);
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }
}
