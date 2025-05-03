package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.common.BasePage;
import school.redrover.common.TestUtils;
import school.redrover.page.account.AccountSettingsPage;
import school.redrover.page.buildhistory.BuildHistoryPage;
import school.redrover.page.freestyle.FreestyleConfigurationPage;
import school.redrover.page.freestyle.FreestyleProjectPage;
import school.redrover.page.managejenkins.ManageJenkinsPage;
import school.redrover.page.multibranch.MultibranchProjectPage;
import school.redrover.page.newitem.NewItemPage;
import school.redrover.page.organizationfolder.OrganizationFolderPage;
import school.redrover.page.pipeline.PipelineConfigurationPage;
import school.redrover.page.signIn.SignInPage;
import school.redrover.page.view.NewViewPage;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(css = "#description > div:nth-child(1)")
    private WebElement descriptionText;

    @FindBy(xpath = "//span[text()='log out']")
    private WebElement logOutButton;

    @FindBy(xpath ="//a[@data-task-success='Done.']")
    private WebElement newItemButtonOnLeftSidePanel;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public HomePage clickAddDescriptionButton() {
        getDriver().findElement(By.id("description-link")).click();

        return this;
    }

    public boolean isDescriptionFieldDisplayed() {
        return getDriver().findElement(By.name("description")).isDisplayed();
    }

    public boolean isJobListEmpty() {
        return getDriver().findElement(By.id("main-panel")).getText().contains("Welcome to Jenkins!");
    }

    public boolean isProjectExists(String projectName) {
        return getProjectNameList().contains(projectName);
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
        return descriptionText.getText();
    }

    public String getWelcomeMessage() {

        return getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("//h1"))))
                .getText();
    }

    public String getNameProject() {

        return getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(By
                        .xpath("//*[@id='job_My name']/td[3]/a"))))
                .getText();
    }

    public NewItemPage createJob() {
        getDriver().findElement(By.xpath("//span[text()='Create a job']")).click();

        return new NewItemPage(getDriver());
    }

    public NewItemPage clickNewItemOnLeftSidePanel() {
        newItemButtonOnLeftSidePanel.click();
        return new NewItemPage(getDriver());
    }

    public FreestyleProjectPage clickOnJobInListOfItemsOnHP(String nameItem) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td/a[@href='job/" + nameItem.replace(" ", "%20") + "/']"))).click();

        return new FreestyleProjectPage(getDriver());
    }

    public <T> T clickOnJobInListOfItems(String nameItem, T resultPage) {
        getWait10()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + nameItem + "']")))
                .click();

        return resultPage;
    }

    public FreestyleProjectPage clickOnJobInListOfItems(String nameItem) {
        getWait10()
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + nameItem + "']")))
                .click();

        return new FreestyleProjectPage(getDriver());
    }

    public String getNameFreestyleProjectText() {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='jenkins-table__link model-link inside']"))).getText();
    }

    public AccountSettingsPage goToAccountSettingsPage() {
        getDriver().findElement(By.xpath("//div[@class='login page-header__hyperlinks']/a[@class='model-link']")).click();

        return new AccountSettingsPage(getDriver());
    }

    public ManageJenkinsPage clickManageJenkinsOnLeftSidePanel() {
        getWait10().until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[@href='/manage']"))).click();

        return new ManageJenkinsPage(getDriver());
    }

    public OrganizationFolderPage clickOnOrganizationFolderInListOfItems(String nameItem) {
        getWait5().until(ExpectedConditions.visibilityOf(getDriver()
                .findElement(By.xpath("//span[text()='" + nameItem + "']")))).click();

        return new OrganizationFolderPage(getDriver());
    }

    public MultibranchProjectPage clickOnMultibranchJobInListOfItems(String nameItem) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + nameItem + "']"))).click();

        return new MultibranchProjectPage(getDriver());
    }

    public PipelineConfigurationPage createNewPipeline(String projectName) {

        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::span[@class='task-link-wrapper ']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        return new PipelineConfigurationPage(getDriver());
    }

    public BuildHistoryPage clickBuildHistoryTab() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();

        return new BuildHistoryPage(getDriver());
    }

    public List<String> getProjectNameList() {
        if (isJobListEmpty()) {
            return List.of();
        }

        return getDriver().findElements(By.cssSelector(".jenkins-table__link > span:nth-child(1)")).stream()
                .map(WebElement::getText).toList();
    }

    public NewItemPage clickNewItem() {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();

        return new NewItemPage(getDriver());
    }

    public NewViewPage clickNewView() {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[aria-label='New View']"))).click();

        return new NewViewPage(getDriver());
    }

    public String getNameOfView(String viewName) {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='/view/" + viewName + "/']"))).getText();
    }

    public boolean isJobDisplayed(String jobName) {
            return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='job_" + jobName + "']/td[3]/a/span"))).isDisplayed();
    }

    public FreestyleConfigurationPage clickJobLink(String jobName) {
        getDriver().findElement(By.linkText(jobName)).click();

        return new FreestyleConfigurationPage(getDriver());
    }

    public FreestyleProjectPage clickFreestyleProjectOnDashboard(String projectName) {
        getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//a[@class='jenkins-table__link model-link inside']/span[text()='%s']".formatted(projectName)))))
                .click();

        return new FreestyleProjectPage(getDriver());
    }

    public NewItemPage clickOnNewItemLinkWithChevron(String projectName) {
        WebElement jobTableLink = getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.cssSelector(String.format("a[href='job/%s/'].jenkins-table__link", projectName)))));

        new Actions(getDriver()).moveToElement(jobTableLink).perform();
        TestUtils.moveAndClickWithJS(getDriver(), getDriver().findElement(By.cssSelector(String.format("button[data-href$='job/%s/']", projectName))));
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.cssSelector(".jenkins-dropdown a[href$='/newJob'")));

        return new NewItemPage(getDriver());
    }

    public String getProjectName() {
        return getWait5()
                         .until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.cssSelector("a[href*='job'].jenkins-table__link"))))
                         .getText();
    }

    public SignInPage clickLogOutButton(){
        logOutButton.click();
        return new SignInPage(getDriver());
    }

    public List<String> getColumnNames() {

        return getDriver().findElements(By.xpath("//th/a")).stream()
                .map(WebElement::getText).toList();
    }

    public String getLogOutButtonText() {
        return logOutButton.getText();
    }

    public boolean isSvgIconDifferentBetweenProjects() {
        List<WebElement> svgIcons = getDriver().findElements(By.cssSelector(".jenkins-table__icon:not(.healthReport) svg"));

        return (svgIcons.get(0).getDomAttribute("title")).equals(svgIcons.get(1).getDomAttribute("title"));
    }
}