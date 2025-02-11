package TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Utilities.BaseClass;
import Utilities.ExtentReportManager;

@Listeners(ExtentReportManager.class)
public class TC003_SuperAdminLogin extends BaseClass {

    @Test
    @Parameters("browser")
    public void SuperAdminLoginTest() throws Throwable {
        // Start logging in Extent Report
        ExtentReportManager.getExtentTest().log(Status.INFO, "Test Case: SuperAdminLoginTest started");

        // Open the application URL
        Thread.sleep(2000);
        driver.get(URL);
        Thread.sleep(2000);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Navigated to URL: " + URL);

        // Perform SuperAdmin login
        superAdminLogin();
        ExtentReportManager.getExtentTest().log(Status.INFO, "SuperAdmin login performed");


        // Verify if login is successful by checking for 'Logout' text
        WebElement verifyLoginSuccess = driver.findElement(By.xpath("//li[@class='text-sm cursor-pointer']"));
        boolean isLoginSuccessful = verifyLoginSuccess.getText().toLowerCase().equals("logout");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking login success");

        // Assertion to check if login was successful
        Assert.assertTrue(isLoginSuccessful, "Login was not successful!");
        
        // Verify if the 'Add Tenant' button is visible on the super admin dashboard
        WebElement verifyAddTenantDisplayed = driver.findElement(By.xpath("//button[normalize-space()='Add Tenant']"));
        boolean isAddTenantVisible = verifyAddTenantDisplayed.getText().toLowerCase().equals("add tenant");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking Add Tenant button visibility");
        Assert.assertTrue(isAddTenantVisible, "Add Tenant was not visible after Super Admin login!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "Add Tenant button is visible");

        Thread.sleep(2000);
        verifyAddTenantDisplayed.click();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Clicked on Add Tenant button");

        // Check if the 'Add Tenant Details' page is displayed
        WebElement verifyAddTenantDetailsDisplayed = driver.findElement(By.xpath("//header[normalize-space()='Add Tenant Details']"));
        boolean isAddTenantDetailsVisible = verifyAddTenantDetailsDisplayed.getText().toLowerCase().equals("add tenant details");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking Add Tenant Details page visibility");
        Assert.assertTrue(isAddTenantDetailsVisible, "Add Tenant Details Page was not visible after clicking Add Tenant!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "Add Tenant Details page is visible");

        // Close the 'Add Tenant Details' page and verify the 'Tenants' page
        driver.findElement(By.xpath("//button[normalize-space()='Go Back']")).click();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Clicked on Go Back button to close Add Tenant Details page");

        WebElement verifyTenantsPageDisplayed = driver.findElement(By.xpath("(//p[@class='text-2xl px-4 py-2'])[1]"));
        boolean isTenantsPageDisplayed = verifyTenantsPageDisplayed.getText().toLowerCase().equals("tenants");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking Tenants page visibility after closing Add Tenant Details page");
        Assert.assertTrue(isTenantsPageDisplayed, "Tenants Page was not visible after closing Add Tenant Details!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "Tenants page is visible after closing Add Tenant Details");
        
        // Navigate to Tenant admins page
        driver.findElement(By.xpath("//span[normalize-space()='Tenant Admins']")).click();
                
        // Verify if the 'Add Tenant Admin' button is visible on the tenant admin page
        WebElement verifyAddTenantAdminDisplayed = driver.findElement(By.xpath("//button[normalize-space()='Add Tenant Admin']"));
        boolean isAddTenantAdminVisible = verifyAddTenantAdminDisplayed.getText().toLowerCase().equals("add tenant admin");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking Add Tenant Admin button visibility");
        Assert.assertTrue(isAddTenantAdminVisible, "Add Tenant Admin was not visible in Tenant Admin page");
        ExtentReportManager.getExtentTest().log(Status.PASS, "Add Tenant Admin button is visible");

        Thread.sleep(2000);
        verifyAddTenantAdminDisplayed.click();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Clicked on Add Tenant Admin button");

        // Check if the 'Create Tenant Admin' page is displayed
        WebElement verifyCreateTenantAdminDisplayed = driver.findElement(By.xpath("//p[normalize-space()='Create Tenant Admin']"));
        boolean isCreateTenantAdminVisible = verifyCreateTenantAdminDisplayed.getText().toLowerCase().equals("create tenant admin");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking Create Tenant Admin page visibility");
        Assert.assertTrue(isCreateTenantAdminVisible, "Create Tenant Admin Page was not visible after clicking Add Tenant Admin!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "Create Tenant Admin Details page is visible");

        // Close the 'Create Tenant Admin' page and verify the 'Tenant Admins' page
        driver.findElement(By.xpath("//button[normalize-space()='Cancel']")).click();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Clicked on Cancel button to close Create Tenant Admin page");

        WebElement verifyTenantAdminsPageDisplayed = driver.findElement(By.xpath("(//p[@class='text-2xl px-4 py-2'])[1]"));
        boolean isTenantAdminsPageDisplayed = verifyTenantAdminsPageDisplayed.getText().toLowerCase().equals("tenant admins");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking Tenant Admins page visibility after closing Create Tenant Admin page");
        Assert.assertTrue(isTenantAdminsPageDisplayed, "Tenant Admins Page was not visible after closing Create Tenant Admin!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "Tenant Admins page is visible after closing Create Tenant Admin");
        
        // Perform logout action
        ExtentReportManager.getExtentTest().log(Status.INFO, "Performing logout action");
        Thread.sleep(2000);
        Logout();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Logout action performed");
        
        // Verify logout is successful by checking if 'Login' text is present
        WebElement verifyLogoutSuccess = driver.findElement(By.xpath("(//p[@class='hidden text-base text-[#666666] mr-[5px] md:inline'])[1]"));
        boolean isLogoutSuccessful = verifyLogoutSuccess.getText().toLowerCase().equals("login");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking logout success by verifying 'Login' text");
        Assert.assertTrue(isLogoutSuccessful, "Logout was not successful!");

        // Log success in Extent Reports if login is successful
        if (isLoginSuccessful && isAddTenantVisible && isAddTenantDetailsVisible && isTenantsPageDisplayed && isAddTenantAdminVisible && isCreateTenantAdminVisible
        		&& isTenantAdminsPageDisplayed && isLogoutSuccessful) {
            ExtentReportManager.getExtentTest().log(Status.PASS, "Login, Add Tenant, Add tenant admin & logout are successful.");
        } else {
            // Log failure in Extent Reports if login is not successful
            ExtentReportManager.getExtentTest().log(Status.FAIL, "Login, Add Tenant, Add tenant admin & logout are not successful.");
        }
    }
}
