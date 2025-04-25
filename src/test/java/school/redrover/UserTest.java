package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.component.HeaderComponent;
import school.redrover.page.account.AccountSettingsPage;
import school.redrover.page.HomePage;

import static org.testng.Assert.assertEquals;

public class UserTest extends BaseTest {

    @Test
    public void testEditDescriptionField() {
        final String descriptionText = "User Description";

        AccountSettingsPage page = new HomePage(getDriver())
                .goToAccountSettingsPage()
                .goToAccountPage()
                .sendDescription(descriptionText)
                .clickSaveDescriptionButton();

        String actualDescription = page.getDescription();

        Assert.assertEquals(actualDescription, descriptionText);

        page
                .goToAccountPage()
                .clearDescription();
    }

    @Test
    public void testSearchUser() {

        String currentAdminIDText = new HeaderComponent(getDriver())
                .clickSearchButton()
                .sendSearchText("admin")
                .clickSearch()
                .getAdminIDText();

        assertEquals(currentAdminIDText, "admin");
    }
}
