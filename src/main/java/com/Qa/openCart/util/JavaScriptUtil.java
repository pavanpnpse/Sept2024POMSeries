package com.Qa.openCart.util;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	
	private WebDriver driver;
	private JavascriptExecutor js;
	
	JavaScriptUtil(WebDriver driver){
		this.driver = driver;
		js = (JavascriptExecutor)driver;
	}
	
	public String getPageTitileUsingJS() {
		return js.executeScript("return document.title").toString();
	}
	
	public String getPageURLUsingJS() {
		return js.executeScript("return document.URL").toString();
	}
	
	public void refreshBrowserByJS() {
		js.executeScript("history.go(0)");
	}
	
	public void navigateBackToPage() {
		js.executeScript("history.go(-1)");
	}
	
	public void navigateFarwardPage() {
		js.executeScript("history.go(1)");
	}
	
	public void generateAlert(String message) {
		js.executeScript("alert('" + message + "')");
	}
	
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}
	
	public void scrollPageDown() {
		 js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public void scrollPageUp() {
		 js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}
	
	public void scrollDown(String height) {
		 js.executeScript("window.scrollTo(0, '" + height + "')");
	}
	
	public void scrollIntoView(WebElement element) {
		 js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void darwBorder(WebElement element) {
		 js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for(int i=0; i<10;i++) {
			changeColor("rgb(0,200,0)", element);
			changeColor("rgb(0,200,0)", element);
		}
	}
	
	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor='" + color + "'", element);
		try {
			Thread.sleep(20);
		}catch(InterruptedException e) {
			
		}
	}
	
	public void clickelementUsingJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}
	
	public void sendkeysByJSUsingId(String id, String value) {
		js.executeScript("document.getElementById('" + id + "').value = '" + value + "'");
	}

}
