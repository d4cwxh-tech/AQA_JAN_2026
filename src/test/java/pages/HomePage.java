package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void search(String text) {
        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input.search-form__input")
                )
        );
        input.clear();
        input.sendKeys(text);
        input.sendKeys(Keys.ENTER);
    }
}
