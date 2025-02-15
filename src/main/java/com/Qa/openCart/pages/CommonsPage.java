package com.Qa.openCart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Qa.openCart.constants.AppConstants;
import com.Qa.openCart.util.ElementUtil;

public class CommonsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public CommonsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By logo = By.className("img-responsive");
	private By footer = By.xpath("//footer//a");
	
	public boolean isLogoDisplayed() {
		return eleUtil.doIsElementDisplayed(logo);
	}
	
	public List<String> getFootersList(){
		List<WebElement> footersList = eleUtil.waitForElementsPresence(footer, AppConstants.DEFAULT_TIME_OUT);
		System.out.println("Toatal number of footers: " + footersList.size());
		List<String> footers = new ArrayList<String>();
		for(WebElement e : footersList) {
			String text = e.getText();
			footers.add(text);
		}
		return footers;
	}
	
	public boolean checkFooterLink(String footerLink) {
		return getFootersList().contains(footerLink);
	}

}
