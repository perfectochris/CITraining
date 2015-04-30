package pageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class BostonGlobeHomePage {
	private WebDriver driver;
	
	public BostonGlobeHomePage (WebDriver driver) {
		this.driver = driver;
	}

	public void enterZip (String zip){
		driver.findElement(By.xpath(".//*[@id='form1']/input[1]")).click();
		driver.findElement(By.xpath(".//*[@id='form1']/input[1]")).sendKeys(zip);
		driver.findElement(By.xpath(".//*[@id='ubmit']")).click();
	}
	
	public void selectSubscriptionLength() {
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@for='rdSubscription1']")));
		driver.findElement(By.xpath("//label[@for='rdSubscription1']")).click();
		driver.findElement(By.xpath("//div[@id='continue_btn']")).click();
	}
	
}