package school.redrover.page.pipeline;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class PipelineProjectPage extends BasePage {

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
}
