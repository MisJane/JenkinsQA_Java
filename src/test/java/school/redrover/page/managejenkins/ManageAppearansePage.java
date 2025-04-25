package school.redrover.page.managejenkins;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class ManageAppearansePage extends BasePage {

    public ManageAppearansePage(WebDriver driver)  { super(driver); }

    public ManageAppearansePage clickDefaultTheme() {
        getDriver().findElement(By.xpath("//label[span[text() = 'Default']]")).click();

        return this;
    }

    public ManageAppearansePage clickDarkSistemTheme() {
        getDriver().findElement(By.xpath("//label[span[text() = 'Dark (System)']]")).click();

        return this;
    }

    public ManageAppearansePage clickDarkTheme() {
        getDriver().findElement(By.xpath("//label[span[text() = 'Dark']]")).click();

        return this;
    }

    public ManageAppearansePage clickSaveButton() {
        getDriver().findElement(By.cssSelector("button.jenkins-button.apply-button")).click();

        return new ManageAppearansePage(getDriver());
    }

    public String getPopUpSaveButtonText() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id=\"notification-bar\"]"))).getText();
           }
}
