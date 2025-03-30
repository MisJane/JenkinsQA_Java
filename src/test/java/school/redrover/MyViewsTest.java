package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import static org.testng.Assert.assertEquals;

public class MyViewsTest extends BaseTest{

    @Test
        public void testMyView(){
            WebDriver driver = getDriver();
            driver.findElement(By.xpath("//a[@href='/me/my-views']")).click();
            driver.findElement(By.xpath("//a[@href ='editDescription']")).click();
            driver.findElement(By.xpath("//textarea[@name ='description']")).sendKeys("MyGroupTest");
            driver.findElement(By.xpath("//button[@name='Submit']")).click();
            WebElement locator = driver.findElement(By.xpath("//*[@id='description']//div[text()='MyGroupTest']"));

            assertEquals(locator.getText(),"MyGroupTest");
        }

        @Test
        public void  testAddNewItem() throws InterruptedException {
            WebDriver driver = getDriver();
            driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
            driver.findElement(By.xpath("//*[@id='name']")).sendKeys("PipeLine1");
            driver.findElement(By.xpath("//*[@class='desc']")).click();
            driver.findElement(By.xpath("//*[@id='ok-button']")).click();
            Thread.sleep(3000);

            driver.findElement(By.xpath("//*[@id='jenkins-name-icon']")).click();
            Thread.sleep(3000);

            WebElement element = driver.findElement(By.xpath("//*[text()='PipeLine1']"));
            Thread.sleep(3000);

            assertEquals(element.getText(),"PipeLine1");
        }
        @Test
        public void testWelcomePage(){
                WebDriver driver = getDriver();
                WebElement welcome = driver.findElement(By.xpath("//*[text()='Welcome to Jenkins!']"));
                assertEquals(welcome.getText(),"Welcome to Jenkins!");
        }
    }
