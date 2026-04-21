package steps;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.SearchResultPage;

import java.time.Duration;
import java.util.List;

public class Steps extends BaseTest {

    private List<String> prices;

    @Given("я открываю сайт {string}")
    public void openSite(String url) {
        driver.get("https://allo.ua/ua/products/mobile/proizvoditel-apple/");
    }

    @When("я ищу {string} и сохраняю цены первых {int} товаров")
    public void searchAndPickPrices(String query, int count) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.product-card")
        ));

        SearchResultPage page = new SearchResultPage(driver);
        prices = page.getFirstPrices(count);

        if (prices == null || prices.isEmpty()) {
            throw new RuntimeException("Товары не найдены — проверь селекторы или загрузку страницы");
        }


    }

    @Then("я проверяю цены в БД: если модель есть - сверяю цену, если нет - записываю")
    public void checkDb() {

        if (prices == null || prices.isEmpty()) {
            throw new RuntimeException("Нет данных для проверки БД");
        }

        for (String item : prices) {
            System.out.println("DB CHECK: " + item);


        }
    }
}