package school.redrover.page.folder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class FolderProjectPage extends BasePage {

    public FolderProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();
    }

    public String getDescription() {
        return getDriver().findElement(By.id("view-message")).getText();
    }

    public String getFolderStatus() {
        return getDriver().findElement(By.className("h4")).getText();
    }
}
