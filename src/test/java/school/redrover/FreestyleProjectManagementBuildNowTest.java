package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.TestUtils;


public class FreestyleProjectManagementBuildNowTest extends BaseTest {

    final String name_Freestyle_Project = "new Freestyle Project";

    void createNewItemFrestyle(WebDriver driver) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/view/all/newJob']"))).click();
        driver.findElement(By.id("name")).sendKeys(name_Freestyle_Project);
        driver.findElement(By.className("hudson_model_FreeStyleProject")).click();

        getWait5()
                .until(ExpectedConditions
                        .visibilityOfElementLocated(
                                By.id("ok-button")))
                .click();
        
        driver.findElement(By.name("Submit")).click();
    }

    @Test
    public void testAvailableBuildNowOnProjectPage() {
        WebDriver driver = getDriver();

        createNewItemFrestyle(driver);

        getWait5()
                .until(ExpectedConditions
                        .visibilityOfElementLocated(
                                By.xpath("//a[contains(@href,'build')][1]")))
                .click();

        Assert.assertEquals(
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-bar"))).getText(),
                "Build scheduled");
    }
    @Ignore
    @Test
    public void testAvailableBuildNowOnbreadcrumbs() {
        WebDriver driver = getDriver();

        createNewItemFrestyle(driver);

        TestUtils.gotoHomePage(this);

        getWait10()
                .until(ExpectedConditions
                        .visibilityOfElementLocated(
                                By.xpath("//td//a[@tooltip][contains(@href,'build')]")))
                .click();

        Assert.assertEquals(
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-bar"))).getText(),
                "Build scheduled");
    }

    @Ignore //Expected condition failed: waiting for visibility of element located by By.xpath: //td//a[@tooltip][contains(@href,'build')] (tried for 5 second(s) with 500 milliseconds interval)
    @Test
    public void testAvailableSuccesResult() {
        WebDriver driver = getDriver();

        createNewItemFrestyle(driver);

        TestUtils.gotoHomePage(this);

        getWait5()
                .until(ExpectedConditions
                        .visibilityOfElementLocated(
                                By.xpath("//td//a[@tooltip][contains(@href,'build')]")))
                .click();

        getDriver().findElement(By.linkText(name_Freestyle_Project)).click();
        getDriver().findElement(By.xpath("//*[@href='lastBuild/']")).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'console')]")).click();

        Assert.assertTrue(
                getWait10()
                        .until(ExpectedConditions.visibilityOfElementLocated(By.id("out"))).getText()
                        .contains("Finished: SUCCESS"),
                "статус не соответсвует ожидаемому Finished: SUCCESS"
        );
    }
}
