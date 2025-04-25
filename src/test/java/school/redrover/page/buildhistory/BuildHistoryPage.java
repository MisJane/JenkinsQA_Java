package school.redrover.page.buildhistory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class BuildHistoryPage extends BasePage {

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public String isBuildHistoryText() {

        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText();
    }
}
