package school.redrover.page.buildhistory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.page.freestyle.FreestyleConfigurationPage;

public class BuildHistoryPage extends BasePage {

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public String isBuildHistoryText() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText();
    }

    public String buildProjectText(String name) {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//span[text()='%s']/..".formatted(name)))).getText();
    }

    public String getTextNoBuilds() {

        return getDriver().findElement(By.xpath("//*[@id='no-builds']")).getText();
    }

    public BuildHistoryPage clickCollectNow() {
        getDriver().findElement(By.xpath("//a[@data-task-post='true']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated
                (By.className("app-builds-container__items"))).click();

        return this;
    }

    public BuildHistoryPage clickDeleteBuild() {
        getDriver().findElement(By.xpath("/html/body/div[3]/div[1]/div/div[5]/span/a")).click();
        getDriver().findElement(By.xpath("//span[text()='Delete the build ‘#1’?']")).getText();
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }
}