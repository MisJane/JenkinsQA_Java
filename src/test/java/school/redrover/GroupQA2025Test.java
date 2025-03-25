package school.redrover;

import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class GroupQA2025Test {

    private WebDriver driver;

    @BeforeTest
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("http://uitestingplayground.com/");
    }

    @AfterMethod
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testClientSideDelay() {
        driver.findElement(By.linkText("Client Side Delay")).click();
        driver.findElement(By.id("ajaxButton")).click();

        assertEquals(driver.findElement(By.className("bg-success")).getText(),
                "Data calculated on the client side.");
    }

    @Test
    public void testUitapSampleAppEmptyLoginEmptyPassword() {
        driver.findElement(By.linkText("Sample App")).click();
        driver.findElement(By.xpath("//button[@class = 'btn btn-primary']")).click();

        assertEquals(
                driver.findElement(By.xpath("//label[@class = 'text-danger']")).getText(),
                "Invalid username/password"
        );
    }

    @Test
    public void testUitapSampleAppCorrectLoginEmptyPassword() {
        driver.findElement(By.linkText("Sample App")).click();
        driver.findElement(By.xpath("//input[@name = 'UserName']")).sendKeys("UserName");
        driver.findElement(By.xpath("//button[@class = 'btn btn-primary']")).click();

        assertEquals(
                driver.findElement(By.xpath("//label[@class = 'text-danger']")).getText(),
                "Invalid username/password"
        );
    }

    @Test
    public void testUitapSampleAppEmptyLoginCorrectPassword() {
        driver.findElement(By.linkText("Sample App")).click();
        driver.findElement(By.xpath("//input[@name = 'Password']")).sendKeys("pwd");
        driver.findElement(By.xpath("//button[@class = 'btn btn-primary']")).click();

        assertEquals(
                driver.findElement(By.xpath("//label[@class = 'text-danger']")).getText(),
                "Invalid username/password"
        );
    }

    @Test
    public void testUitapSampleAppCorrectLoginCorrectPassword() {
        driver.findElement(By.linkText("Sample App")).click();
        driver.findElement(By.xpath("//input[@name = 'UserName']")).sendKeys("UserName");
        driver.findElement(By.xpath("//input[@name = 'Password']")).sendKeys("pwd");
        driver.findElement(By.xpath("//button[@class = 'btn btn-primary']")).click();

        assertEquals(
                driver.findElement(By.xpath("//label[@class = 'text-success']")).getText(),
                "Welcome, UserName!"
        );
    }

    @Test
    public void linkIsClickableTest() {
        assertTrue(driver.findElement(By.cssSelector("[href=\"/dynamicid\"]")).isEnabled());
    }

    @Test
    public void getLinkName() {
        assertEquals(driver.findElement(By.cssSelector("[href=\"/dynamicid\"]")).getText(),"Dynamic ID");
    }

    @Test
    public void getLinkNameShorter() {
        assertEquals(driver.findElement(By.cssSelector("[href=\"/classattr\"]")).getText(), "Class Attribute");
    }
}