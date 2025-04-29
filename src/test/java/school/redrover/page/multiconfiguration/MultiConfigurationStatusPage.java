package school.redrover.page.multiconfiguration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class MultiConfigurationStatusPage extends BasePage {

    public MultiConfigurationStatusPage (WebDriver driver) {
        super(driver);
    }

    public MultiConfigurationStatusPage clickEnableButton() {
        getDriver().findElement(By.xpath("//*[@id='enable-project']/button")).click();

        return this;
    }

    public boolean projectDisabledMessageCheck() {

        try {
            return getDriver().findElement(By.xpath("//*[@id='enable-project']")).isDisplayed();
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean messageNotDisplayedCheck() {
        try {
            getWait5().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='enable-project']")));
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}