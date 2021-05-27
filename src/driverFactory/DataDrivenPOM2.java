package driverFactory;import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pageLayer.AddEmpPage;
import pageLayer.LoginPage;
import pageLayer.LogoutPage;
import utilities.ExcelFileUtil;

public class DataDrivenPOM2 {
WebDriver driver;
ExtentReports report;
ExtentTest test;
String inputpath="C:\\Automation\\Automation_Framework\\Testinput\\empsheet.xlsx";
String outputpath="C:\\Automation\\Automation_Framework\\TestOutput\\empResults.xlsx";
@BeforeTest
public void setUp()throws Throwable
{
	report= new ExtentReports("./ExtentReport/emp.html");
	System.setProperty("webdriver.chrome.driver", "./CommonDrivers/chromedriver.exe");
	driver= new ChromeDriver();
	driver.get("http://orangehrm.qedgetech.com/");
	driver.manage().window().maximize();
	LoginPage login = PageFactory.initElements(driver, LoginPage.class);
	login.VerifyLogin("Admin", "Qedge123!@#");
}
@Test
public void addemp() throws Throwable
{
	AddEmpPage emp = PageFactory.initElements(driver, AddEmpPage.class);
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//count row in sheet
	int rc =xl.rowCount("empsheet");
	Reporter.log("No of rows::"+rc,true);
	for(int i=1;i<=rc; i++)
	{
		test= report.startTest("emp creation");
		String Fname =xl.getCellData("empsheet", i, 0);
		String Mname =xl.getCellData("empsheet", i, 1);
		String Lname =xl.getCellData("empsheet", i, 2);
		emp.verifyEmp(Fname, Mname, Lname);
		String expected="viewPersonalDetails";
		String actual =driver.getCurrentUrl();
		if(actual.contains(expected))
		{
			Reporter.log("emp adds Success::"+expected+"      "+actual,true);
			xl.setCellData("empsheet", i, 3, "Pass", outputpath);
			test.log(LogStatus.PASS, "emp adds Success::"+expected+"   "+actual);
		}
		else
		{
			Reporter.log("emp Not added::"+expected+"      "+actual,true);
			xl.setCellData("empsheet", i, 3, "Fail", outputpath);
			test.log(LogStatus.FAIL, "emp Not Added::"+expected+"   "+actual);
		}
		report.endTest(test);
		report.flush();
	}
}
@AfterTest
public void tearDown()throws Throwable
{
	LogoutPage logout = PageFactory.initElements(driver, LogoutPage.class);
	logout.verifyLogout();
	driver.close();
}
}

