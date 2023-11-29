package chrome.driver.test;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.prakticum.page.object.MainPage;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {
    public  WebDriver driver;
     MainPage mainPage;

    @Before
    public void setUp() {
            WebDriverManager.chromedriver().setup();
            Configuration.browser = "chrome";
            Configuration.startMaximized = true;
            mainPage = open(MainPage.URL, MainPage.class);
        }

    @After
    public void cleanUp() {
        driver.quit();
    }
}

