import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonalAccountTest {

    private WebDriver driver;
    private PikabuData data;

    @BeforeEach
    public void setUp() {
        data = new PikabuData();
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Irina\\IdeaProjects\\tpo3\\src\\test\\java\\resourses\\chromedriver.exe");
        driver = new ChromeDriver();
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\Irina\\IdeaProjects\\tpo3\\src\\test\\java\\resourses\\geckodriver.exe");
        //driver = new FirefoxDriver();
    }

    @Test
    public void getToAccount() {
        driver.get(data.getUrl());
        data.login(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Заменяем селектор на XPath
        WebElement avatarLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'header-right-menu__item avatar avatar_indicate-on-hover') and @data-own='true']")));
        avatarLink.click();

        // Заменяем селектор на XPath
        WebElement spanElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='labItmo']")));
        String content = spanElement.getText();

        assertEquals(content, data.getLogin());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

