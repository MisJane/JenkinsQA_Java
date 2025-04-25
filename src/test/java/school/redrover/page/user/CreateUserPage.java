package school.redrover.page.user;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;

public class CreateUserPage extends BasePage {

    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage sendUserName(String userName){
        getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("form-content")));
        getDriver().findElement(By.id("username")).sendKeys(userName);

        return this;
    }

    public CreateUserPage sendPassword(String password){
        getDriver().findElement(By.name("password1")).sendKeys(password);

        return this;
    }

    public CreateUserPage sendConfirmPassword(String password){
        getDriver().findElement(By.name("password2")).sendKeys(password);

        return this;
    }

    public CreateUserPage sendFullName(String fullName){
        getDriver().findElement(By.name("fullname")).sendKeys(fullName);

        return this;
    }

    public CreateUserPage sendEmailAddress(String email){
        getDriver().findElement(By.name("email")).sendKeys(email);

        return this;
    }

    public UsersPage clickCreateUserButton(){
        getDriver().findElement(By.name("Submit")).click();

        return new UsersPage(getDriver());
    }
}
