package Utilities;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class AppUtils extends ExcelUtils {

    public WebDriver driver;
    public static String url = "http://10.80.3.31/fibrez/";
    
    // Centralize Excel file paths and sheet names
    public static final String testDataFilePath = "D:\\Prayog\\T_connect\\Automation\\QA_Smoke_TestData.xlsx";
    public static final String testDataSheetName1 = "Book Now Creation";  
    public static final String testDataSheetName2 = "Login Types";    
    public static final String smokeTestingFilePath = "D:\\Prayog\\T_connect\\Automation\\QA_Smoke_Testing_Automation_Testcases.xlsx";
    public static final String smokeTestingSheetName = "Smoke Test Cases";

    public void superAdminLogin() throws Throwable {
        int rowNum = 1;
        String username = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 1);
        String password = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 2);
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//p[@class='hidden text-base text-[#666666] mr-[5px] md:inline'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("robotCheck")).click();
//        String captcha = driver.findElement(By.xpath("(//span[@class='font-bold'])[1]")).getText();
//        driver.findElement(By.id("captcha")).sendKeys(captcha);
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        Thread.sleep(2000);
    }
    
    public void adminLogin() throws Throwable {
        int rowNum = 2;
        String username = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 1);
        String password = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 2);
        driver.findElement(By.xpath("(//p[@class='hidden text-base text-[#666666] mr-[5px] md:inline'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("robotCheck")).click();
//        String captcha = driver.findElement(By.xpath("(//span[@class='font-bold'])[1]")).getText();
//        driver.findElement(By.id("captcha")).sendKeys(captcha);
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        Thread.sleep(2000);
    }

    public void msoLogin() throws Throwable {
        int rowNum = 3;
        String username = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 1);
        String password = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 2);
        driver.findElement(By.xpath("(//p[@class='hidden text-base text-[#666666] mr-[5px] md:inline'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("robotCheck")).click();
//      String captcha = driver.findElement(By.xpath("(//span[@class='font-bold'])[1]")).getText();
//      driver.findElement(By.id("captcha")).sendKeys(captcha);
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        Thread.sleep(2000);
    }

    public void lcoLogin() throws Throwable {
        int rowNum = 4;
        String username = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 1);
        String password = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 2);
        driver.findElement(By.xpath("(//p[@class='hidden text-base text-[#666666] mr-[5px] md:inline'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("robotCheck")).click();
//      String captcha = driver.findElement(By.xpath("(//span[@class='font-bold'])[1]")).getText();
//      driver.findElement(By.id("captcha")).sendKeys(captcha);
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        Thread.sleep(2000);
    }

    public void customerLogin() throws Throwable {
        int rowNum = 7;
        String username = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 1);
        String password = ExcelUtils.getCellData(testDataFilePath, testDataSheetName2, rowNum, 2);
        driver.findElement(By.xpath("(//p[@class='hidden text-base text-[#666666] mr-[5px] md:inline'])[1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("robotCheck")).click();
//      String captcha = driver.findElement(By.xpath("(//span[@class='font-bold'])[1]")).getText();
//      driver.findElement(By.id("captcha")).sendKeys(captcha);
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        Thread.sleep(2000);
    }

    public void Logout() {
        // Perform logout action
        driver.findElement(By.xpath("//li[@class='text-sm cursor-pointer']")).click();
        //driver.quit();
    }

    @BeforeMethod
    public void startApp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Wait for the browser to be ready using JavaScript Executor
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    @AfterMethod
    public void closeApp() {
        if (driver != null) {
            driver.quit();
        }
    }
}
