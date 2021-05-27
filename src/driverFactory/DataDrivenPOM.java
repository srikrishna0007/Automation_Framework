package driverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;




import pageLayer.AddUserPage;
import pageLayer.LoginPage;
import pageLayer.LogoutPage;
import utilities.ExcelFileUtil;

public class DataDrivenPOM {
WebDriver driver;
ExtentReports report;
ExtentTest test;
String inputpath="C:\\Automation\\Automation_Framework\\Testinput\\UserCreation.xlsx";
String outputpath="C:\\Automation\\Automation_Framework\\TestOutput\\userResults.xlsx";
@BeforeTest
public void setUp()throws Throwable
{
	report= new ExtentReports("./ExtentReport/User.html");
	System.setProperty("webdriver.chrome.driver", "./CommonDrivers/chromedriver.exe");
	driver= new ChromeDriver();
	driver.get("http://orangehrm.qedgetech.com/");
	driver.manage().window().maximize();
	LoginPage login = PageFactory.initElements(driver, LoginPage.class);
	login.VerifyLogin("Admin", "Qedge123!@#");
}
@Test
public void adduser()throws Throwable
{
	AddUserPage user = PageFactory.initElements(driver, AddUserPage.class);
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//count row in sheet
	int rc =xl.rowCount("userData");
	Reporter.log("No of rows::"+rc,true);
	for(int i=1;i<=rc; i++)
	{
		test= report.startTest("User Creation");
		String ename =xl.getCellData("userData", i, 0);
		String username =xl.getCellData("userData", i, 1);
		String password =xl.getCellData("userData", i, 2);
		String cpassword=xl.getCellData("userData", i, 3);
		user.verifyUser(ename, username, password, cpassword);
		String expected="viewSystemUsers";
		String actual =driver.getCurrentUrl();
		if(actual.contains(expected))
		{
			Reporter.log("User addess Success::"+expected+"      "+actual,true);
			xl.setCellData("userData", i, 4, "Pass", outputpath);
			test.log(LogStatus.PASS, "User addess Success::"+expected+"   "+actual);
		}
		else
		{
			Reporter.log("User Not added::"+expected+"      "+actual,true);
			xl.setCellData("userData", i, 4, "Fail", outputpath);
			test.log(LogStatus.FAIL, "User Not Added::"+expected+"   "+actual);
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


