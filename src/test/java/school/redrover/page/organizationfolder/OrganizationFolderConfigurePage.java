package school.redrover.page.organizationfolder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class OrganizationFolderConfigurePage extends BasePage {
    public OrganizationFolderConfigurePage(WebDriver driver) { super(driver); }

    public OrganizationFolderPage clickSave() {
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        return new OrganizationFolderPage(getDriver());
    }
}
