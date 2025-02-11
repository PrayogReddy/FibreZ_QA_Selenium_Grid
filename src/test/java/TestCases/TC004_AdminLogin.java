package TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Utilities.BaseClass;
import Utilities.ExtentReportManager;

@Listeners(ExtentReportManager.class)
public class TC004_AdminLogin extends BaseClass {

    @Test
    public void adminLoginTest() throws Throwable {
        // Start logging in Extent Report
        ExtentReportManager.getExtentTest().log(Status.INFO, "Test Case: adminLoginTest started");

        // Open the application URL
        Thread.sleep(2000);
        driver.get(URL);
        Thread.sleep(2000);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Navigated to URL: " + URL);

        // Perform admin login
        adminLogin();
        Thread.sleep(2000);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Admin login performed");

        // Verify if login is successful by checking for 'Logout' text
        WebElement verifyLoginSuccess = driver.findElement(By.xpath("//li[@class='text-sm cursor-pointer']"));
        boolean isLoginSuccessful = verifyLoginSuccess.getText().toLowerCase().equals("logout");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking login success");

        // Assertion to check if login was successful
        Assert.assertTrue(isLoginSuccessful, "Login was not successful!");
        
        // Verify Admin Dashboard is visible
        WebElement verifyAdminDashboardSuccess = driver.findElement(By.xpath("//p[normalize-space()='Admin Dashboard']"));
        boolean isAdminDashboardVisible = verifyAdminDashboardSuccess.getText().toLowerCase().equals("admin dashboard");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking Admin Dashboard visibility");
        Assert.assertTrue(isAdminDashboardVisible, "Admin Dashboard was not visible after login!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "Admin Dashboard is visible");
        Thread.sleep(2000);

        // Validate Revenue Pie Chart is visible on the Dashboard
        WebElement pieChartElement = driver.findElement(By.xpath("(//canvas[@role='img'])[1]"));
        pieChartElement.click();
        WebElement isPieChartVisible = driver.findElement(By.xpath("//p[normalize-space()='List of Payments']"));
        boolean isListofPaymentsVisible = isPieChartVisible.getText().equalsIgnoreCase("List of Payments");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking Pie Chart visibility on Admin Dashboard");
        Assert.assertTrue(isListofPaymentsVisible, "Pie chart is not visible on the Admin Dashboard!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "Pie chart is visible on the Admin Dashboard");
        driver.findElement(By.xpath("//div[@class='relative bg-basegray border rounded-lg rounded-b-none px-3 py-2']//*[name()='svg']")).click();
        
        // Perform logout action
        ExtentReportManager.getExtentTest().log(Status.INFO, "Performing logout action");
        Logout();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Logout action performed");
        
        // Verify logout is successful by checking if 'Login' text is present
        WebElement verifyLogoutSuccess = driver.findElement(By.xpath("(//p[@class='hidden text-base text-[#666666] mr-[5px] md:inline'])[1]"));
        boolean isLogoutSuccessful = verifyLogoutSuccess.getText().toLowerCase().equals("login");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Checking logout success by verifying 'Login' text");
        Assert.assertTrue(isLogoutSuccessful, "Logout was not successful!");

        // Log success in Extent Reports if login is successful
        if (isLoginSuccessful && isAdminDashboardVisible && isListofPaymentsVisible && isLogoutSuccessful) {
            ExtentReportManager.getExtentTest().log(Status.PASS, "Login, Admin Dashboard, Pie Chart visibility & Logout are successful.");
        } else {
            // Log failure in Extent Reports if login is not successful
            ExtentReportManager.getExtentTest().log(Status.FAIL, "Login, Admin Dashboard, Pie Chart visibility & Logout are not successful");
        }
    }
}
