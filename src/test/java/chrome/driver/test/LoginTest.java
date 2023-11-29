package chrome.driver.test;

import io.qameta.allure.junit4.DisplayName;
import ru.prakticum.User;
import ru.prakticum.UserClient;
import ru.prakticum.UserGenerator;
import ru.prakticum.page.object.LoginPage;
import ru.prakticum.page.object.MainPage;
import ru.prakticum.page.object.PasswordPage;
import ru.prakticum.page.object.RegisterPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginTest extends BaseTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private User user;
    private String accessToken;

    @Before
    @DisplayName("Регистрация аккаунта")
    public void accountTestRegistration() {
        user = UserGenerator.getDefault();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register(user.getName(), user.getEmail(), user.getPassword());
    }

    @Test
    @DisplayName("Вход в приложение по кнопке «Войти в аккаунт» на главной странице")
    public void loginFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickLogInButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        boolean expected = true;
        boolean actual = mainPage.checkoutButtonIsDisplayed();
        Assert.assertEquals("Пользовтель не смог авторизоваться", expected, actual);
    }

    @Test
    @DisplayName("вход через кнопку «Личный кабинет»")
    public void loginFromPersonalArea() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickPersonalAreaButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        boolean expected = true;
        boolean actual = mainPage.checkoutButtonIsDisplayed();
        Assert.assertEquals("Пользовтель не смог авторизоваться", expected, actual);
    }

    //вход через кнопку в форме регистрации
    @Test
    @DisplayName("Dход через кнопку в форме регистрации")
    public void loginFromRegistrationPage() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.clickLogInLink();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        MainPage mainPage = new MainPage(driver);
        boolean expected = true;
        boolean actual = mainPage.checkoutButtonIsDisplayed();
        Assert.assertEquals("Данные не совпадают", expected, actual);
    }

    @Test
    @DisplayName("вход через кнопку в форме восстановления пароля.")
    public void loginFromForgotPwdPage() {
        PasswordPage forgotPwdPage = new PasswordPage(driver);
        forgotPwdPage.open();
        forgotPwdPage.clickLogInLink();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());
        MainPage mainPage = new MainPage(driver);
        boolean expected = true;
        boolean actual = mainPage.checkoutButtonIsDisplayed();
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
