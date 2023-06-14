package com.OrangeHRMAutomationTest;



import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.awt.Desktop;
import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class OrangeHRMEmpMgmt{

	WebDriver driver;
	public static ExtentReports extentReports;
	public static ExtentTest extentTest;
	
	@BeforeSuite
	public void initialiseExtentReports() {
		ExtentSparkReporter sparkReporter_all = new ExtentSparkReporter("AllTests.html");
		sparkReporter_all.viewConfigurer().viewOrder().as(new ViewName[] {
				ViewName.DASHBOARD,
				ViewName.TEST,
				ViewName.EXCEPTION,
				ViewName.AUTHOR,
				ViewName.DEVICE
				
		});
		sparkReporter_all.config().setReportName("All Tests report");
		
		ExtentSparkReporter sparkReporter_failed = new ExtentSparkReporter("FailedTests.html");
		sparkReporter_failed.filter().statusFilter().as(new Status[] {Status.FAIL}).apply();
		sparkReporter_failed.config().setReportName("Failure report");

		extentReports = new ExtentReports();
		extentReports.attachReporter(sparkReporter_all,sparkReporter_failed);
		
		extentReports.setSystemInfo("OS", System.getProperty("os.name"));
		extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
		extentReports.setSystemInfo("WEB URL", "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	}
	
	@AfterSuite
	public void generateExtentReports() throws Exception {
		extentReports.flush();
		Desktop.getDesktop().browse(new File("AllTests.html").toURI());
		Desktop.getDesktop().browse(new File("FailedTests.html").toURI());
	
	}
//	@BeforeTest
//	public void report(ITestContext context) {
//		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
//		String device = capabilities.getBrowserName()+" "+capabilities.getBrowserVersion();
//		String author = context.getCurrentXmlTest().getParameter("author");
//		
//		extentTest = extentReports.createTest(context.getName());
//		extentTest.assignAuthor(author);
//		extentTest.assignDevice(device);
//	}
//	@BeforeMethod
//	public void setup() {
//		WebDriverManager.chromedriver().setup();
//		driver = new ChromeDriver();
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
//		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
//		driver.manage().window().maximize();
//	}
	@Parameters("browser")
	@BeforeMethod
    public void setUp(ITestContext context,String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            // Set up the ChromeDriver
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            // Set up the GeckoDriver for Firefox
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("safari")) {
            // Set up the InternetExplorerDriver
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        } else {
            throw new IllegalArgumentException("Invalid browser specified");
        }

        // Open the Orange HRM website
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().window().maximize();
		Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
		String device = capabilities.getBrowserName()+" "+capabilities.getBrowserVersion();
		String author = context.getCurrentXmlTest().getParameter("author");
		
		extentTest = extentReports.createTest(context.getName());
		extentTest.assignAuthor(author);
		extentTest.assignDevice(device);
    }
	

	@Test(testName="login",priority=1)
	public void login() throws InterruptedException {
		extentTest.info( "Nevigate to Url");
		Thread.sleep(2000);
		extentTest.info("Enter username & password");
		driver.findElement(By.name("username")).sendKeys("Admin");
		driver.findElement(By.name("password")).sendKeys("admin123");
		extentTest.info("Click on the login page");
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")).click();
		Thread.sleep(2000);
		
		extentTest.info("Nevigated to Home page");
		String expectedURL = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
		String actualURL = driver.getCurrentUrl();
		Assert.assertEquals(expectedURL, actualURL);
		extentTest.pass("Assertion is passed for webpage URL");

	}

	@Test(testName="add",priority = 2)
	public void addEmployee() throws InterruptedException {
		login();
		Thread.sleep(2000);
		extentTest.info("Navigate to PIM");
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/aside/nav/div[2]/ul/li[2]/a")).click();
		Thread.sleep(2000);
		extentTest.info("Navigate to Add Employee page");
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[1]/button")).click();
		Thread.sleep(2000);
		extentTest.info("Enter data");
		driver.findElement(By.name("firstName")).sendKeys("Arnaz");
		driver.findElement(By.name("middleName")).sendKeys("Shahid");
		driver.findElement(By.name("lastName")).sendKeys("Jalse");
		String empId = driver.findElement(By.xpath(
				"//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[1]/div[2]/div[1]/div[2]/div/div/div[2]/input"))
				.getText();
		
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/form/div[2]/button[2]")).click();
		extentTest.info("Employee Successfully added");
		Thread.sleep(7000);
//		String actualName=driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/div/div[1]/div[1]/div[1]/h6")).getText();
//		Assert.assertEquals(actualName, "John Doe");		
		String empId2 = driver.findElement(By.xpath(
				"//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[1]/form/div[2]/div[1]/div[1]/div/div[2]/input"))
				.getText();
		
		Assert.assertEquals(empId, empId2);
		extentTest.pass("Assertion is passed");
		
	}
	
	@Test(testName="update",priority = 3)
	public void updateEmployee() throws InterruptedException {
		login();
		extentTest.info("Navigate to PIM");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/aside/nav/div[2]/ul/li[2]/a"))).click();
		extentTest.info("Enter Name & Click on search button");
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/div/div/input")).sendKeys("Arnaz Jalse");
		driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]")).click();
		extentTest.info("Click on update button");
		driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div[2]/div/div[9]/div/button[2]/i")).click();
		Thread.sleep(5000);
		
		WebElement updateName = driver.findElement(By.name("firstName"));
		updateName.clear();
		updateName.clear();
		
		extentTest.info("Enter new Name");
		updateName.sendKeys("ArnazUp");
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[1]/form/div[5]/button")).click();
		Thread.sleep(2000);
		driver.navigate().refresh();
		extentTest.info("Successfully updated");
		Thread.sleep(2000);
		String updatedactual = driver.findElement(By.tagName("h6")).getText();
		
		Assert.assertEquals("updatedactual", "ArnazUpJalse");
		extentTest.pass("Assertion is passed");
		
	}
	@Test(testName="delete",priority = 4)
	public void deleteEmployee() throws InterruptedException {
		login();
		extentTest.info("Navigate to PIM");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/aside/nav/div[2]/ul/li[2]/a"))).click();
		extentTest.info("Enter Name & Click on search button");
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/div/div/input")).sendKeys("ArnazUp");
		driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]")).click();
		Thread.sleep(2000);
		extentTest.info("Click on delete button");
		driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div[1]/div/div[9]/div/button[1]")).click();
		  
		driver.findElement(By.xpath("/html/body/div/div[3]/div/div/div/div[3]/button[2]")).click();
		Thread.sleep(2000);
		 
		extentTest.info("Employee Successfully deleted");
		String actual = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div")).getText();
		Assert.assertEquals(actual,"");
		extentTest.pass("Assertion is passed");
			  
	}
	@AfterMethod
	public void tearDown(Method m, ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			extentTest.fail(result.getThrowable());
		} else if(result.getStatus() == ITestResult.SUCCESS) {
			extentTest.pass(m.getName() + " is passed");
		}
		driver.quit();
	}

	

}
