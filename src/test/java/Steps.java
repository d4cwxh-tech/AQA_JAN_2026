import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Steps {

    @Given("я отправляю GET запрос на {string}")
    public void test(String url) {
        System.out.println("--- НАЧИНАЕМ ПРОВЕРКУ API ---");

        // Прямое обращение к RestAssured, чтобы точно сработало
        RestAssured
                .given()
                .baseUri(url)
                .when()
                .get()
                .then()
                .statusCode(200) // Проверяем, что сервер ответил 200 (ОК)
                .log().status()  // Печатаем статус код
                .log().body();   // Печатаем данные в консоль

        System.out.println("\n--- ТЕСТ ПРОЙДЕН: СЕРВЕР ОТВЕТИЛ УСПЕШНО ---");
    }
}