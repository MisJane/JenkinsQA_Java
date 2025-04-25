package school.redrover.page.user;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

import java.util.List;

public class UsersPage extends BasePage {

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage clickCreateUserButton() {
        getWait10().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[@href='addUser']"))).click();

        return new CreateUserPage(getDriver());
    }

    public List<WebElement> getUsersList() {

        return getWait10().until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath("//table[@id='people']/tbody/tr")));
    }

    public String getUserIdTdText() {

        return getDriver().findElement(By.xpath("//tbody/tr[2]/td[2]")).getText();
    }

    public String getNameTdText() {

        return getDriver().findElement(By.xpath("//tbody/tr[2]/td[3]")).getText();
    }
}
