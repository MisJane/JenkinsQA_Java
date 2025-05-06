package school.redrover.page.newitem;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import school.redrover.common.BasePage;
import school.redrover.common.TestUtils;
import school.redrover.page.error.ErrorPage;
import school.redrover.page.folder.FolderConfigurationPage;
import school.redrover.page.freestyle.FreestyleConfigurationPage;
import school.redrover.page.multibranch.MultibranchConfigurationPage;
import school.redrover.page.multiconfiguration.MultiConfigurationConfigurePage;
import school.redrover.page.organizationfolder.OrganizationFolderConfigurePage;
import school.redrover.page.pipeline.PipelineConfigurationPage;

import java.time.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewItemPage extends BasePage {

    @FindBy(id = "name")
    private WebElement itemName;

    @FindBy(css = ".hudson_model_FreeStyleProject")
    private WebElement freeStyleProject;

    @FindBy(id = "ok-button")
    private WebElement buttonOk;

    @FindBy(xpath = "//span[text()='Folder']")
    private WebElement folder;

    @FindBy(id = "ok-button")
    private WebElement okButton;



    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public boolean isNewItemPageOpened() {
        return getWait5().until(ExpectedConditions.visibilityOf(
                getDriver().findElement(By.id("add-item-panel")))).isDisplayed();
    }

    public NewItemPage sendItemName(String name) {
        itemName.sendKeys(name);

        return this;
    }

    public String getInputValue() {
        return getDriver().findElement(By.id("name")).getShadowRoot().findElement(By.cssSelector("div")).getText();
    }

    public boolean isOkButtonEnabled() {
        return getDriver().findElement(By.id("ok-button")).isEnabled();
    }

    public FreestyleConfigurationPage clickOkButton() {
        buttonOk.click();

        return new FreestyleConfigurationPage(getDriver());
    }

    public ErrorPage clickOkButtonWithError() {
        getDriver().findElement(By.id("ok-button")).click();

        return new ErrorPage(getDriver());
    }

    public String getErrorMessageText() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText();
    }

    public String getEmptyNameMessage() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-required"))).getText();
    }

    public PipelineConfigurationPage selectPipelineAndClickOk() {
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        return new PipelineConfigurationPage(getDriver());
    }

    public FolderConfigurationPage selectFolderAndClickOk() {
        folder.click();
        buttonOk.click();

        return new FolderConfigurationPage(getDriver());
    }

    public String getNewItemPageHeaderText() {
        return getDriver().findElement(By.xpath("//h1[text()='New Item']")).getText();
    }

    public String getNewItemPageURL() {
        return getDriver().getCurrentUrl();
    }

    public NewItemPage selectFreestyle() {
        freeStyleProject.click();

        return this;
    }

    public FreestyleConfigurationPage selectFreestyleAndClickOk() {
        selectFreestyle();
        okButton.click();

        return new FreestyleConfigurationPage(getDriver());
    }

    public List<String> getItemTypesTextList() {
        List<String> itemTypesTextList = new ArrayList<>();
        List<WebElement> webElementList = getDriver().findElements(By.xpath("//li[@role='radio']//span"));
        for (WebElement webElement : webElementList) {
            itemTypesTextList.add(webElement.getText());
        }
        return itemTypesTextList;
    }

    public String getItemNameInvalidMessage() {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText();
    }

    public String getCopyFromFieldText() {

        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("add-item-copy"))).getText();
    }

    public NewItemPage selectMultiConfiguration() {
        getDriver().findElement(By.cssSelector(".hudson_matrix_MatrixProject")).click();

        return this;
    }

    public MultiConfigurationConfigurePage selectMultiConfigurationAndClickOk() {
        getDriver().findElement(By.cssSelector(".hudson_matrix_MatrixProject")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        return new MultiConfigurationConfigurePage(getDriver());
    }

    public MultibranchConfigurationPage selectMultibranchAndClickOk() {
        getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        return new MultibranchConfigurationPage(getDriver());
    }

    public FolderConfigurationPage selectFolderAndClickOkWithJS() {
        TestUtils.scrollAndClickWithJS(getDriver(),
                getDriver().findElement(By.xpath("//span[text()='Folder']")));
        getDriver().findElement(By.id("ok-button")).click();

        return new FolderConfigurationPage(getDriver());
    }

    public MultiConfigurationConfigurePage redirectToMultiConfigurationConfigurePage() {
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.id("ok-button")));
        getWait5().until(ExpectedConditions.urlContains("/job"));

        return new MultiConfigurationConfigurePage(getDriver());
    }

    public ErrorPage redirectToErrorPage() {
        TestUtils.scrollAndClickWithJS(getDriver(), getDriver().findElement(By.id("ok-button")));
        getWait5().until(ExpectedConditions.urlContains("/createItem"));

        return new ErrorPage(getDriver());
    }

    public String getItemTypeText(String itemType) {
        return getDriver().findElement(By.xpath("//span[text()='" + itemType + "']")).getText();
    }

    public List<String> getAllItemsTypesLabels() {
        return getDriver().findElements(By.className("label"))
                .stream()
                .map(itemType -> itemType.getText()).collect(Collectors.toList());
    }

    public NewItemPage enterProjectNameAndSelect(String nameProject, String projectTypeText) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-input"))).sendKeys(nameProject);
        String xpath = String.format("//span[text()='%s']", projectTypeText);
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).click();
        getWait5().until(ExpectedConditions.visibilityOf(okButton)).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();

        return new NewItemPage(getDriver());
    }

    public FreestyleConfigurationPage selectFreestyleClickOkAndWaitCreateItemFormIsClose() {
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(okButton)).click();
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.id("createItem")));

        return new FreestyleConfigurationPage(getDriver());
    }

    public OrganizationFolderConfigurePage selectOrganizationFolderAndClickOk() {
        TestUtils.scrollAndClickWithJS(getDriver(),
                getDriver().findElement(By.xpath("//span[text()='Organization Folder']")));
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        return new OrganizationFolderConfigurePage(getDriver());
    }

    public List<String> getJobsDescriptions() {
        return getDriver().findElements(By.className("desc"))
                .stream()
                .map(jobDescription -> jobDescription.getText()).collect(Collectors.toList());
    }

    public List<String> getAllProjectTypeTitles() {
        return Arrays.asList(
                        "//span[@class][text()='Freestyle project']",
                        "//span[@class][text()='Pipeline']",
                        "//span[@class][text()='Multi-configuration project']",
                        "//span[@class][text()='Folder']",
                        "//span[@class][text()='Multibranch Pipeline']",
                        "//span[@class][text()='Organization Folder']"
                ).stream()
                .map(xpath -> getDriver().findElement(By.xpath(xpath)).getText())
                .toList();
    }

    public String getCopyFromText() {
        WebElement copyFromTextBlock = getDriver().findElement(By.xpath("//div[@class='add-item-copy']"));
        return copyFromTextBlock.getText().trim();
    }

    public NewItemPage sendTextCopyForm(String text) {
        WebElement actualTextCopyForm = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("from")));
        actualTextCopyForm.sendKeys(text);

        return new NewItemPage(getDriver());
    }

    public String getAutocompleteSuggestionText() {
        return getWait5()
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("tippy-7")))
                .getText();
    }

    public NewItemPage clickOnJobItem(String itemLabel) {
        WebElement itemType = getDriver().findElement(By.xpath(String.format("//span[text()='%s']", itemLabel)));
        TestUtils.scrollAndClickWithJS(getDriver(), itemType);

        return this;
    }

    public boolean isListItemHighlighted(String itemLabel) {
        WebElement itemType = getDriver().findElement(By.xpath(String.format("//span[text()='%s']", itemLabel)));
        WebElement listItem = itemType.findElement(By.xpath("./ancestor::li"));

        return listItem.getDomAttribute("class").contains("active");
    }

    public String getErrorMessageOnEmptyField() {
        return getWait5().until
                (ExpectedConditions.visibilityOfElementLocated((By.cssSelector("#itemname-required.input-validation-message"))))
                .getText();
    }

    public boolean isUnsafeCharacterMessageDisplayed() {

        return getDriver().findElement(By.id("itemname-invalid")).isDisplayed();
    }

    public boolean isCopyFromOptionInputDisplayed() {
        List<WebElement> copyFromOptionInputs = getDriver().findElements(By.id("from"));

        return !copyFromOptionInputs.isEmpty() && copyFromOptionInputs.get(0).isDisplayed();
    }

    public NewItemPage enterValueToCopyFromInput(String randomAlphaNumericValue) {
        WebElement copyFromInput = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.id("from")));

        TestUtils.scrollAndClickWithJS(getDriver(), copyFromInput);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].focus();", copyFromInput);
        copyFromInput.sendKeys(randomAlphaNumericValue);

        getWait10()
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jenkins-dropdown.jenkins-dropdown--compact")))
                .click();

        return this;
    }

    public String getDropdownItemText() {
        return getWait10()
                          .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jenkins-dropdown.jenkins-dropdown--compact")))
                          .getText();
    }

    public NewItemPage selectItemByName(String projectName) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='%s']".formatted(projectName)))).click();

        return this;
    }

    public NewItemPage selectPipeline() {
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        return this;
    }

    public String getEmptyNameMessageColor() {
        return getDriver().findElement(By.id("itemname-required")).getCssValue("color");
    }

}

