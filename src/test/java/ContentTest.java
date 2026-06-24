import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContentTest {
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
    public void selectContentUser() {
        driver.get(data.getUrl());
        data.login(driver);

        String originalTab = driver.getWindowHandle();

        WebElement linkElement = driver.findElement(By.xpath("//a[contains(@class, 'story__title-link')]"));
        String linkText = linkElement.getText();

        linkElement.click();

        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(originalTab)) {
                driver.switchTo().window(tab);
                break;
            }
        }

        String pageTitle = driver.getTitle();
        assertEquals(pageTitle, linkText + " | Пикабу");
    }

    @Test
    public void selectContentGuest() {
        driver.get(data.getUrl());
        String originalTab = driver.getWindowHandle();

        WebElement linkElement = driver.findElement(By.xpath("//a[contains(@class, 'story__title-link')]"));
        String linkText = linkElement.getText();

        linkElement.click();

        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(originalTab)) {
                driver.switchTo().window(tab);
                break;
            }
        }

        String pageTitle = driver.getTitle();
        assertEquals(pageTitle, linkText + " | Пикабу");
    }

    @Test
    public void rateContentUp() {
        driver.get(data.getUrl());
        data.login(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement ratingCountBefore = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'story__rating-count')]")));

        int likesBefore = Integer.parseInt(ratingCountBefore.getText());

        WebElement ratingButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'story__rating-plus')]")));
        ratingButton.click();

        WebElement ratingCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'story__rating-count')]")));

        String likesText = ratingCount.getText();
        int likesCount = Integer.parseInt(likesText);

        assertEquals(likesBefore + 1, likesCount);
    }

    @Test
    public void rateContentDown() {
        driver.get(data.getUrl());
        data.login(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement ratingCountBefore = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'story__rating-count')]")));
        int likesBefore = Integer.parseInt(ratingCountBefore.getText());

        WebElement ratingButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'story__rating-down')]")));
        ratingButton.click();

        WebElement ratingCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'story__rating-count')]")));

        String likesText = ratingCount.getText();
        int likesCount = Integer.parseInt(likesText);

        assertEquals(likesBefore, likesCount);
    }

    @Test
    public void savePost() {
        driver.get(data.getUrl());
        data.login(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'story__save')]")));
        saveButton.click();

        WebElement linkElement = driver.findElement(By.xpath("//a[contains(@class, 'story__title-link')]"));
        String linkText = linkElement.getText();

        WebElement savedLink = driver.findElement(By.xpath("//a[@data-menu-item='saved']"));
        savedLink.click();

        WebElement linkElementTitle = driver.findElement(By.xpath("//a[contains(@class, 'story__title-link_visited')]"));
        String linkTextTitle = linkElementTitle.getText();

        assertEquals(linkText, linkTextTitle);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

