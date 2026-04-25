package steps;

import base.BaseTest;
import db.DatabaseManager;
import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.HomePage;
import pages.SearchResultPage;

import java.util.List;

public class Steps extends BaseTest {

    List<String[]> products;

    @Given("я открываю сайт {string}")
    public void openSite(String url) {
        driver = getDriver();
        driver.get(url);
    }

    @When("я ищу {string} и сохраняю цены первых {int} товаров")
    public void search(String query, int count) {

        HomePage homePage = new HomePage(driver);
        homePage.search(query);

        SearchResultPage page = new SearchResultPage(driver);
        products = page.getFirstProducts(count);

        System.out.println("PRODUCTS SIZE: " + products.size());
    }

    @Then("я проверяю цены в БД: если модель есть - сверяю цену, если нет - записываю")
    public void checkDB() {

        if (products == null || products.isEmpty()) {
            throw new RuntimeException("Products not loaded");
        }

        for (String[] product : products) {

            String name = product[0];
            String price = cleanPrice(product[1]);

            String dbPrice = DatabaseManager.getPrice(name);

            if (dbPrice == null) {
                DatabaseManager.insert(name, price);
                System.out.println("INSERT: " + name + " -> " + price);
            } else {
                String cleanDbPrice = cleanPrice(dbPrice);

                System.out.println("COMPARE: " + name + " DB=" + cleanDbPrice + " SITE=" + price);

                if (!cleanDbPrice.equals(price)) {
                    DatabaseManager.update(name, price);
                    System.out.println("UPDATED: " + name + " -> " + price);
                }

                Assert.assertEquals(price, cleanDbPrice);
            }
        }
    }

    private String cleanPrice(String price) {
        return price.replaceAll("[^0-9]", "");
    }
}