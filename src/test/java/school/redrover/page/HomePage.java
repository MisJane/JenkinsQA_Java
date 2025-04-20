package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class HomePage extends BasePage {


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickAddDescriptionButton() {
        getDriver().findElement(By.id("description-link")).click();

        return this;
    }

    public boolean isDescriptionFieldDisplayed() {
        return getDriver().findElement(By.name("description")).isDisplayed();
    }

    public HomePage sendDescription(String text) {
        getDriver().findElement(By.cssSelector("#description > form > div.jenkins-form-item.tr > div.setting-main.help-sibling > textarea"))
                .sendKeys(text);

        return this;
    }

    public HomePage clickSaveDescriptionButton() {
        getDriver().findElement(By.cssSelector("#description > form > div:nth-child(3) > button"))
                .click();

        return this;
    }

    public String getDescriptionText() {
        return getDriver().findElement(By.cssSelector("#description > div:nth-child(1)")).getText();
    }

    public NewItemPage createJob() {
        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        return new NewItemPage(getDriver());
    }

    public NewItemPage clickNewItemOnLeftSidePanel() {
        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::span[@class='task-link-wrapper ']")).click();

        return new NewItemPage(getDriver());
    }

    public FreestyleProjectPage clickOnJobInListOfItems(String nameItem) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + nameItem + "']"))).click();

        return new FreestyleProjectPage(getDriver());
    }

    public PipelineConfigurationPage createNewPipeline(String projectName) {

        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::span[@class='task-link-wrapper ']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        return new PipelineConfigurationPage(getDriver());
    }
}
