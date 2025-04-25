package school.redrover.page.search;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.page.user.UserAdminPage;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public SearchPage sendSearchText(String text) {
        getWait10().until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("command-bar")))
                .sendKeys(text);

        return new SearchPage(getDriver());
    }

    public UserAdminPage clickSearch() {
        getWait10().until(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='search-results']/a[@href='/user/admin']")))
                .click();

        return new UserAdminPage(getDriver());
    }
}
