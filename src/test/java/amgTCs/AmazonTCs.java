package amgTCs;

import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AmazonTCs {
	WebDriver driver = null;
	HSSFWorkbook workbook;
	HSSFSheet sheet;
	LinkedHashMap<String, Object[]> TestNGResults;
	public static String driverPath = "C:\\Users\\Amol Dhakate\\eclipse-workspace\\ArtAMG";
	String firstClickItem;

	@BeforeClass
	public void openBrowser() {
		String newUrl = "https://www.google.co.in/";
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Amol Dhakate\\Downloads\\chromedriver_win32(1)\\chromedriver.exe");
		ChromeOptions o = new ChromeOptions();
		o.addArguments("--incognito");
		DesiredCapabilities c = DesiredCapabilities.chrome();
		c.setCapability(ChromeOptions.CAPABILITY, o);
		driver = new ChromeDriver(o);
		driver.manage().window().maximize();
		driver.get(newUrl);
		System.out.println(newUrl + " " + "URL open Sucessfully");
	}

	@Test(priority = 1)
	public void printSearchResult() {
		WebElement abc = driver.findElement(By.name("q"));
		abc.click();
		System.out.println("Clicked on Searchbox");
		abc.sendKeys("amazon");
		int srchListSize = 0;
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		try {
			List<WebElement> srchList = driver.findElements(By.xpath("//div[@class='pcTkSc'][@role='option']"));
			System.out.println("Total serch result is :" + srchList.size());
			srchListSize = srchList.size();
			System.out.println("Going to print serch result :");
			for (WebElement webElement2 : srchList) {
				String sList = webElement2.getText();
				System.out.println(sList);
			}
		} catch (Exception e) {
			System.out.println("No result found");
		}

		abc.sendKeys(Keys.ENTER);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		if (srchListSize != 0) {
			TestNGResults.put("2", new Object[] { 1d, "Open Google and Type amazon then press eneter",
					"Suggestion of search result printed", "Pass" });
		} else {
			TestNGResults.put("2", new Object[] { 1d, "Open Google and Type amazon then press eneter",
					"Suggestion of search result printed", "Fail" });
		}
	}

	@Test(priority = 2)
	public void goToAmazon() {

		WebElement amg = driver.findElement(By.xpath("//h3[contains(text(),'Amazon.in')]"));
		amg.click();
		System.out.println("Clicked on amg");

		Select cat = new Select(driver.findElement(By.id("searchDropdownBox")));
		cat.selectByVisibleText("Electronics");

		WebElement esearch = driver.findElement(By.id("twotabsearchtextbox"));
		esearch.click();
		String texpectedSearch = "Dell Computers";
		esearch.sendKeys(texpectedSearch, Keys.ENTER);

		String ActualserchedText = driver.findElement(By.xpath("//span[@class='a-color-state a-text-bold']")).getText();
		System.out.println("Searched Text is: " + ActualserchedText);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		String NewActualserchedText = ActualserchedText.replace("\"", "");
		System.out.println(NewActualserchedText);

		if (texpectedSearch.contentEquals(NewActualserchedText)) {
			TestNGResults.put("3",
					new Object[] { 2d,
							"Click on amazon.in and Select catogary as 'Electronics' and search for 'Dell Computer'",
							"Serched for Dell Computer", "Pass" });
		} else {
			TestNGResults.put("3",
					new Object[] { 2d,
							"Click on amazon.in and Select catogary as 'Electronics' and search for 'Dell Computer'",
							"Serched for Dell Computer", "Fail" });
		}

	}

	@Test(priority = 3)
	public void priceRange() {
		String lpr = "30,000";
		String hpr = "50,000";
		WebElement lrange = driver.findElement(By.id("low-price"));
		lrange.sendKeys(lpr, Keys.TAB);
		WebElement hrange = driver.findElement(By.id("high-price"));
		hrange.sendKeys(hpr);
		driver.findElement(By.id("a-autoid-1")).click();
		System.out.println("Range filter applied sucessfully");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String lr = driver.findElement(By.id("low-price")).getAttribute("value");
		String hr = driver.findElement(By.id("high-price")).getAttribute("value");
		System.out.println("Min: " + lr + " Max: " + hr);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		if (lr.contentEquals(lpr) && hr.contentEquals(hpr)) {
			TestNGResults.put("4", new Object[] { 3d, "Enter Min Price as '30,000' and Max Price as '50,000'",
					"Range filter applied sucessfully", "Pass" });
		} else {
			TestNGResults.put("4", new Object[] { 3d, "Enter Min Price as '30,000' and Max Price as '50,000'",
					"Range filter applied sucessfully", "Fail" });
		}

	}

	@Test(priority = 4)
	public void printfiveStarRating() {
		System.out.println("Going to print first page list :");
		int sizeOfList1 = print5Star();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[@aria-label='Go to page 2']")).click();
		System.out.println("Going to print second page list :");
		int sizeOfList2 = print5Star();

		if (sizeOfList1 != 0 && sizeOfList2 != 0) {
			TestNGResults.put("5",
					new Object[] { 4d, "Print five star result in console", "Five star Printed Sucessfully", "Pass" });
		} else {
			TestNGResults.put("5",
					new Object[] { 4d, "Print five star result in console", "Five Star Printed Sucessfully", "Fail" });
		}

	}

	@Test(priority = 5)
	public void clickOnFirtsFive() {

		String listXpath = "//span[@aria-label='5.0 out of 5 stars']//parent::div//parent::div//parent::div//a[@class=\"a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal\"][@target=\"_blank\"]";
		System.out.println("Going to click on 5 star rating first product");
		try {
			WebElement aLink = driver.findElement(By.xpath(listXpath));
			aLink.click();

		} catch (Exception e) {
			System.out.println("No Recordes Found");
		}

		try {

			String currentHandle = driver.getWindowHandle();
			String aLink1 = driver.findElement(By.xpath(listXpath)).getAttribute("href");
			System.out.println(firstClickItem);
			Set<String> handles = driver.getWindowHandles();
			for (String actual : handles) {
				if (!actual.equalsIgnoreCase(currentHandle)) {
					driver.switchTo().window(actual);
					driver.get(aLink1);
					System.out.println("Windows Swithed");
				}
			}
			firstClickItem = driver.findElement(By.id("productTitle")).getText();
			amzLogin();
			driver.findElement(By.id("add-to-wishlist-button")).click();
			driver.findElement(By.id("atwl-dd-create-list")).click();
			WebElement ListName = driver.findElement(By.id("list-name"));
			ListName.clear();
			ListName.sendKeys("FromTestNG", Keys.TAB, Keys.TAB, Keys.TAB, Keys.ENTER);
			driver.findElement(By.xpath("//span[contains(text(),'Create List')]")).click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.findElement(By.id("a-autoid-58-announce")).click();
			String wishListAddedItem = driver.findElement(By.id("itemName_I2W37A5P1WW11W")).getText();
			System.out.println("Clicked item name is: " + firstClickItem);
			System.out.println("Wish List Item Name is: " + wishListAddedItem);
			System.out.println("Sucessfully added in wishlist");

			if (firstClickItem.equalsIgnoreCase(wishListAddedItem)) {
				TestNGResults.put("6",
						new Object[] { 5d, "Click on five rating product and add to wishList and validate",
								"Sucessfully added in wishlist,Item matched", "Pass" });
			} else {
				TestNGResults.put("6",
						new Object[] { 5d, "Click on five rating product and add to wishList and validate",
								"Sucessfully added in wishlist, Item not matched", "Fail" });
			}

		} catch (Exception e) {
			TestNGResults.put("6", new Object[] { 5d, "Click on five rating product and add to wishList and validate",
					"Sucessfully added in wishlist", "Fail" });

		}

	}

	@BeforeTest(alwaysRun = true)
	public void suiteSetUp() {

		// create a new work book
		workbook = new HSSFWorkbook();
		// create a new work sheet
		sheet = workbook.createSheet("TestNG Result Summary");
		TestNGResults = new LinkedHashMap<String, Object[]>();
		// add test result excel file column header
		TestNGResults.put("1", new Object[] { "Test Step No.", "Action", "Expected Output", "Actual Output" });

	}

	@AfterClass
	public void suiteTearDown() {
		// write excel file and file name is SaveTestNGResultToExcel.xls
		Set<String> keyset = TestNGResults.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = TestNGResults.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof Date)
					cell.setCellValue((Date) obj);
				else if (obj instanceof Boolean)
					cell.setCellValue((Boolean) obj);
				else if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(new File("SaveTestNGResultToExcel.xls"));
			workbook.write(out);
			out.close();
			System.out.println("Successfully saved Selenium WebDriver TestNG result to Excel File!!!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Sign Out from Amazon
		try {
			String signouthref = "/gp/flex/sign-out.html?path=%2Fgp%2Fyourstore%2Fhome&useRedirectOnSuccess=1&signIn=1&action=sign-out&ref_=nav_signout";
			driver.get(signouthref);
		} catch (Exception e) {
			System.out.println("You allready Logged out");
		}

		// close the browser and exit
		driver.close();
		driver.quit();
	}

	public int print5Star() {

		String listXpath = "//span[@aria-label='5.0 out of 5 stars']//parent::div//parent::div//parent::div//span[@class='a-size-medium a-color-base a-text-normal']";
		List<WebElement> ratingList = driver.findElements(By.xpath(listXpath));
		for (WebElement webElement2 : ratingList) {
			String name1 = webElement2.getText();
			System.out.println(name1 + "\n");

		}
		return ratingList.size();

	}

	public void amzLogin() {

//		Sign in to Amazon

		try {
			driver.findElement(By.id("nav-link-accountList-nav-line-1")).click();
			System.out.println("Clicked on Sign in");
			WebElement email = driver.findElement(By.id("ap_email"));
			email.click();
			String emialorphone = "abc@gmail.com";
			String pass = "YourPassword";
			
			email.sendKeys(emialorphone, Keys.ENTER);

			WebElement pass = driver.findElement(By.id("ap_password"));
			pass.click();
			pass.sendKeys(pass, Keys.ENTER);

			System.out.println("Login Sucess");

		} catch (Exception e) {
			System.out.println("Error While Login");
		}

	}
}
