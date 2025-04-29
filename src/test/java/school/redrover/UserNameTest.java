package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.common.BaseTest;
import school.redrover.page.account.AccountSettingsPage;

public class UserNameTest extends BaseTest {

    @Test
    public void testUserName() {

        AccountSettingsPage currentPage = new HomePage(getDriver()).goToAccountSettingsPage();
        Assert.assertEquals(currentPage.getFullUserName(), currentPage.getUserButton());
    }
}