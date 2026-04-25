package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {

    WebDriver driver;
    WebDriverWait wait;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public List<String[]> getFirstProducts(int count) {

        List<String[]> result = new ArrayList<>();

        List<WebElement> items = wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.xpath("//div[contains(@class,'product-card')]"), 2
                )
        );

        for (WebElement item : items) {
            try {
                String name = item.findElement(
                        By.xpath(".//a[contains(@class,'product-card__title')]")
                ).getText();

                String price = item.findElement(
                        By.xpath(".//span[contains(@class,'sum')]")
                ).getText();

                if (!name.isEmpty() && !price.isEmpty()) {
                    result.add(new String[]{name, price});
                }

                if (result.size() == count) break;

            } catch (Exception ignored) {
            }
        }

        return result;
    }
}