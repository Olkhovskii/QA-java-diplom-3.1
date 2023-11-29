package chrome.driver.test;

import io.qameta.allure.junit4.DisplayName;
import ru.prakticum.User;
import ru.prakticum.UserClient;
import ru.prakticum.UserGenerator;
import ru.prakticum.page.object.LoginPage;
import ru.prakticum.page.object.RegisterPage;
import org.junit.Assert;
import org.junit.Test;

public class RegistrationTest extends BaseTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserClient userClient = new UserClient();
    private User user;
    private String accessToken;

    @Test
    @DisplayName("Успешная регистрация нового пользователея") //вход по кнопке «Войти в аккаунт» на главной,
    public void newAccountPage() {
        user = UserGenerator.getDefault();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.open();
        registerPage.register(user.getName(), user.getEmail(), user.getPassword());
        LoginPage loginPage = new LoginPage(driver);
        String expected = "Вход";
        String actual = loginPage.getTitleTextInput();
        Assert.assertEquals("Данные не совпадают", expected, actual);
        accessToken = userClient.getAccessTokenOnLogin(user);
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("регистрация пользоватлея с невалидным паролем")
    public void accountWithWrongPWD() {
        user = UserGenerator.getDefault();
        RegisterPage register = new RegisterPage(driver);
        register.open();
        register.register(user.getName(), user.getEmail(), "45$");
        String actualError = register.getErrorPasswordText();
        String expectedError = "Некорректный пароль";
        Assert.assertEquals("Минимальный пароль — шесть символов", expectedError, actualError);
    }
}