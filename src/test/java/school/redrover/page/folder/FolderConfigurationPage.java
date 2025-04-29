package school.redrover.page.folder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.FolderTest;
import school.redrover.common.BasePage;
import school.redrover.page.HomePage;

public class FolderConfigurationPage extends BasePage {

    public FolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public FolderConfigurationPage sendDisplayName(String text) {
        getDriver().findElement(By.xpath("//input[@checkdependson]")).sendKeys(text);

        return this;
    }

    public FolderConfigurationPage sendDescription(String text) {
        getDriver().findElement(By.xpath("//textarea[@name='_.description']")).sendKeys(text);

        return this;
    }

    public FolderProjectPage clickSave() {
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        return new FolderProjectPage(getDriver());
    }

    public HomePage saveAndReturnToHomePage() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("jenkins-home-link"))).click();

        return new HomePage(getDriver());
    }

}
