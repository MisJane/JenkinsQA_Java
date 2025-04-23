package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import java.time.Duration;

public class Pipeline5Test extends BaseTest {
    @Ignore
    @Test
    public void testCreatePipeline() {
        WebDriver driver = getDriver();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/view/all/newJob']")))
                .click();

        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("New Item");
        driver.findElement(By.xpath("//span[normalize-space()='Pipeline']")).click();
        driver.findElement(By.xpath("//button[@id='ok-button']")).click();
        driver.findElement(By.xpath("//textarea[@name='description']")).sendKeys("New pipeline");
        driver.findElement(By.xpath("//label[normalize-space()='GitHub project']")).click();
        driver.findElement(By.xpath("//input[@name='_.projectUrlStr']")).sendKeys("https://github.com/RedRoverSchool/JenkinsQA_Java_2025_spring.git");

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                driver.findElement(By.xpath("//label[contains(text(), 'GitHub hook trigger')]")));

        driver.findElement(By.xpath("//label[contains(text(), 'GitHub hook trigger')]")).click();
        driver.findElement(By.xpath("(//select[@class='jenkins-select__input dropdownList'])[2]")).click();
        driver.findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//a[contains(@class, 'model-link')]//span[contains(text(), 'New Item')]")).getText(),
                "New Item");
    }
}
