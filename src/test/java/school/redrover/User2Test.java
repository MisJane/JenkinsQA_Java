package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class User2Test extends BaseTest {

    private void navigateToCreateUserPage() {
        WebDriver driver = getDriver();
        driver.findElement(By.cssSelector("a[href='/manage']")).click();
        driver.findElement(By.cssSelector("a[href='securityRealm/']")).click();
        driver.findElement(By.cssSelector("a[href='addUser']")).click();
    }

    public void createUser(String username, String password, String fullName, String email) {
        navigateToCreateUserPage();
        WebDriver driver = getDriver();
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.name("password1")).sendKeys(password);
        driver.findElement(By.name("password2")).sendKeys(password);
        driver.findElement(By.name("fullname")).sendKeys(fullName);
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("Submit")).click();
    }

    public void deleteUser() {
        getDriver().findElement(By.cssSelector("a[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("a[href='securityRealm/']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[data-message*='Delete Jenkins user']"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-id='ok']"))).click();
    }

    @Test
    public void testUserItemOnManageJenkinsPage() {
        final String expectedPath = "/manage/securityRealm/";
        WebDriver driver = getDriver();

        driver.findElement(By.cssSelector("a[href='/manage']")).click();
        driver.findElement(By.cssSelector("a[href='securityRealm/']")).click();

        getWait5().until(ExpectedConditions.urlContains(expectedPath));
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedPath),
                "Current URL doesn't contain expected path: " + expectedPath);
    }

    @Test
    public void testCreateUserPage() {
        final String expectedTitle = "Create User [Jenkins]";
        WebDriver driver = getDriver();

        navigateToCreateUserPage();

        getWait5().until(ExpectedConditions.titleIs(expectedTitle));
        Assert.assertEquals(driver.getTitle(), expectedTitle,
                "Page title does not match expected, actual title: " + driver.getTitle());
        WebElement usernameField = driver.findElement(By.id("username"));
        Assert.assertTrue(usernameField.isDisplayed(),
                "Username field is not visible on the Create User page");
    }

    @Test
    public void testCreateUserForm() {
        navigateToCreateUserPage();

        WebElement createUserButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit")));
        Assert.assertTrue(createUserButton.isDisplayed(), "'Create User' button is not visible.");
        List<WebElement> inputFields = getDriver().findElements(By.cssSelector(".jenkins-input"));
        Assert.assertEquals(inputFields.size(), 5, "Expected 5 input fields in the Create User form");
    }

    @Test
    public void testNewUserIsCreated() {
        final String username = "TestUser";

        createUser(username, "TestUserPassword", "Test User", "testuser@email.com");

        String actualUsername = getWait5().until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("a[href='user/testuser/']"))).getText();
        Assert.assertEquals(actualUsername, username, "The expected new user " + username + " is not displayed");
    }
    @Ignore
    @Test(dependsOnMethods = "testNewUserIsCreated")
    public void testDeleteUser() {
        deleteUser();

        boolean deletedUser = getWait5().until(ExpectedConditions
                .invisibilityOfElementLocated(By.cssSelector("a[href='user/testuser/']")));
        Assert.assertTrue(deletedUser, "The deleted User still visible on the page.");
    }
}
