package school.redrover.page.managejenkins;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.page.user.UsersPage;

public class ManageJenkinsPage extends BasePage {

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public UsersPage clickUsers(){
        getWait10().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[@href='securityRealm/']"))).click();

        return new UsersPage(getDriver());
    }

    public ManageAppearansePage clickAppearanse() {
        getDriver().findElement(By.xpath("//a[@href='appearance']")).click();

        return new ManageAppearansePage(getDriver());
    }

}
