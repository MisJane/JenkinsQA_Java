package school.redrover.page.organizationfolder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.page.HomePage;
import school.redrover.page.newitem.NewItemWithinFolderPage;

public class OrganizationFolderPage extends BasePage {

    public OrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    public OrganizationFolderPage clickDeleteOrganizationFolderOnLeftSidePanel() {
        getDriver().findElement(By.xpath("//a[@data-title='Delete Organization Folder']")).click();

        return this;
    }

    public void clickYesOnDeletionConfirmationPopup() {
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();
    }

    public String getMDeletionPopupText() {

        return getWait5().until(ExpectedConditions.visibilityOf(
                getDriver().findElement(By.className("jenkins-dialog__contents")))).getText();
    }

    public NewItemWithinFolderPage clickOnNewItemWithinFolder() {
        getDriver().findElement(By.xpath("//span[text()='New Item']/parent::a")).click();

        return new NewItemWithinFolderPage(getDriver());
    }

    public String getJobWithinFolderName(String jobName) {

        return getDriver().findElement(By.xpath(String.format("//span[text()='%s']", jobName))).getText();
    }

    public HomePage goToHomePage() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("jenkins-home-link"))).click();

        return new HomePage(getDriver());
    }

    public String getProjectName() {
        return getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();
    }
}
