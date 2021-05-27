package pageLayer;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogoutPage {
@FindBy(id="welcome")
WebElement ClickWelcome;
@FindBy(linkText="Logout")
WebElement ClickLogout;
public void verifyLogout() throws Throwable
{
ClickWelcome.click();
Thread.sleep(3000);
ClickLogout.click();
Thread.sleep(3000);
}
}
