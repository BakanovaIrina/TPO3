import org.junit.jupiter.api.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthTest {

    private WebDriver driver;
    private PikabuData data;

    @BeforeEach
    public void setUp() {
        data = new PikabuData();
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Irina\\IdeaProjects\\tpo3\\src\\test\\java\\resourses\\chromedriver.exe");
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\Irina\\IdeaProjects\\tpo3\\src\\test\\java\\resourses\\geckodriver.exe");
        //driver = new FirefoxDriver();

        driver = new ChromeDriver();
    }

    @Test
    public void correctLogin() {
        driver.get(data.getUrl());
        data.login(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement divElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'user__info-item')]")));
        String divText = divElement.getText();

        assertEquals(divText, data.getLogin());
    }

    @Test
    public void incorrectPassword() {
        driver.get(data.getUrl());
        data.loginWithIncorrectPassword(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='auth__error']")));

        String errorText = errorElement.getText();

        assertEquals(errorText, "Ошибка. Вы ввели неверные данные авторизации");
    }

    @Test
    public void exitTest() {
        driver.get(data.getUrl());
        data.login(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signOutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'user__exit')]")));
        signOutButton.click();

        WebElement exitButton = driver.findElement(By.xpath("//button[contains(@class, 'button button_danger')]"));
        exitButton.click();

        WebElement divElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'auth__header')]")));
        String divText = divElement.getText();

        assertEquals(divText, "Войти");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

