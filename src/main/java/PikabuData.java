import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PikabuData {
    private final String login = "labItmo";
    private final String password = "12345Haha";

    private final String incorrectPassword = "Haha12345";
    private final String url = "https://pikabu.ru/";

    private final String searchText = "Котопашка";


    public void login(WebDriver driver){
        WebElement loginField = driver.findElement(By.name("username"));
        loginField.clear();
        loginField.sendKeys(login);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.clear();
        passwordField.sendKeys(password);

        WebElement loginButton = driver.findElement(By.cssSelector(".button_success.button_width_100"));
        loginButton.click();
    }

    public void loginWithIncorrectPassword(WebDriver driver){
        WebElement loginField = driver.findElement(By.name("username"));
        loginField.clear();
        loginField.sendKeys(login);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.clear();
        passwordField.sendKeys(incorrectPassword);

        WebElement loginButton = driver.findElement(By.cssSelector(".button_success.button_width_100"));
        loginButton.click();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getIncorrectPassword() {
        return incorrectPassword;
    }

    public String getSearchText() {
        return searchText;
    }
}
