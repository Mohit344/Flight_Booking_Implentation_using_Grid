package Flight_Booking;

	import java.io.FileInputStream;

	import java.io.IOException;
	import java.time.Duration;
	import java.util.NoSuchElementException;
	import java.util.Properties;
	import java.util.concurrent.TimeUnit;
	import java.util.function.Function;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
    import org.openqa.selenium.remote.RemoteWebDriver;
    import org.openqa.selenium.support.ui.FluentWait;
	import org.testng.annotations.AfterClass;
    import org.testng.annotations.BeforeClass;
    import org.testng.annotations.Parameters;
    import org.testng.annotations.Test;

    import BaseClass.BaseClass;
    import Xlsx_Reader.XlsxReader;
	public class Two_Way_Booking {
		
		public static RemoteWebDriver driver;
		
		Properties loc = new Properties();
        
		/**
         * 
         * @param browser
         * @throws IOException
         */
		
		 @BeforeClass
		   @Parameters("browser")
		public void setUp(String browser) throws IOException
		{
			FileInputStream input = new FileInputStream(".\\src\\test\\resources\\locators\\locators.properties");

			loc.load(input);
			driver=BaseClass.getDriver(browser);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		}
		 
         /**
          * 
          * @param arr
          * @throws InterruptedException
          */
		 
		@Test(priority=1,dataProvider="SearchProvider",dataProviderClass=XlsxReader.class)
		public void search(String[] arr) throws InterruptedException
		{
			driver.get(loc.getProperty("url"));
			driver.findElement(By.xpath(loc.getProperty("domestic"))).click();
			driver.findElement(By.xpath(loc.getProperty("twoway"))).click();
			WebElement from = waitAndGetElement(30, driver,"(//input[@class='search'])[1]");
			from.sendKeys(arr[0]);
			Thread.sleep(1000);
			WebElement to = waitAndGetElement(30, driver,"(//input[@class='search'])[2]");
			to.sendKeys(arr[1]);
			Thread.sleep(1000);
			driver.findElement(By.xpath(loc.getProperty("depart"))).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath(loc.getProperty("departdate"))).click();
			WebElement arrivedate = waitAndGetElement(30, driver,"(//a[text()='1' and @class='day'])[4]");
			arrivedate.click();
			Thread.sleep(1000);
			//driver.findElement(By.xpath(loc.getProperty("adult"))).click();
		    Thread.sleep(1000);
			driver.findElement(By.xpath(loc.getProperty("class"))).click();
			driver.findElement(By.xpath(loc.getProperty("submit"))).click();
			Thread.sleep(10000);
			driver.findElement(By.xpath(loc.getProperty("fly1"))).click();
		
			driver.findElement(By.xpath(loc.getProperty("fly2"))).click();
	       
	    	driver.findElement(By.xpath(loc.getProperty("book2"))).click();
			driver.findElement(By.xpath(loc.getProperty("email"))).sendKeys(arr[2]);
			String phoneno=arr[3].replace(".", "").replace("E9", "");
			
			driver.findElement(By.xpath(loc.getProperty("mobile"))).sendKeys(phoneno);
			
			driver.findElement(By.xpath(loc.getProperty("continue"))).click();
		    driver.findElement(By.xpath(loc.getProperty("dropdown1"))).click();
		    driver.findElement(By.xpath(loc.getProperty("Mr"))).click();
			driver.findElement(By.xpath(loc.getProperty("firstname1"))).sendKeys(arr[4]);
			driver.findElement(By.xpath(loc.getProperty("lastname1"))).sendKeys(arr[5]);
			driver.findElement(By.xpath(loc.getProperty("submitlast"))).click();	  
		}
		
		/**
		 * 
		 * @param seconds
		 * @param driver
		 * @param xpath
		 * @return
		 */
		public WebElement waitAndGetElement(long seconds,WebDriver driver,final String xpath) {
			FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(seconds))
					.pollingEvery(Duration.ofSeconds(2))
					.ignoring(NoSuchElementException.class);
			
			WebElement element = fluentWait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver dvr) {
					return dvr.findElement(By.xpath(xpath));
				}
			});
			return element;
		}
		
		/**
		 * tearDown() is used for the implementation of quit method 
		 */
		
		@AfterClass
		public void tearDown()
		{
			driver.quit();// quit() is use to close the browser window 
		}


	}

