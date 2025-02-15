package com.Qa.openCart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import com.Qa.openCart.constants.AppConstants;
import com.Qa.openCart.exceptions.FrameworkExecpetion;

import io.qameta.allure.Step;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	private static final Logger log =  LogManager.getLogger(DriverFactory.class);

	@Step("init the driver using properties : {0}")
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		//System.out.println("browser name is " + browserName);
		log.info("browser name is " + browserName);

		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		switch(browserName.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			break;
		case "edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			break;
		default:
			//System.out.println("===pls pass right browser====");
			log.error("===pls pass right browser====");
			new FrameworkExecpetion("===pls pass right browser====");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		//driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	/**
	 * get driver using threadlocal
	 * 
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/*
	 * This method is used to initialize properties
	 * Supply env name using maven command line
	 * mvn clean install -Denv="qa"
	 */
	public Properties initProp() {
		String envName = System.getProperty("env");
		//System.out.println("running tests suite on env:" +envName);
		log.info("running tests suite on env:" + envName);
		FileInputStream fis = null;
		prop = new Properties();
		
		try {
			if(envName == null) {
				System.out.println("no env is passed, hence running test suite on qa env..");
				log.warn("no env is passed, hence running test suite on qa env..");
				fis = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);
			} else {
				switch(envName.trim().toLowerCase()) {
				case "qa":
					fis = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);
					break;
				case "uat":
					fis = new FileInputStream(AppConstants.CONFIG_UAT_PROP_FILE_PATH);
					break;
				case "dev":
					fis = new FileInputStream(AppConstants.CONFIG_DEV_PROP_FILE_PATH);
					break;
				case "prod":
					fis = new FileInputStream(AppConstants.CONFIG_PROP_FILE_PATH);
					break;

				default:
					log.error("plz pass the right environment name" + envName);
					throw new FrameworkExecpetion("===INVALID ENV===");
				}
			} 

			prop.load(fis);

		}catch(FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	
	/**
	 * takescreenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);//it stores screenshot in temparery directory
		String path = System.getProperty("user.dir") + "/screenshot/" + "_" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);//it stores screenshot in temparery directory
		return srcFile;
	}
	
	public static byte[] getScreenshotByte() {
		byte[] byteArray = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES);//it stores screenshot in temparery directory
		return byteArray;
	}
	
	public static String getScreenshotBase64() {
		String base64String = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BASE64);//it stores screenshot in temparery directory
		return base64String;
	}


}
