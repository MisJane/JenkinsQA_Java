package school.redrover.page.folder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.page.HomePage;

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

    public FolderProjectPage createJobInFolder(){
        getDriver().findElement(By.linkText("Create a job")).click();

        return this;
    }

    public FolderProjectPage sendItemName(String name) {
        getDriver().findElement(By.id("name")).sendKeys(name);

        return this;
    }

    public HomePage selectFreestyleClickOkAndReturnToHomePage() {
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("jenkins-home-link"))).click();

        return new HomePage(getDriver());
    }
}
