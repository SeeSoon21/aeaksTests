package sillenium;

import org.assertj.core.api.Assertions;
import org.example.service.ConfProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

public class Tests {
    private ChromeDriver driver;
    private WebDriverWait wait;

    private String pageNotFound = "Page not found";
    private String name = "cucumberismybro";
    private String email = "egornesobaka@yandex.ru";
    private String password = "bebraMet10";

    //BeforeTest -- используется, чтобы уменьшить количество повторяемого кода
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Шаблон для тестирования будущей страницы регистрации
     */
    @Test
    public void registrationNotFoundTest() {
        int num = new Random().nextInt(100) + 1;
        String name = num + "cucumberismybro";
        String email = num + "egornesobaka@yandex.ru";
        String password = "bebraMet10";

        driver.get("http://127.0.0.1:8000/registration");

        //вернёт строку, типа "page not found at..."
        String result = driver.getTitle();

        assertTrue(result.contains(pageNotFound));
    }

    /**
     * Шаблон для тестирования будущей страницы авторизации
     */
    @Test
    public void authorizationNotFoundTest() {
        driver.get("http://127.0.0.1:8000/login");
        String result = driver.getTitle();

        assertTrue(result.contains(pageNotFound));
    }

    /**
     * Открытие блока решенных задач
     */
    @Test
    public void historyOfSolvedTasksTest() {
        driver.get("http://127.0.0.1:8000/solvedTasks");
        String result = driver.getTitle();

        assertTrue(result.contains(pageNotFound));
    }

    /**
     * Страница с выбором задачи(после нажатия на кнопку "начнём")
     */
    @Test
    public void startButtonTest() {
        driver.get("http://127.0.0.1:8000/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='btn-text']")));
        driver.findElement(By.xpath("//span[@id='btn-text']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='code-btn']")));
    }

    /**
     * Страница с выбором задачи(выпадающий список)
     */
    @Test
    public void chooseTaskTest() {
        driver.get("http://127.0.0.1:8000/exercises");

        //тут должны получить элемент, типа "выбор задачи" и получить выпадающий список
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='btn-text']")));

        //а пока -- заглушка
        String result = driver.getTitle();
        assertTrue(result.contains("IndexError"));
    }

    /**
     * Получение результата о правильности решения задачи
     */
    @Test
    public void correctSolutionTest() {
        String textarea = "//textarea[@id='code-textarea']";

        driver.get("http://127.0.0.1:8000/exercises/?page=1");

        //ввод данных в textarea
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(textarea)));
        driver.findElement(By.xpath(textarea)).click();
        driver.findElement(By.xpath(textarea)).sendKeys
                ("def fun(x):\n" +
                        "    return x+1");

        //press send-btn
        driver.findElement(By.xpath("//button[@id='code-btn']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@value='False']")));
    }

    /**
     * Получение результата о проваленном решении задачи
     */
    @Test
    public void incorrectSolutionTest() {
        String textarea = "//textarea[@id='code-textarea']";

        driver.get("http://127.0.0.1:8000/exercises/?page=1");

        //ввод данных в textarea
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(textarea)));
        driver.findElement(By.xpath(textarea)).click();
        driver.findElement(By.xpath(textarea)).sendKeys
                ("def fun(x):\n" +
                        "    return x-1");

        //press send-btn
        driver.findElement(By.xpath("//button[@id='code-btn']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@value='False']")));
    }

    @After
    public void quit() {
        System.out.println("test close");
        //driver.quit();
    }

}
