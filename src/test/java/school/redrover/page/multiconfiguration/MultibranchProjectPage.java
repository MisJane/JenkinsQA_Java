package school.redrover.page.multiconfiguration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class MultibranchProjectPage extends BasePage {

    public MultibranchProjectPage(WebDriver driver) { super(driver); }

    public MultibranchConfigurationPage goToConfigurationPage() {
        getDriver().findElement(By.xpath("//a[@href='./configure']")).click();

        return new MultibranchConfigurationPage(getDriver());
    }

}
