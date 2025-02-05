package TestCases;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import Utilities.BaseClass;
import Utilities.ExtentReportManager;

@Listeners(ExtentReportManager.class)
public class TC001_LaunchApplication extends BaseClass {

    @Test
    @Parameters("browser")
    public void checkUrlTest(String browser) throws Throwable {
        // Start logging in Extent Report
        ExtentReportManager.getExtentTest().log(Status.INFO, "Test Case: checkUrlTest started on " + browser);

        // Open the application URL
        driver.get(URL);
        Thread.sleep(2000);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Navigated to URL: " + URL);
        
        // Get the current URL
        String currentUrl = driver.getCurrentUrl();
        ExtentReportManager.getExtentTest().log(Status.INFO, "Current URL retrieved: " + currentUrl);

        // Compare the current URL to the expected URL
        boolean isUrlCorrect = currentUrl.equals(URL);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Expected URL: " + URL);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Is URL Correct: " + isUrlCorrect);

        // Assertion to check if the URL is correct
        Assert.assertTrue(isUrlCorrect, "URL was not visible after opening application");

        // Log result in Extent Report
        if (isUrlCorrect) {
            ExtentReportManager.getExtentTest().log(Status.PASS, "URL verification passed");
        } else {
            ExtentReportManager.getExtentTest().log(Status.FAIL, "URL verification failed");
        }

        // Record the timestamp of the test execution
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedNow = now.format(formatter);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Test executed at: " + formattedNow);
    }
}
