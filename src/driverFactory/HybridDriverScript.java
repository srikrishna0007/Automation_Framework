package driverFactory;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import constant.PBConstant;
import utilities.ExcelFileUtil;

public class HybridDriverScript extends PBConstant{
	String inputpath="C:\\Automation\\Automation_Framework\\Testinput\\HybridTest.xlsx";
	String outputpath="C:\\Automation\\Automation_Framework\\test-output\\HybridResults.xlsx";
	String TCSheet="TestCases";
	String TSSheet="TestSteps";
	ExtentReports report;
	ExtentTest test;
	@Test
	public void startTest()throws Throwable
	{
		//generate html report
		report= new ExtentReports("./ExtentReport/"+FunctionLibrary.generateDate()+"  "+"Hybrid.html");
		boolean res =false;
		String tcres="";
		//accessing excel methods
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//count no of rows in TCSheet
		int TCCount =xl.rowCount(TCSheet);
		//count no of rows in TSSheet
		int TSCount =xl.rowCount(TSSheet);
		Reporter.log("No of rows in TCSheet::"+TCCount+"  "+"No of rows in TSSheet::"+TCCount,true);
		//iterate all rows in TCSheet
		for(int i=1; i<=TCCount; i++)
		{
			//start test case
			test= report.startTest("HybridFramework");
			//read execute cell
			String Execute =xl.getCellData(TCSheet, i, 2);
			if(Execute.equalsIgnoreCase("Y"))
			{
			//read tcid cell
				String tcid =xl.getCellData(TCSheet, i, 0);
				//iterate all rows in TSSheet
				for(int j=1; j<=TSCount; j++)
				{
					//read description
					String Desription=xl.getCellData(TSSheet, j, 2);
					//read tsid cell
					String tsid = xl.getCellData(TSSheet, j, 0);
					if(tcid.equalsIgnoreCase(tsid))
					{
						//get keyword cell
						String keyword = xl.getCellData(TSSheet, j, 4);
						if(keyword.equalsIgnoreCase("AdminLogin"))
						{
							String user=xl.getCellData(TSSheet, j, 5);
							String pass=xl.getCellData(TSSheet, j, 6);
							res=FunctionLibrary.verifyLogin(user, pass);
							test.log(LogStatus.INFO, Desription);
						}
						else if(keyword.equalsIgnoreCase("NewBranchCreation"))
						{
							String branchname=xl.getCellData(TSSheet, j, 5);
							String Address1=xl.getCellData(TSSheet, j, 6);
							String Address2=xl.getCellData(TSSheet, j, 7);
							String Address3=xl.getCellData(TSSheet, j, 8);
							String Area=xl.getCellData(TSSheet, j, 9);
							String Zipcode=xl.getCellData(TSSheet, j, 10);
							String country=xl.getCellData(TSSheet, j, 11);
							String state=xl.getCellData(TSSheet, j, 12);
							String city=xl.getCellData(TSSheet, j, 13);
							FunctionLibrary.ClickBranches();
							res=FunctionLibrary.verifyNewBranch(branchname, Address1, Address2, Address3, Area, Zipcode, country, state, city);
							test.log(LogStatus.INFO, Desription);
						}
						else if(keyword.equalsIgnoreCase("UpdateBranch"))
						{
							String branch =xl.getCellData(TSSheet, j, 5);
							String Address1 =xl.getCellData(TSSheet, j, 6);
							String zip =xl.getCellData(TSSheet, j, 10);
							FunctionLibrary.ClickBranches();
							res=FunctionLibrary.verifyBranchUpdate(branch, Address1, zip);
							test.log(LogStatus.INFO, Desription);
						}
						else if(keyword.equalsIgnoreCase("NewRoleCreation"))
						{

							String rolename=xl.getCellData(TSSheet, j, 5);
							String roledes=xl.getCellData(TSSheet, j, 6);
							String roletype=xl.getCellData(TSSheet, j, 7);
							FunctionLibrary.ClickRoles();
							res=FunctionLibrary.verifyRoles(rolename, roledes, roletype);
							test.log(LogStatus.INFO,Desription);
						
						
						}
						else if(keyword.equalsIgnoreCase("AdminLogout"))
						{
							res=FunctionLibrary.verifyLogout();
							test.log(LogStatus.INFO, Desription);
						}
						String tsres="";
						if(res)
						{
							//if res true write as pass into results cell
							tsres="Pass";
							xl.setCellData(TSSheet, j, 3, tsres, outputpath);
							test.log(LogStatus.PASS, Desription);
						}
						else
						{
							//if res false write as fail into results cell
							tsres="fail";
							xl.setCellData(TSSheet, j, 3, tsres, outputpath);
							test.log(LogStatus.FAIL, Desription);
						}
						tcres = tsres;
					}
					report.endTest(test);
					report.flush();
				}
				//write as tcres into TCSheet
				xl.setCellData(TCSheet, i, 3, tcres, outputpath);
			}
			else
			{
				//write as blocked into status cell in TCSheet
				xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
			}
			
		}
	}
}
