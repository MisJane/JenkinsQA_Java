package school.redrover.page.pipeline;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.page.HomePage;

public class PipelineProjectPage extends BasePage {

    @FindBy(css = "[data-title='Delete Pipeline']")
    private WebElement buttonDeletePipeline;

    @FindBy(css = "[data-id='ok']")
    private WebElement buttonConfirmDeletion;

    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return getDriver().findElement(By.className("page-headline")).getText();
    }

    public String getDescription() {
        return getDriver().findElement(By.cssSelector("#description >div")).getText();
    }

    public PipelineConfigurationPage clickConfigure() {
        getDriver().findElement(By.cssSelector("[href$='configure']")).click();

        return new PipelineConfigurationPage(getDriver());
    }

    public String getProjectDisabledStatus() {
        return getDriver().findElement(By.id("enable-project")).getText();
    }

    public WebElement getDisabledProjectWarningMessageElement() {
        return getDriver().findElement(By.id("enable-project"));
    }

    public boolean isDisabledProjectWarningMessageDisplayed() {
        return getDisabledProjectWarningMessageElement().isDisplayed();
    }

    public String getDisabledProjectWarningMessageText() {
        return getDisabledProjectWarningMessageElement().getText();
    }

    public HomePage deletePipeline() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buttonDeletePipeline)).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(buttonConfirmDeletion)).click();

        return new HomePage(getDriver());
    }
}
