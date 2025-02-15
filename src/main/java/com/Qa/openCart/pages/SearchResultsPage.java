package com.Qa.openCart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.Qa.openCart.constants.AppConstants;
import com.Qa.openCart.util.ElementUtil;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By productResults = By.cssSelector("div.product-thumb");
	
	public int getProductResultsCount() {
		//int resultCount = driver.findElements(productResults).size();
		int resultCount = eleUtil.waitForElementsVisible(productResults, AppConstants.DEFAULT_TIME_OUT).size();
		System.out.println("products result Count" + resultCount);
		return resultCount;
	}
	
	public void selectProduct(String productName) {
		System.out.println("product name" + productName);
		//driver.findElement(By.linkText(productName)).click();
		eleUtil.doClick(By.linkText(productName));
	}

}
