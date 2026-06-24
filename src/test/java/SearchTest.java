import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchTest {
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
    public void searchContentUser() {
        driver.get(data.getUrl());
        data.login(driver);

        WebElement searchMenu = driver.findElement(By.xpath("//div[contains(@class, 'header-right-menu__item header-right-menu__search')]"));

        Actions actions = new Actions(driver);
        actions.moveToElement(searchMenu).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='q']")));

        searchInput.sendKeys(data.getSearchText());
        searchInput.sendKeys(Keys.ENTER);

        WebElement linkElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'story__title-link')]")));
        String linkText = linkElement.getText();

        assertEquals(linkText, data.getSearchText());
    }

    @Test
    public void searchContentGuest() {
        driver.get(data.getUrl());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        WebElement searchMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'header-right-menu__item header-right-menu__search')]")));

        Actions actions = new Actions(driver);
        actions.moveToElement(searchMenu).perform();

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='q']")));

        searchInput.sendKeys(data.getSearchText());
        searchInput.sendKeys(Keys.ENTER);

        WebElement linkElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'story__title-link')]")));
        String linkText = linkElement.getText();

        assertEquals(linkText, data.getSearchText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

