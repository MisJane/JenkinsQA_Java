package school.redrover;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import static org.testng.Assert.*;


public class UsernameFieldTest extends BaseTest {

    public void setUp(WebDriver driver) throws Exception {

        driver.findElement(By.xpath("//a[@href='/manage']")).click();
        driver.findElement(By.xpath("//a[@href='securityRealm/']")).click();
        driver.findElement(By.xpath("//a[@href='addUser']")).click();
    }

    @Test
    public void testUsernameFieldFocusAndEditable() throws Exception {
        WebDriver driver = getDriver();
        setUp(driver);
        WebElement usernameField = driver.findElement(By.id("username"));

        assertTrue(usernameField.isEnabled());
        usernameField.click(); // The ability to click on the field.

    }

    @Ignore
    @Test
    public void testUsernameFieldAcceptsOnlyValidChars() throws Exception {
        WebDriver driver = getDriver();
        setUp(driver);
        WebElement usernameField = driver.findElement(By.id("username"));

        // invalid values are entered
        usernameField.sendKeys("invalid user@name");
        WebElement submitButton = driver.findElement(By.name("Submit"));
        submitButton.click();

        WebElement errorMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='main-panel']/form/div[1]/div[2]"))
        );

        assertTrue(driver.findElement(By.cssSelector("div[class*='error']")).isEnabled());
        assertEquals(errorMessage.getText(),
                "User name must only contain alphanumeric characters, underscore and dash");

    }

    @Ignore
    @Test
    public void testEmptyUsernameValidation() throws Exception {
        WebDriver driver = getDriver();
        setUp(driver);
        WebElement submitButton = driver.findElement(By.name("Submit"));
        submitButton.click();


        WebElement errorMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='main-panel']/form/div[1]/div[2]"))
        );
        assertEquals(errorMessage.getText(),
                "\"\" is prohibited as a username for security reasons.");
    }

}
