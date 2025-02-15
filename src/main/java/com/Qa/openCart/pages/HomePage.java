package com.Qa.openCart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Qa.openCart.constants.AppConstants;
import com.Qa.openCart.util.ElementUtil;

public class HomePage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content > h2");
	private By searchInput = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	/*Note: these were previous methods before introducing element util. Below methods are after using element util.
	 * public String getHomePageTitle() { String title = driver.getTitle();
	 * System.out.println("Home page title===>" + title); return title; }
	 * 
	 * public String getHomePageURL() { String url = driver.getCurrentUrl();
	 * System.out.println("Home page url===>" + url); return url; }
	 */
	
	public String getHomePageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.HOME_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
		//String title = driver.getTitle();
		System.out.println("login page title===>" + title);
		return title;
	}
	
	public String getHomePageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.HOME_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIME_OUT);
		//String url = driver.getCurrentUrl();
		System.out.println("login page url===>" + url);
		return url;
	}
	
	public boolean isLogoutLinkExist() {
		//return driver.findElement(logoutLink).isDisplayed();
		return eleUtil.doIsElementDisplayed(logoutLink);
	}
	
	public void logout() {
		if(isLogoutLinkExist()) {
			eleUtil.doClick(logoutLink);
		}
		//pending-WIP
	}
	
	public List<String> getHeadersList() {
		List<WebElement> headersList = eleUtil.waitForElementsVisible(headers, AppConstants.SHORT_TIME_OUT);
		//List<WebElement> headersList = driver.findElements(headers);
		List<String> headersValList = new ArrayList<String>();
		for(WebElement e : headersList) {
			String text = e.getText();
			headersValList.add(text);
		}
		return headersValList;
	}
	
	public void doSearch(String searchKey) {
		System.out.println("search key==" + searchKey);
		WebElement searchEle = eleUtil.waitForElementVisible(searchInput, AppConstants.DEFAULT_TIME_OUT);
		eleUtil.doSendKeys(searchEle, searchKey);
		eleUtil.doClick(searchIcon);
		
		/*
		 * searchEle.clear(); searchEle.sendKeys(searchKey);
		 * eleUtil.doClick(searchIcon);
		 */
		//driver.findElement(searchInput).sendKeys(searchKey);
		//driver.findElement(searchIcon).click();
	}

}
