package test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.xml.sax.Locator;

public class TestHelloWorld {

	public static void main(String[] args) {

		// 如果火狐浏览器没有默认安装在C盘，需要制定其路径
		System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.zim.com/pages/findyourroute.aspx");

		WebElement elementFrom = driver.findElement(By.name("ctl00$PlaceHolderRightSideBar$ctl00$schedulesWidget$findYourRouteNew2$txtFrom"));
		String k = "Hong Kong -- Hong Kong";
		elementFrom.sendKeys(k);

		WebElement elementTo = driver.findElement(By
				.name("ctl00$PlaceHolderRightSideBar$ctl00$schedulesWidget$findYourRouteNew2$txtTo"));
		elementTo.sendKeys("Singapore -- Singapore");

		JavascriptExecutor jse = (JavascriptExecutor) driver;

		jse.executeScript("document.getElementsByName('ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_txtFrom_Value')[0].setAttribute('type', 'text');");
		WebElement elementFromVal = driver.findElement(By.name("ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_txtFrom_Value"));
		elementFromVal.sendKeys("HKHKG;1");

		jse.executeScript("document.getElementsByName('ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_txtTo_Value')[0].setAttribute('type', 'text');");
		WebElement elementToVal = driver.findElement(By.name("ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_txtTo_Value"));
		elementToVal.sendKeys("SGSIN;1");

		WebElement select = driver.findElement(By.id("ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_ddlWeeksAhead"));

		List<WebElement> allOptions = select.findElements(By.tagName("option"));
		/*for (WebElement option : allOptions) {
			System.out.println(String.format("Value is: %s", option.getAttribute("value")));
			if (paramMap.get("weeks").equals(option.getAttribute("value"))) {
				option.click();
				break;
			}
		}*/

		WebElement searchElement = driver.findElement(By
				.id("ctl00_PlaceHolderRightSideBar_ctl00_schedulesWidget_findYourRouteNew2_btnFind"));
		searchElement.click();
		String response = driver.getPageSource();
		System.out.println(response);
		String title = driver.getTitle();
		String currentUrl = driver.getCurrentUrl();
		 System.out.println(title+"\n"+currentUrl);
		driver.close();
		
		

	}
	

}