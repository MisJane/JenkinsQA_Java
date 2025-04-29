package school.redrover.page.account;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class AccountSettingsPage extends BasePage {

    public AccountSettingsPage(WebDriver driver)  { super(driver); }

    public AccountPage goToAccountPage() {
        getDriver().findElement(By.xpath("//a[@href='/user/admin/account']")).click();

        return new AccountPage(getDriver());
    }

    public String getDescription() {
        return getDriver().findElement(By.id("description")).getText();
    }

    public String getUserButton() {
        return getDriver().findElement(By.xpath("//*[@id='page-header']/div[3]/a[1]/span")).getText();
    }

    public String getFullUserName() {
        return getDriver().findElement(By.xpath("//*[@id='main-panel']/div[1]/div[1]/h1")).getText();
    }
}
