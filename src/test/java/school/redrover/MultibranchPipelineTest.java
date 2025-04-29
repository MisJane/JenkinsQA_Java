package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.multibranch.MultibranchProjectPage;


public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_NAME = "Multibranch Pipeline Job Test";

    @Test
    public void testCreateNewJobMultibranch() {
        MultibranchProjectPage multibranchProjectPage = new HomePage(getDriver())
                .createJob()
                .sendItemName(MULTIBRANCH_NAME)
                .selectMultibranchAndClickOk()
                .clickSaveButton();

        Assert.assertEquals(multibranchProjectPage.getProjectName(), MULTIBRANCH_NAME);
    }

    @Test
    public void testAddDescriptionCreatingMultibranch() {
        final String expectedDescription = "Add description";

        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("MultiBranch");

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("[name$='description']")).sendKeys(expectedDescription);
        getDriver().findElement(By.name("Submit")).click();

        String actualDescription = getDriver().findElement(By.id("view-message")).getText();
        Assert.assertEquals(actualDescription, expectedDescription);
    }

    @Test
    public void testCreateWithSpecialSymbols() {
        String[] specialCharacters = {"!", "%", "&", "#", "@", "*", "$", "?", "^", "|", "/", "]", "["};

        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        WebElement nameField = getDriver().findElement(By.xpath("//input[@name='name']"));

        for (String specChar : specialCharacters) {
            nameField.clear();
            nameField.sendKeys("Mult" + specChar + "branch");

            WebElement actualMessage = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.
                    xpath("//div[@id='itemname-invalid']")));

            String expectMessage = "» ‘" + specChar + "’ is an unsafe character";
            Assert.assertEquals(actualMessage.getText(), expectMessage, "Message is not displayed");
        }
    }

    @Test
    public void testVerifySectionHasTooltip() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("MultiBranchPipeline");

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        String actualTooltip = getDriver().
                findElement(By.xpath("//*[contains(text(), '" + "Display Name" + "')]//a"))
                .getAttribute("tooltip");

        Assert.assertEquals(actualTooltip, "Help for feature: Display Name");
    }

    @Ignore
    @Test
    public void testTryCreateProjectExistName() throws InterruptedException {

        final String projectName = "MultiBuild";
        final String errorMessage = "» A job already exists with the name " + "‘" + projectName + "’";

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tasks']/div[1]/span/a"))).click();
        getDriver().findElement(By.className("jenkins-input")).sendKeys(projectName);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        JavascriptExecutor js1 = (JavascriptExecutor) getDriver();
        js1.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.name("Submit")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='jenkins-name-icon']"))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tasks']/div[1]/span/a"))).click();
        JavascriptExecutor js2 = (JavascriptExecutor) getDriver();
        js2.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();

        String actualMessage = getDriver().findElement(By.xpath("//*[@id='itemname-invalid']")).getText();
        Assert.assertEquals(actualMessage, errorMessage);
    }
}
