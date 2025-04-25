package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;
import school.redrover.page.user.UsersPage;

import java.util.List;

public class User1Test extends BaseTest {

    @Test
    public void testCreateNewUser() {
        final String userName = "testUser";
        final String password = "123Test";
        final String fullName = "Test User";
        final String email = "testuser@test.com";

        List<WebElement> users = new HomePage(getDriver())
                .clickManageJenkinsOnLeftSidePanel()
                .clickUsers()
                .clickCreateUserButton()
                .sendUserName(userName)
                .sendPassword(password)
                .sendConfirmPassword(password)
                .sendFullName(fullName)
                .sendEmailAddress(email)
                .clickCreateUserButton()
                .getUsersList();

        Assert.assertEquals(users.size(), 2);

        UsersPage usersPage = new UsersPage(getDriver());
        String userIdTdText = usersPage.getUserIdTdText();
        String nameTdText = usersPage.getNameTdText();

        Assert.assertEquals(userIdTdText, userName);
        Assert.assertEquals(nameTdText, fullName);
    }
}
