package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderCardTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless=new"); // можно "--headless" если старый Chrome
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void shouldSubmitFormSuccessfully() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"))
                .click();
        driver.findElement(By.cssSelector("button.button"))
                .click();

        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']"))
                .getText();

        assertTrue(text.contains("Ваша заявка успешно отправлена"),
                "Ожидали сообщение об успехе, но получили: " + text);
    }
}
