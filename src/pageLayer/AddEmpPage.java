package pageLayer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class AddEmpPage {
WebDriver driver;
public AddEmpPage(WebDriver driver)
{
this.driver=driver;
}
@FindBy(id="menu_pim_viewPimModule")
WebElement ClickPIM;
@FindBy(id="btnAdd")
WebElement ClickAddbtn;
@FindBy(xpath="//input[@id='firstName']")
WebElement Fname;
@FindBy(xpath="//input[@id='middleName']")
WebElement Mname;
@FindBy(xpath="//input[@id='lastName']")
WebElement Lname;
@FindBy(xpath="//input[@id='btnSave']")
WebElement ClickSavebtn;
public void verifyEmp(String Fname,String Mname,String Lname) throws Throwable
{
Actions ac= new Actions(driver);
ac.moveToElement(ClickPIM).click().perform();
Thread.sleep(3000);
ac.moveToElement(ClickAddbtn).click().perform();
Thread.sleep(3000);
this.Fname.sendKeys(Fname);
this.Mname.sendKeys(Mname);
this.Lname.sendKeys(Lname);
ac.moveToElement(ClickSavebtn).click().perform();
Thread.sleep(3000);
}

}
