package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.*;

public class JenkinsThemeTest extends BaseTest {

    @Test
    public void testDarkTheme() throws InterruptedException {
        HomePage homePage = new HomePage(getDriver());
        String popUpSaveButtonText = homePage
                    .clickManageJenkinsOnLeftSidePanel()
                    .clickAppearanse()
                    .clickDarkSistemTheme()
                    .clickDefaultTheme()
                    .clickDarkTheme()
                    .clickSaveButton()
                    .getPopUpSaveButtonText();

        Assert.assertEquals(popUpSaveButtonText, "Saved");
    }
}
