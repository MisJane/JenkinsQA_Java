package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;


public class TestTest extends BaseTest {
    @Test
    public void testAddDescription() {
        WebDriver driver = getDriver();

        driver.findElement(By.xpath("//a[@id='description-link']")).click();
        driver.findElement(By.xpath("//textarea[@name='description']")).sendKeys("I'm a user");
        driver.findElement(By.xpath("//button[@formnovalidate='formNoValidate']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='description']/div[1]")).getText().contains("I'm a user"));
    }

    @Test
    public void testHomePagePOM(){
       String str = new HomePage(getDriver())
               .getWelcomeMessage();
       Assert.assertEquals(str, "Welcome to Jenkins!");

    }

    @Test
    public void testNewItemPOM(){
        String name = "Pipeline1";
        String str = new HomePage(getDriver())
                .clickNewItem()
                .sendItemName(name)
                .selectPipelineAndClickOk()
                .clickSave()
                .getProjectName();


        Assert.assertEquals(str, name);



//                .selectFolderAndClickOk()
//                .getConfidurationText();

    }

}
