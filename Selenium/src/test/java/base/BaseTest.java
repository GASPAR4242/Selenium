package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }

    private static final ChromeOptions CHROME_OPTIONS;
    static {
        CHROME_OPTIONS = new ChromeOptions();
        String options = System.getenv("CHROME_OPTIONS");
        if (options != null) {
            for (String argument : options.split(";")) {
                CHROME_OPTIONS.addArguments(argument);
            }
        }
        CHROME_OPTIONS.addArguments("--window-size=1920,1080");

        WebDriverManager.chromedriver().setup();
    }

    private WebDriver driver;

    protected WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver(CHROME_OPTIONS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().deleteAllCookies();
    }

    @AfterMethod
    public void setDown() {
        driver.quit();
    }
}
