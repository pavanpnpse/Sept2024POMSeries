package com.Qa.openCart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.Qa.openCart.factory.DriverFactory;
import com.Qa.openCart.pages.CommonsPage;
import com.Qa.openCart.pages.HomePage;
import com.Qa.openCart.pages.LoginPage;
import com.Qa.openCart.pages.ProductInfoPage;
import com.Qa.openCart.pages.SearchResultsPage;
import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;

import io.qameta.allure.Step;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	
	WebDriver driver;
	DriverFactory df;
	
	protected Properties prop;
	
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected CommonsPage commonsPage;
	
	@Step("init the driver and properties")
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(String browserName) {
		
		df = new DriverFactory();
		prop = df.initProp();
		
		if(browserName !=null) {
			prop.setProperty("browser", browserName);
		}
		
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		searchResultsPage = new SearchResultsPage(driver);
		productInfoPage = new ProductInfoPage(driver);
		commonsPage = new CommonsPage(driver);
		
		//Below lines are for adding system variables to ChainTest report
		ChainPluginService.getInstance().addSystemInfo("Build#","1.0");
		ChainPluginService.getInstance().addSystemInfo("Headless#", prop.getProperty("headless"));
		ChainPluginService.getInstance().addSystemInfo("Incognito",prop.getProperty("incognito"));
		ChainPluginService.getInstance().addSystemInfo("Owner","Pavan Patil");
	}
	
	@AfterMethod
	public void attachScreenshot(ITestResult result) {
		if(!result.isSuccess()) {
		    //1st option: enable below method when file type returning
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
			/*
			 * "image/png", "image/jpg", "image/jpeg", "image/gif", "image/bmp",
			 * "image/webp", "image/tiff"
			 */
			//2nd option: enable below method when  Bye[] type returning
			//ChainTestListener.embed(DriverFactory.getScreenshotByte(), "image/png");
				
			//3rd option: enable below method when Base64 type returning
			//ChainTestListener.embed(DriverFactory.getScreenshotBase64(), "image/png");
		}
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
