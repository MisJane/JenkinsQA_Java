package school.redrover.page.user;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class UserAdminPage extends BasePage {

    public UserAdminPage(WebDriver driver) {
        super(driver);
    }

    public String getAdminIDText() {
        return getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-app-bar h1"))).getText();
    }
}