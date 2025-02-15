package com.Qa.openCart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.Qa.openCart.constants.AppConstants;
import com.Qa.openCart.util.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String, String> productMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	public String getProductHeader() {
		 //String header = driver.findElement(productHeader).getText();
		 String header = eleUtil.doElementGetText(productHeader);
		 System.out.println("product header:===>" + header);
		 return header;
	}
	
	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsPresence(productImages, AppConstants.SHORT_TIME_OUT).size();
		System.out.println(getProductHeader() + ": Images Count = "+ imagesCount);
		return imagesCount;
	}
	
	/*
	 * get the full product information: header images count, meta data, price data
	 */
	public Map<String, String> getProductInfo() {
		//productMap = new HashMap<String, String>();
		productMap = new LinkedHashMap<String, String>();
		
		productMap.put("header", getProductHeader());
		productMap.put("imagescount", getProductImagesCount()+"");
		getProductMetaData();
		getProductPriceData();
		return productMap;
	}
	
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitForElementsPresence(productMetaData, AppConstants.DEFAULT_TIME_OUT);
		for(WebElement e : metaList) {
			String metaText = e.getText();
			String[] meta = metaText.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);
		}
	}
	
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.waitForElementsPresence(productPriceData, AppConstants.DEFAULT_TIME_OUT);
		String productPrice = priceList.get(0).getText().trim();
		String productExTax = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("price", productPrice);
		productMap.put("extax", productExTax);
	}

}
