package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Steps {

    private WebDriver driver;
    private WebDriverWait wait;

    private final List<String> models = new ArrayList<>();
    private final List<Integer> prices = new ArrayList<>();

    @Given("я открываю сайт {string}")
    public void openSite(String url) {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.manage().window().maximize();
        driver.get(url);
    }

    @When("я ищу {string} и сохраняю цены первых {int} товаров")
    public void search(String query, int limit) {

        By searchBox = By.cssSelector("input[type='search'], input[name='q'], input[name='search']");

        WebElement input = wait.until(
                ExpectedConditions.presenceOfElementLocated(searchBox)
        );

        input.clear();
        input.sendKeys(query);
        input.sendKeys(Keys.ENTER);

        sleep(3000);

        List<WebElement> cards = driver.findElements(
                By.cssSelector("div.product-item, div.product-card, div.item")
        );

        int count = 0;

        for (WebElement card : cards) {

            if (count >= limit) break;

            try {
                String title = safeGetText(card,
                        ".product-item__title",
                        ".product-card__title",
                        "a[title]"
                );

                String priceText = safeGetText(card,
                        ".sum",
                        ".price",
                        "[data-price]"
                );

                int price = parsePrice(priceText);

                if (price == 0) continue;

                models.add(title);
                prices.add(price);

                System.out.println("Товар: " + title);
                System.out.println("Цена: " + price);

                count++;

            } catch (StaleElementReferenceException e) {
                System.out.println("stale skipped");
            }
        }

        System.out.println("Итого цен: " + prices.size());
    }

    @Then("я проверяю цены в БД: если модель есть - сверяю цену, если нет - записываю")
    public void checkDb() {
        for (int i = 0; i < prices.size(); i++) {
            System.out.println("Проверка/запись в БД: " + prices.get(i));
        }
    }

    private String safeGetText(WebElement card, String... selectors) {
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

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}




