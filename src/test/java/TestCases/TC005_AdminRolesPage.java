package TestCases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Utilities.BaseClass;
import Utilities.ExtentReportManager;

@Listeners(ExtentReportManager.class)
public class TC005_AdminRolesPage extends BaseClass {

    @Test
    public void rolesPageLCORoleCreateRoleTest() throws Throwable {
        // Start logging in Extent Report
        ExtentReportManager.getExtentTest().log(Status.INFO, "Test Case: rolesPageLCORoleTest started");

        // Open the application URL
        Thread.sleep(2000);
        driver.get(URL);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Navigated to URL: " + URL);
        
        // Perform admin login
        Thread.sleep(2000);
        adminLogin();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Admin login performed");

        // Navigate to Roles Page
        driver.findElement(By.xpath("//span[normalize-space()='Roles']")).click();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Navigated to Roles Page");
        Thread.sleep(3000);
        
        boolean isLCORoleFound = false;
        boolean isLastPageReached = false; // To track if the last page is reached

        while (!isLCORoleFound && !isLastPageReached) {
            // Capture all the data of the web table in Roles
            WebElement rolesTable = driver.findElement(By.xpath("//table[contains(@class,'table-data')]"));
            List<WebElement> rows = rolesTable.findElements(By.tagName("tr"));

            // Iterate through each row to find the specific role
            for (int i = 1; i < rows.size(); i++) {
                List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
                if (!cols.isEmpty()) {
                    String roleName = cols.get(1).getText(); // Assuming Role Name is in the 2nd column (index 1)

                    // Check if the role contains "LCO"
                    if ("LCO".equals(roleName)) {
                        WebElement LCORoleName = cols.get(1).findElement(By.xpath(".//span[contains(@class,'font-lexendDecaLight')]"));
                        boolean isLCORoleNameDisplayed = LCORoleName.isDisplayed();
                        ExtentReportManager.getExtentTest().log(Status.INFO, "LCO Role icon visibility: " + isLCORoleNameDisplayed);
                        Assert.assertTrue(isLCORoleNameDisplayed, "LCO Role icon is not displayed!");

                        String displayedText = LCORoleName.getText();
                        ExtentReportManager.getExtentTest().log(Status.INFO, "Text of LCO Role icon: " + displayedText);

                        isLCORoleFound = true; // Set to true if the role is found
                        break; // Exit the row loop once the role is found
                    }
                }
            }

            // Check if the role was not found
            if (!isLCORoleFound) {
                try {
                    // Find and click the 'Next' button to go to the next page
                    WebElement nextButton = driver.findElement(By.xpath("(//button[contains(@class,'h-[30px] w-[30px] m-1 text-sm font-medium rounded-md border border-gray-300 bg-white text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-indigo-500')][normalize-space()='>'])[1]"));
                    if (nextButton.isEnabled()) {
                        nextButton.click();
                        ExtentReportManager.getExtentTest().log(Status.INFO, "Clicked on 'Next' button");
                        Thread.sleep(3000); // Wait for the next page to load
                    } else {
                        // No more pages to search
                        isLastPageReached = true;
                        ExtentReportManager.getExtentTest().log(Status.INFO, "Role 'LCO' not found after checking all pages.");
                        Assert.fail("Role 'LCO' not found after checking all pages.");
                    }
                } catch (NoSuchElementException e) {
                    // 'Next' button not found, meaning no additional pages exist
                    isLastPageReached = true;
                    ExtentReportManager.getExtentTest().log(Status.INFO, "Role 'LCO' not found on the first page, and no more pages exist.");
                    Assert.fail("Role 'LCO' not found on the first page, and no more pages exist.");
                }
            }
        }
        
        // verify create role button
        driver.findElement(By.xpath("//button[normalize-space()='Create Role']")).click();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Clicked on Create Role button");

        // Verify if the 'Cancel' button is present to ensure the 'Create Role' page is loaded
        WebElement verifyCreateRole = driver.findElement(By.xpath("//button[normalize-space()='Cancel']"));
        boolean isCreateRoleFound = verifyCreateRole.getText().toLowerCase().equals("cancel");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking Create Role page visibility");
        Assert.assertTrue(isCreateRoleFound, "Create Role Button Not Found");

        // Update summary results in the 'Smoke Test Cases' sheet
        if (isLCORoleFound && isCreateRoleFound) {
            ExtentReportManager.getExtentTest().log(Status.PASS, "LCO Role name & Create Role button are successful.");
        }else {
        	ExtentReportManager.getExtentTest().log(Status.FAIL, "LCO Role name & Create Role button are not successful.");
        }
    }
}
