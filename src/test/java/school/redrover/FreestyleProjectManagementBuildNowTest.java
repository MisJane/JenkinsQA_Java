package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;


public class FreestyleProjectManagementBuildNowTest extends BaseTest {

    final String name_Freestyle_Project = "new Freestyle Project";

    void createNewItemFrestyle(WebDriver driver) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/view/all/newJob']"))).click();
        driver.findElement(By.id("name")).sendKeys(name_Freestyle_Project);
        driver.findElement(By.className("hudson_model_FreeStyleProject")).click();
        driver.findElement(By.id("ok-button")).click();
        driver.findElement(By.name("Submit")).click();
    }
    @Test
    public void testAvailableBuildNowOnProjectPage() {
        WebDriver driver = getDriver();
        createNewItemFrestyle(driver);
        TestUtils.gotoHomePage(this);
        driver.findElement(By.xpath("//span[text()='" + name_Freestyle_Project + "']")).click();
        driver.findElement(By.xpath(  "//*[@class='task-link task-link-no-confirm ' and contains(@href,'build')]")).click();
        Assert.assertEquals(
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-bar"))).getText(),
                "Build scheduled");
    }
}
