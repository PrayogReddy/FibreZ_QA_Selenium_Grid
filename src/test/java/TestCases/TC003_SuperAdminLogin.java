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
        ExtentReportManager.getExtentTest().log(Status.INFO, "Test Case: SuperAdminLoginTest started");

        // Navigate to URL
        driver.get(URL);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Navigated to URL: " + URL);
        Thread.sleep(2000);

        // Perform login action
        superAdminLogin();
        ExtentReportManager.getExtentTest().log(Status.INFO, "SuperAdmin login performed");
        Thread.sleep(2000);

        // Validate login success
        WebElement verifyLoginSuccess = driver.findElement(By.xpath("//li[@class='text-sm cursor-pointer']"));
        boolean isLoginSuccessful = verifyLoginSuccess.getText().equalsIgnoreCase("Logout");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking login success");
        Assert.assertTrue(isLoginSuccessful, "Login was not successful!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "SuperAdmin login successful");

        // Click 'Add Tenant' button
        driver.findElement(By.xpath("//button[normalize-space()='Add Tenant']")).click();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Clicked on Add Tenant button");
        Thread.sleep(2000);

        // Validate 'Add Tenant Details' page visibility
        WebElement verifyAddTenantDetails = driver.findElement(By.xpath("//header[normalize-space()='Add Tenant Details']"));
        boolean isAddTenantDetailsVisible = verifyAddTenantDetails.getText().equalsIgnoreCase("Add Tenant Details");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking Add Tenant Details page visibility");
        Assert.assertTrue(isAddTenantDetailsVisible, "Add Tenant Details page not visible!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "Add Tenant Details page is visible");

        // Close the 'Add Tenant Details' page
        driver.findElement(By.xpath("//button[normalize-space()='Cancel']")).click();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Closed Add Tenant Details page");
        Thread.sleep(2000);

        // Perform logout action
        ExtentReportManager.getExtentTest().log(Status.INFO, "Performing logout action");
        Logout();
        Thread.sleep(2000);

        // Validate logout success
        WebElement verifyLogoutSuccess = driver.findElement(By.xpath("(//p[contains(@class, 'hidden') and contains(text(), 'Login')])[1]"));
        boolean isLogoutSuccessful = verifyLogoutSuccess.getText().equalsIgnoreCase("Login");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking logout success");
        Assert.assertTrue(isLogoutSuccessful, "Logout was not successful!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "SuperAdmin logout successful");
    }
}
