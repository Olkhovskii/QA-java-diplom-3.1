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

public class PersonalAccountTest extends BaseTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private User user;
    private String accessToken;

    @Before
    public void accountRegistration() {
        user = UserGenerator.getDefault();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register(user.getName(), user.getEmail(), user.getPassword());
    }

    @Test
    @DisplayName("переход по клику на «Личный кабинет». Пользователь не залогинен.")
    public void personalAreaNotLoggedInFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickPersonalAreaButton();
        LoginPage loginPage = new LoginPage(driver);
        String expected = "Вход";
        String actual = loginPage.getTitleTextInput();
        Assert.assertEquals("Не совершен выход из аккаунта пользователя", expected, actual);
    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет». Пользователь залогинен.")
    public void personalAreaLoggedInFromMainPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAreaButton();
        ProfilePage profilePage = new ProfilePage(driver);
        boolean expected = true;
        boolean actual = profilePage.checkProfileButton();
        Assert.assertEquals("Данные не совпадают", expected, actual);
    }

    @After
    public void accountDelete() {
        accessToken = userClient.getAccessTokenOnLogin(user);
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }
}
