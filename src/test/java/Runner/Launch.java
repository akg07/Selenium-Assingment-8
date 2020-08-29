package Runner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Launch {

	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "./src/test/resources/Driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://demoqa.com/browser-windows");
//		System.out.println(driver.getTitle());
		
//		Get Text From New Tab
		
		getTextFromTab(driver);
		
//		Get Text Form new window
		getTextFromWindow(driver);
		
//		get text from alert box
		getTextFromAlertWindow(driver);
	
	}

	private static void getTextFromAlertWindow(WebDriver driver) {
		WebElement alert = driver.findElement(By.xpath("//button[@id=\"messageWindowButton\" and @class=\"btn btn-primary\"]"));
		alert.click();
		
		String parent = driver.getWindowHandle();
		Set<String> iterator = driver.getWindowHandles();

		String popUpWin = null;
		Iterator<String> iter = iterator.iterator();
		
		while(iter.hasNext()) {
			popUpWin = iter.next();
		}
		
		driver.switchTo().window(popUpWin);
		
		System.out.println(driver.getTitle());
		WebElement alertText = driver.findElement(By.xpath("//body"));
		String msg = alertText.getText();
		
		System.out.println("Alerttext is --> " + msg);
		
		driver.close();
		
		driver.switchTo().window(parent);
		
	}

	private static void getTextFromWindow(WebDriver driver) {
		String parent = driver.getWindowHandle();
		
		WebElement newWindow = driver.findElement(By.xpath("//button[@id=\"windowButton\" and @class=\"mt-4 btn btn-primary\"]"));
		newWindow.click();
		
		Set<String> s = driver.getWindowHandles();
		
		Iterator<String> i = s.iterator();
		while(i.hasNext()) {
			String child_window = i.next();
			
			if(!parent.equals(child_window)) {
				driver.switchTo().window(child_window);
				WebElement winText = driver.findElement(By.xpath("//*[@id = \"sampleHeading\"]"));
				System.out.println("New window text is --> " + winText.getText());
				
				driver.close();
			}
		}
		
		driver.switchTo().window(parent);
		
	}

	private static void getTextFromTab(WebDriver driver) {
		WebElement newTab = driver.findElement(By.xpath("//button[@id=\"tabButton\" and @class=\"btn btn-primary\"]"));
		newTab.click();
		
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		
		WebElement tabText = driver.findElement(By.xpath("//*[@id=\"sampleHeading\"]"));
		System.out.println("New Tab Text is --> " + tabText.getText());
		
		driver.close();
		
		driver.switchTo().window(tabs.get(0));
	}

}
