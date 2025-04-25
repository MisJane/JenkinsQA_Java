package school.redrover.page.account;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class AccountPage extends BasePage {

    public AccountPage(WebDriver driver)  { super(driver); }

    public AccountPage sendDescription(String description) {
        getDriver().findElement(By.name("_.description")).sendKeys(description);

        return this;
    }

    public AccountPage clearDescription() {
        getDriver().findElement(By.name("_.description")).clear();
        this.clickSaveDescriptionButton();

        return this;
    }

    public AccountSettingsPage clickSaveDescriptionButton() {
        getDriver().findElement(By.name("Submit"))
                .click();

        return new AccountSettingsPage(getDriver());
    }
}
