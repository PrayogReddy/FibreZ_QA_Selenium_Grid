package TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Utilities.BaseClass;
import Utilities.ExcelUtils;
import Utilities.ExtentReportManager;

@Listeners(ExtentReportManager.class)
public class TC002_BookNowRequest extends BaseClass {

    @Test
    @Parameters("browser")
    public void bookNowFeatureTest() throws Throwable {
        
        ExtentReportManager.getExtentTest().log(Status.INFO, "Test Case: bookNowFeatureTest started");

     // Iterate through each row of the test data
        for (int i = 4; i < 5; i++) {
        // Read test data from Excel
        String name = ExcelUtils.getCellData(testDataFilePath, testDataSheetName1, i, 0);
        String phoneNumber = ExcelUtils.getCellData(testDataFilePath, testDataSheetName1, i, 1);
        String emailAddress = ExcelUtils.getCellData(testDataFilePath, testDataSheetName1, i, 2);
        String message = ExcelUtils.getCellData(testDataFilePath, testDataSheetName1, i, 3);

        // Navigate to URL
        driver.get(URL);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Navigated to URL: " + URL);
        Thread.sleep(2000);
        
        // Click 'Book Now' button
        driver.findElement(By.xpath("//div[normalize-space()='Book Now']")).click();
        ExtentReportManager.getExtentTest().log(Status.INFO, "'Book Now' button clicked");
        Thread.sleep(2000);

        // Fill 'Book Now' form
        driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys(name);
        driver.findElement(By.xpath("//input[@placeholder='Phone Number']")).sendKeys(phoneNumber);
        driver.findElement(By.xpath("//input[@placeholder='Email ID']")).sendKeys(emailAddress);
        driver.findElement(By.xpath("//input[@placeholder='Message']")).sendKeys(message);
        ExtentReportManager.getExtentTest().log(Status.INFO, "Entered data: Name=" + name + ", Phone=" + phoneNumber + ", Email=" + emailAddress + ", Message=" + message);

        // Click 'Submit' button
        driver.findElement(By.xpath("//button[normalize-space()='Submit']")).click();
        ExtentReportManager.getExtentTest().log(Status.INFO, "'Submit' button clicked");
        Thread.sleep(2000);

        // Validate submission
        WebElement verifySuccess = driver.findElement(By.xpath("//p[contains(@class, 'hidden') and contains(text(), 'Login')]"));
        boolean isSubmissionSuccessful = verifySuccess.getText().equalsIgnoreCase("Login");
        ExtentReportManager.getExtentTest().log(Status.INFO, "Validating submission success");
        
        Assert.assertTrue(isSubmissionSuccessful, "Submission was not successful!");
        ExtentReportManager.getExtentTest().log(Status.PASS, "Submission successful");
    }
  }
}