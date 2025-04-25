package school.redrover;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Ignore;
import school.redrover.common.BaseTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;


public class NewItem10Test extends BaseTest {
    //US_01.001 | New Item > Create a new item #693

    @Ignore
    @Test
    public void VerifyItemList() {
        WebDriver driver = getDriver();
        List<String> expectedItemTypes = List.of("Freestyle project", "Pipeline", "Multi-configuration project", "Folder", "Multibranch Pipeline", "Organization Folder"); // excluding  until ambiguity is resolved

        //US_01.001.01 - New Item Page Accessibility

        //The page should have an input field "Enter an item name".
        driver.findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        String inputFieldTitle = driver.findElement(By.className("jenkins-form-label")).getText();
        Assert.assertEquals(inputFieldTitle, "Enter an item name");

        //A list of available item types (Freestyle Project, Pipeline, Multi-configuration Project, Folder,
        // Multibranch Pipeline, Organization Folder) should be displayed.
        List<String> itemTypes = driver.findElements(By.xpath("//span[@class='label']")).stream()
                .map(element -> element.getText()).collect(Collectors.toList());
        Assert.assertEquals(itemTypes, expectedItemTypes);

        //US_01.001.02 - Entering an Item Name

        //US_01.001.03 - Creating the Item

        //US_01.001.04 - Error Handling

        //US_01.001.06 - Special characters (except underscores) should not be allowed in the input field
    }
}
