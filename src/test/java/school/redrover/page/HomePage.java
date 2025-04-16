package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.common.BasePage;

public class HomePage extends BasePage {


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickAddDescriptionButton() {
        getDriver().findElement(By.id("description-link")).click();

        return this;
    }

    public boolean isDescriptionFieldDisplayed() {
        return getDriver().findElement(By.name("description")).isDisplayed();
    }

    public HomePage sendDescription(String text) {
        getDriver().findElement(By.cssSelector("#description > form > div.jenkins-form-item.tr > div.setting-main.help-sibling > textarea"))
                .sendKeys(text);

        return this;
    }

    public HomePage clickSaveDescriptionButton() {
        getDriver().findElement(By.cssSelector("#description > form > div:nth-child(3) > button"))
                .click();

        return this;
    }

    public String getDescriptionText() {
        return getDriver().findElement(By.cssSelector("#description > div:nth-child(1)")).getText();
    }

    public NewItemPage createJob() {
        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        return new NewItemPage(getDriver());
    }
}
