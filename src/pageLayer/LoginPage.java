package pageLayer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
WebDriver driver;
public LoginPage(WebDriver driver)
{
this.driver=driver;
}
//maintain repository
@FindBy(name="txtUsername")
WebElement EnteruserName;
@FindBy(name="txtPassword")
WebElement EnterPassword;
@FindBy(name="Submit")
WebElement ClickLogin;
//method for login
public void VerifyLogin(String userName,String Password) throws Throwable
{
	this.EnteruserName.sendKeys(userName);
	this.EnterPassword.sendKeys(Password);
	this.ClickLogin.click();
	Thread.sleep(4000);
}


}
