package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public List<WebElement> getProductCards() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-product-id], .product-item, .product-card")
        ));

        return driver.findElements(
                By.cssSelector("[data-product-id], .product-item, .product-card")
        );
    }

    public String getTitle(WebElement card) {
        return safeText(card,
                ".product-item__title",
                ".product-card__title",
                "a[title]"
        );
    }

    public String getPrice(WebElement card) {
        return safeText(card,
                ".sum",
                ".price",
                "[data-price]"
        );
    }

    public List<Integer> extractPrices(int limit) {
        List<Integer> prices = new ArrayList<>();

        List<WebElement> cards = getProductCards();

        for (WebElement card : cards) {
            if (prices.size() >= limit) break;

            try {
                String priceText = getPrice(card);
                int price = parsePrice(priceText);

                if (price > 0) {
                    prices.add(price);
                }

            } catch (StaleElementReferenceException ignored) {}
        }

        return prices;
    }

    private String safeText(WebElement card, String... selectors) {
        for (String sel : selectors) {
            List<WebElement> els = card.findElements(By.cssSelector(sel));
            if (!els.isEmpty()) {
                return els.get(0).getText();
            }
        }
        return "";
    }

    private int parsePrice(String text) {
        try {
            return Integer.parseInt(text.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }
}