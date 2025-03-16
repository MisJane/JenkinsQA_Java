package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class GroupRedRoverJavaUTC3Test {

    private WebDriver driver;

    private WebDriverWait wait5;
    private WebDriverWait wait10;

    protected WebDriverWait getWait5() {
        if (wait5 == null) {
            synchronized (WebDriverWait.class) {
                wait5 = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
            }
        }

        return wait5;
    }

    protected WebDriverWait getWait10() {
        if (wait10 == null) {
            synchronized (WebDriverWait.class) {
                wait10 = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            }
        }

        return wait10;
    }

    protected WebDriver getDriver() {
        if (driver == null) {
            synchronized (WebDriver.class) {
                driver = new ChromeDriver();
            }
        }

        return driver;
    }

    @BeforeMethod
    protected void start() {
        getDriver();
        getDriver().manage().window().maximize();
    }

    @AfterMethod
    protected void stop() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAuthorization() {

        getDriver().get("https://school.qa.guru/cms/system/login?required=true");

        getDriver().findElement(By.name("email")).sendKeys("gjcjavxusj@zvvzuv.com");
        getDriver().findElement(By.name("password")).sendKeys("E3&i&d1B");
        getDriver().findElement(By.id("xdget33092_1_1")).click();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Профиль']"))).click();

        getDriver().findElement(By.xpath("//*[@target='_self']")).click();

        String getName = getDriver().findElement(By.xpath("//h1[contains(text(),'Мой профиль')]")).getText();

        Assert.assertEquals(getName, "Мой профиль");
    }

    @Test
    public void RickAstleyTest() throws InterruptedException {

        String xPathPlayButton = "//button[@aria-keyshortcuts='k']";
        String xPathReject = "//button[contains(@aria-label, 'Reject the use of cookies')]";

        getDriver().get("https://www.youtube.com/watch?v=hPr-Yc92qaY");

        WebElement rejectButton = getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath(xPathReject)));

        if (rejectButton.isDisplayed()) {
            rejectButton.click();
        }

        getDriver().findElement(By.xpath(xPathPlayButton)).click();

        int count = 0;

        WebElement button = getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath(xPathPlayButton)));

        for (int i = 0; i < 10; i++) {
            Thread.sleep(9000);
            button.click();
            count++;
        }

        Assert.assertEquals(count, 10);
    }

    @Test
    public void testCheckBox() throws InterruptedException {

        getDriver().get("https://www.selenium.dev/selenium/web/web-form.html");

        getDriver().findElement(By.cssSelector("#my-check-2")).click();

        getDriver().findElement(By.xpath("//button[@class='btn btn-outline-primary mt-3']")).click();

        Thread.sleep(2000);

        String value = getDriver().findElement(By.id("message")).getText();

        Assert.assertEquals(value, "Received!");

    }

}