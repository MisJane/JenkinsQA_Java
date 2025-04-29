package school.redrover.page.freestyle;

import org.checkerframework.common.value.qual.IntRange;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.common.BasePage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FreestyleConfigurationPage extends BasePage {

    public FreestyleConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public FreestyleConfigurationPage addDescription(String text) {
        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(text);

        return this;
    }

    public FreestyleProjectPage clickSaveButton() {
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        return new FreestyleProjectPage(getDriver());
    }


    public FreestyleConfigurationPage scrollToGeneralItem() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.xpath("//*[@id='general']")));

        return this;
    }

    public FreestyleConfigurationPage scrollToSourceCodeManagementItem() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.xpath("//*[@id='source-code-management']")));

        return this;
    }

    public FreestyleConfigurationPage scrollToTriggersItem() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.xpath("//*[@id='triggers']")));

        return this;
    }

    public FreestyleConfigurationPage scrollToBuildEnvironmentItem() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.xpath("//*[@id='environment']")));

        return this;
    }

    public FreestyleConfigurationPage scrollToBuildStepsItem() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.xpath("//*[@id='build-steps']")));

        return this;
    }

    public FreestyleConfigurationPage clickPostBuildActionsItem() {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getDriver().findElement(By.xpath("//*[@id='post-build-actions']")));


        return this;
    }

    public FreestyleConfigurationPage checkTriggerBuildsRemotelyCheckbox() {
        getDriver().findElement(By.xpath("//label[contains(text(), 'Trigger builds remotely (e.g., from scripts)')]")).click();

        return this;
    }

    public FreestyleConfigurationPage checkBuildAfterProjectsCheckbox() {
        getDriver().findElement(By.xpath("//label[contains(text(), 'Build after other projects are built')]")).click();

        return this;
    }

    public FreestyleConfigurationPage checkBuildPeriodicallyCheckbox() {
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//label[contains(text(), 'Build periodically')]")));
        getDriver().findElement(By.xpath("//label[contains(text(), 'Build periodically')]")).click();

        return this;
    }

    public FreestyleConfigurationPage checkGithubHookCheckbox() {
        getDriver().findElement(By.xpath("//label[contains(text(), 'GitHub hook trigger for GITScm polling')]")).click();

        return this;
    }

    public FreestyleConfigurationPage checkPollCSMCheckbox() {
        getDriver().findElement(By.xpath("//label[contains(text(), 'Poll SCM')]")).click();

        return this;
    }

    public FreestyleConfigurationPage sendScheduleText(String text) {
        getDriver().findElement(By.xpath("//div[@class='setting-main']/textarea[@name='_.spec']")).clear();
        getDriver().findElement(By.xpath("//div[@class='setting-main']/textarea[@name='_.spec']")).sendKeys(text);

        return this;
    }

    public String sendScheduleActualText() {

        return getDriver().findElement(By.xpath("//div[@class='setting-main']/textarea[@name='_.spec']")).getText();
    }

    public FreestyleConfigurationPage clickEnableDisableToggle() {
        getDriver().findElement(By.cssSelector("label[for='enable-disable-project']")).click();

        return this;
    }

    public String getToggleStatus() {
        List<WebElement> statusList = getDriver().findElements(By.cssSelector("span[class^='jenkins-toggle-switch__label']"));
        for (WebElement e : statusList) {
            if (e.isDisplayed()) {
                return e.getText();
            }
        }
        return "";
    }

    public String getProjectStatus() {
        return getDriver().findElement(By.className("jenkins-toggle-switch__label__checked-title")).getText();
    }

    public FreestyleConfigurationPage waitUntilTextConfigureToBePresentInH1() {
        getWait10().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "Configure"));

        return this;
    }

    public String getSectionNameTriggers() {
        return getDriver().findElement(By.id("triggers")).getText();
    }

    public String getSubtitleTextTriggers() {
        return getDriver()
                .findElement(By.xpath("//div[contains(text(), 'Set up')]"))
                .getText();
    }

    public boolean isTriggerBuildsRemotelyCheckboxDisplayed() {
        return getDriver().findElement(By.id("cb13")).isDisplayed();
    }

    public boolean isTriggerBuildsRemotelyCheckboxEnabled() {
        return getDriver().findElement(By.id("cb13")).isEnabled();
    }

    public String getTriggerBuildsRemotelyLabelText() {
        return getDriver().findElement(By.xpath("//label[contains(text(), 'Trigger builds remotely')]")).getText();
    }

    public int countHelperIconsTriggersSection() {
        return getDriver().findElements(By.xpath("//div[@class='jenkins-section__title' and @id='triggers']/following-sibling::div//a[@class='jenkins-help-button']")).size();
    }

    public String getTriggerBuildsRemotelyHelpIconTitle() {
        return getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[3]/div[3]/div[1]/div/a"))
                .getDomAttribute("title");
    }

    public boolean isBuildAfterProjectsCheckboxDisplayed() {
        return getDriver().findElement(By.id("cb14")).isDisplayed();
    }

    public boolean isBuildAfterProjectsCheckboxEnabled() {
        return getDriver().findElement(By.id("cb14")).isEnabled();
    }

    public String getBuildAfterProjectsLabelText() {
        return getDriver().findElement(By.xpath("//label[contains(text(), 'Build after other projects are built')]")).getText();
    }

    public String getBuildAfterProjectsHelpIconTitle() {
        return getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/section[3]/div[4]/div[1]/div/a"))
                .getDomAttribute("title");
    }

    public boolean isBuildPeriodicallyCheckboxDisplayed() {
        return getDriver().findElement(By.id("cb15")).isDisplayed();
    }

    public boolean isBuildPeriodicallyCheckboxEnabled() {
        return getDriver().findElement(By.id("cb15")).isEnabled();
    }

    public String getBuildPeriodicallyLabelText() {
        return getDriver().findElement(By.xpath("//label[contains(text(), 'Build periodically')]")).getText();
    }

    public String getBuildPeriodicallyHelpIconTitle() {
        return getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/section[3]/div[5]/div[1]/div/a"))
                .getDomAttribute("title");
    }

    public boolean isGithubHookTriggerCheckboxDisplayed() {
        return getDriver().findElement(By.id("cb16")).isDisplayed();
    }

    public boolean isGithubHookTriggerCheckboxEnabled() {
        return getDriver().findElement(By.id("cb16")).isEnabled();
    }

    public String getGithubHookTriggerLabelText() {
        return getDriver().findElement(By.xpath("//label[contains(text(), 'GitHub hook trigger for GITScm polling')]")).getText();
    }

    public String getGithubHookTriggerHelpIconTitle() {
        return getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/section[3]/div[6]/div[1]/div/a"))
                .getDomAttribute("title");
    }

    public FreestyleConfigurationPage addBuildSteps(@IntRange(from = 1, to = 7) int itemNumber){
        final String locator = "button.jenkins-dropdown__item";

        WebElement buttonBuildSteps = getDriver().findElement(By.cssSelector("button[suffix='builder']"));

            for (int i = 0; i < 5; i++) {
                try {
                    new Actions(getDriver())
                            .scrollToElement(
                                    getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='post-build-actions']"))))
                            .perform();
                    buttonBuildSteps.click();
                    getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));

                    break;
                } catch (ElementClickInterceptedException e) {
                    try {
                        getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/button")).click();
                        buttonBuildSteps.click();
                        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));

                        break;
                    } catch (ElementClickInterceptedException y) {
                        System.err.println("Элемент перекрыт, пробуем снова... " + y.getMessage());
                    }
                }
            }
        getDriver().findElements(By.cssSelector(locator)).get(--itemNumber).click();

        return this;
    }

    public List<String> getChunkHeaderList() {
        final int MAX_ATTEMPTS = 3;

        List<String> previousList = Collections.emptyList();
        List<String> currentList;

        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            currentList = getDriver().findElements(By.cssSelector(".repeated-chunk__header")).stream()
                    .map(WebElement::getText)
                    .map(text -> text.replace("?", ""))
                    .filter(text -> !text.trim().isEmpty())
                    .toList();

            if (currentList.equals(previousList)) return currentList;

            previousList = new ArrayList<>(currentList);

            if (i < MAX_ATTEMPTS - 1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Список продолжает обновляться", e);
                }
            }
        }

        return previousList;
    }

    public FreestyleConfigurationPage clickTriggerBuildsRemotely() {
        getDriver().findElement(By.xpath("//label[contains(text(), 'Trigger builds remotely')]")).click();

        return this;
    }


    public FreestyleConfigurationPage clickBuildAfterProjects() {
        getDriver().findElement(By.xpath("//label[contains(text(), 'Build after other projects are built')]")).click();

        return this;
    }

    public FreestyleConfigurationPage setProjectToWatch(String projectName) {
        getDriver().findElement(By.xpath("//input[@name='_.upstreamProjects']")).clear();
        getDriver().findElement(By.xpath("//input[@name='_.upstreamProjects']")).sendKeys(projectName);
        getWait5().until(ExpectedConditions.attributeToBe(By
                .xpath("//input[@name='_.upstreamProjects']"), "aria-expanded", "true"));
        getDriver().findElement(By.xpath("//input[@name='_.upstreamProjects']")).sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

        return this;
    }

    public FreestyleConfigurationPage clickAllReverseBuildTriggerLabels() {
        List<WebElement> labels = getDriver().findElements(
                By.xpath("//input[@name='ReverseBuildTrigger.threshold']/following-sibling::label")
        );
        for (WebElement label : labels) {
            label.click();
        }

        return this;
    }

    public FreestyleConfigurationPage clickFreestyleText() {
        getDriver().findElement(By.xpath("//a[text()='Freestyle']")).click();


        return this;
    }

    public FreestyleConfigurationPage enterAuthToken(String token) {
        getDriver().findElement(By.xpath("//input[@name='authToken']")).sendKeys(token);

        return this;
    }

    public String getAuthenticationTokenLabelText() {
        return getDriver().findElement(By.xpath("//div[text()='Authentication Token']")).getText();
    }

    public String getAuthTokenDomValue() {
        return getDriver().findElement(By.xpath("//input[@name='authToken']")).getDomAttribute("value");
    }

    public String getTriggerInfoText() {
        return getDriver()
                .findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[3]/div[3]/div[4]/div/div[2]"))
                .getText()
                .trim();
    }


    public String getCurrentProjectName() {
        return getDriver().findElement(By.xpath("//input[@name='_.upstreamProjects']"))
                .getDomAttribute("value");
    }

    public boolean isLastRadioButtonSelected() {
        List<WebElement> radios = getDriver().findElements(By.name("ReverseBuildTrigger.threshold"));
        return radios.get(radios.size() - 1).getDomAttribute("checked").equals("true");
    }

    public FreestyleConfigurationPage addPostBuildActions(@IntRange(from = 1, to = 11) int itemNumber){
        final String locator = ".jenkins-dropdown__disabled, button.jenkins-dropdown__item";

        Actions actions = new Actions(getDriver());

        for (int i = 0; i < 5; i++) {
            try {
                actions.sendKeys(Keys.END).perform();
                getDriver().findElement(By.cssSelector("button[suffix='publisher']")).click();
                getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));

                break;
            }catch (Exception e){
                System.err.println("Ошибка, пробуем снова..." + e.getMessage());
            }
        }

        getDriver().findElements(By.cssSelector(locator)).get(--itemNumber).click();

        return this;
    }

    public FreestyleConfigurationPage clickToReverseBuildTriggerAndSetUpStreamProject(String jobName) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='triggers']/parent::section")));

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[name = 'jenkins-triggers-ReverseBuildTrigger']"))));


        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();",
                getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[name = 'jenkins-triggers-ReverseBuildTrigger']"))));

        getDriver().findElement(By.name("_.upstreamProjects")).sendKeys(jobName);

        return this;
    }

    public FreestyleConfigurationPage clickBuildNow() {
        getDriver().findElement(By.xpath("//a[@data-build-success='Build scheduled']")).click();

        return this;
    }

    public FreestyleConfigurationPage waitJobStarted(String jobName) {
        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.id("no-builds")));
        getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(" #jenkins-build-history a[title='Success']")));

        return this;
    }

    public List<String> getBuildList() {
        return getDriver().findElements(By.cssSelector("div[page-entry-id]")).stream()
                .map(WebElement::getText).toList();
    }

    public String getBuildStatusText() {
        return getDriver().findElement(By.id("jenkins-build-history")).getText();
    }

    public FreestyleConfigurationPage checkGitHubProjectCheckbox() {
        getDriver().findElement(By.xpath("//section[@nameref='rowSetStart28']//div[@nameref='rowSetStart25']//span/label")).click();

        return this;
    }

    public FreestyleConfigurationPage sentGitHubProjectURL(String projectURL) {
        getDriver().findElement(By.xpath("//div/input[@name='_.projectUrlStr']")).sendKeys(projectURL);

        return this;
    }

    public FreestyleConfigurationPage clickThrottleBuilds() {
        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/div[12]/div[1]/div/span/label")).click();

        return this;
    }

    public FreestyleConfigurationPage selectTimePeriod(String period) {
        WebElement timePeriod = getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.name("_.durationName"))));
        timePeriod.click();
        new Select(timePeriod).selectByVisibleText(period);

        return this;
    }

    public String getDescriptionText() {
        return getWait10().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.name("description")))).getText();
    }

    public boolean getSelectThrottleBuilds() {
        return getDriver().findElement(By.name("_.throttle")).isSelected();
    }

    public String getTimePeriod() {
        return new Select(getDriver().findElement(By.name("_.durationName"))).getFirstSelectedOption().getText();
    }
}
