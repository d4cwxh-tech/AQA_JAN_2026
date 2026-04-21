package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {

    private WebDriver driver;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getFirstPrices(int count) {
        List<String> prices = new ArrayList<>();

        List<WebElement> items = driver.findElements(By.cssSelector("div.product-card"));

        for (WebElement item : items) {

            if (prices.size() >= count) break;

            try {
                String title = safeGetText(item,
                        By.cssSelector("a.product-card__title"),
                        By.xpath(".//a[contains(@class,'title')]")
                );

                String price = safeGetText(item,
                        By.cssSelector(".v-pb__cur"),
                        By.xpath(".//*[contains(text(),'₴')]")
                );

                if (!title.isEmpty() && !price.isEmpty()) {
                    prices.add(title + " | " + price);
                }

            } catch (Exception e) {
                System.out.println("Пропуск товара");
            }
        }

        System.out.println("Итого цен: " + prices.size());
        return prices;
    }

    private String safeGetText(WebElement root, By... locators) {
        for (By locator : locators) {
            try {
                return root.findElement(locator).getText();
            } catch (Exception ignored) {}
        }
        return "";
    }
}