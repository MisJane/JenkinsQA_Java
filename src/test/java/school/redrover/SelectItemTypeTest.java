package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.page.HomePage;

import java.util.Arrays;
import java.util.List;

public class SelectItemTypeTest extends BaseTest {
    @Test
    public void testItemsTypeList() {
        final List<String> items = Arrays.asList
                ("Freestyle project", "Pipeline", "Multi-configuration project", "Folder", "Multibranch Pipeline", "Organization Folder");

        HomePage homePage = new HomePage(getDriver());
        List<String> listOfJobs = homePage.createJob().getItemTypesTextList();

        Assert.assertNotEquals(listOfJobs.size(), 0);
        Assert.assertTrue(items.containsAll(listOfJobs));
    }

    @Test
    public void testItemsTypeDescriptionExist() {
        final List<String> itemsDisc = Arrays.asList
                ("Classic, general-purpose job type that checks out from up to one SCM, executes build steps serially, followed by post-build steps like archiving artifacts and sending email notifications.",
                "Orchestrates long-running activities that can span multiple build agents. Suitable for building pipelines (formerly known as workflows) and/or organizing complex activities that do not easily fit in free-style job type.",
                "Suitable for projects that need a large number of different configurations, such as testing on multiple environments, platform-specific builds, etc.",
                "Creates a container that stores nested items in it. Useful for grouping things together. Unlike view, which is just a filter, a folder creates a separate namespace, so you can have multiple things of the same name as long as they are in different folders.",
                "Creates a set of Pipeline projects according to detected branches in one SCM repository.",
                "Creates a set of multibranch project subfolders by scanning for repositories.");

        HomePage homePage = new HomePage(getDriver());
        List<String> listOfJobsDisc = homePage.createJob().getJobsDescriptions();

        Assert.assertTrue(listOfJobsDisc.containsAll(itemsDisc));
    }
}
