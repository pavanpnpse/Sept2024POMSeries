package com.Qa.openCart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Qa.openCart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {
	
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	private void nullcheck(CharSequence... value) {
		if(value == null) {
			throw new RuntimeException("value cannot be null");
		}
	}
	
	private void highlight(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}
	
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		highlight(element);
		return element;
	}
	
	public void doSendKeys(By locator, CharSequence... value) {
		nullcheck(value);
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
	}
	
	public void doSendKeys(WebElement element, CharSequence... value) {
		nullcheck(value);
		element.clear();
		element.sendKeys(value);
	}
	
	public void doSendKeys(String locatorType, String locatorValue, CharSequence... value) {
		nullcheck(value);
		WebElement element = getElement(getLocator(locatorType, locatorValue));
		element.clear();
		element.sendKeys(value);
	}
	
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}
	
	/**
	 * below getLocator is implemented to get locator based on string locator type and locator value.
	 * @param locatorType
	 * @param locatorValue
	 * @return
	 */
	public By getLocator(String locatorType, String locatorValue) {
		By locator = null;
		
		switch (locatorType.toUpperCase()) {
		case "ID":
			locator = By.id(locatorValue);
			break;
		case "NAME":
			locator = By.name(locatorValue);
			break;
		case "CLASSNAME":
			locator = By.className(locatorValue);
			break;
		case "XPATH":
			locator = By.xpath(locatorValue);
			break;
		case "CSSSELECTOR":
			locator = By.cssSelector(locatorValue);
			break;
		case "LINKTEXT":
			locator = By.linkText(locatorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(locatorValue);
			break;
		case "TAGNAME":
			locator = By.tagName(locatorValue);
			break;
			
		default:
			throw new RuntimeException("please pass right locator value: " + locatorType);
		}
		return locator;
	}
	
	public void doClick(String locatorType, String LocatorValue) {
		getElement(getLocator(locatorType, LocatorValue)).click();
	}
	
	public String doElementGetText(String locatorType, String LocatorValue) {
		return getElement(getLocator(locatorType, LocatorValue)).getText();
	}
	
	public boolean doElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	public List<WebElement> getElements(By locator){
		return driver.findElements(locator);
	}
	
	public boolean isElementDisplayed(By locator) {
		if(getElements(locator).size()==1) {
			System.out.println("element is displayed one time");
			return true;
		} else {
			System.out.println("element is not displayed");
			return false;
		}
	}
	
	public boolean isElementDisplayed(By locator, int elementCount) {
		if(getElements(locator).size()==elementCount) {
			System.out.println("element is displayed " + elementCount + "times" );
			return true;
		} else {
			System.out.println("count did not match expected element count");
			return false;
		}
	}
	
	//isDisplayed() method result in NoSuchElementException if element not found, To check element is displayed or not using isDisplayed() method. 
	public boolean doIsElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch(NoSuchElementException e) {
			System.out.println("Element is not displayed");
			return false;
		}
	}
	
	public String doGetDomAttribute(By locator, String attrName) {
		return getElement(locator).getDomAttribute(attrName);
	}
	
	public String doGetDomProperty(By locator, String propName) {
		return getElement(locator).getDomProperty(propName);
	}
	
	//************Select dropdown utilities******************//
	public void doSelectDropdownByIndex(By locator, int index) {
		Select sel = new Select(getElement(locator));
		sel.selectByIndex(index);
	}
	
	public void doSelectDropdownByVisibleText(By locator, String visibleText) {
		Select sel = new Select(getElement(locator));
		sel.selectByVisibleText(visibleText);
	}
	
	public void doSelectDropdownByValue(By locator, String value) {
		Select sel = new Select(getElement(locator));
		sel.selectByValue(value);
	}
	
	public List<String> getDropdownOptionsTextList(By locator) {
		Select sel =new Select(getElement(locator));
		List<WebElement> options = sel.getOptions();
		System.out.println("options size:" + options);
		
		List<String> optionValueList = new ArrayList<String>();
		for(WebElement e: options) {
			optionValueList.add(e.getText());
		}
		return optionValueList;
	}
	
	public int getDropdownOptionsCount(By locator) {
		Select sel =new Select(getElement(locator));
		List<WebElement> options = sel.getOptions();
		return options.size();
	}
	
	public void getDropdownOptionsText(By locator) {
		Select sel =new Select(getElement(locator));
		List<WebElement> options = sel.getOptions();
		
		for(WebElement e: options) {
			System.out.println(e.getText());
		}
	}
	
	public void doSelectDropdownByContainsText(By locator, String partialText) {
		Select sel =new Select(getElement(locator));
		sel.selectByContainsVisibleText(partialText);
	}
	
	public void doSearch(By dropdownFeild, By suggestionsdropdown, String searchKey, String actualValue) throws InterruptedException {
		//getElement(dropdownFeild).sendKeys(searchKey);
		doSendKeys(suggestionsdropdown, searchKey);
		Thread.sleep(2000);
		List<WebElement> suggestionsList = getElements(suggestionsdropdown);
		for(WebElement e : suggestionsList) {
				String text = e.getText();
				if(text.contains(actualValue)) {
					e.click();
					break;
				}
		}
	}
	
	public void selectChoice(By ChoiceDropdown, By choices, String... choiceValue) throws InterruptedException {
		doClick(ChoiceDropdown);
		Thread.sleep(3000);
		List<WebElement> choicesList = getElements(choices);
		System.out.println(choicesList.size());
		
		if(choiceValue[0].equalsIgnoreCase("all")) {
			for(WebElement e : choicesList) {
				e.click();
			}
		} else {
			for(WebElement e : choicesList) {
				String text = e.getText();
				System.out.println(text);
				
				for(String ch : choiceValue) {
					if(text.equals(ch)) {
						e.click();
					}
				}
			}
		}
	}
	
	//*****************Actions Utils****************************************************
	public void handleTwoLevelMenu(By parentMenuLocator, By childMenuLocator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenuLocator)).build().perform();
		getElement(childMenuLocator).click();
	}
	
	public void doActionsSendkeys(By locator, CharSequence ... value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator)).build().perform();
	}
	
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}
	
	public void handleFourLevelSubMenu(By level1, By level2, By level3, By level4) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(level1)).click();
		Thread.sleep(2000);
		act.moveToElement(getElement(level2)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(getElement(level3)).build().perform();
		Thread.sleep(2000);
		getElement(level4).click();
	}
	
	public void doSendKeysWithPause(By locator, String value, long pauseTime) {
		Actions act = new Actions(driver);
		char[] val = value.toCharArray();
		
		for(char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch))
			.pause(pauseTime).perform();
		}
		
	}
	
	//****************Wait utils*************
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlight(element);
		return element;
	}
	
	public WebElement waitForElementPresence(By locator, long timeout, long pollongTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(pollongTime));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlight(element);
		return element;
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that isgreater than 0.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	//below step annotation is from allure report
	@Step("waiting For Element: {1} within timeout {1} ")
	public WebElement waitForElementVisible(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlight(element);
		return element;
	}
	
	public WebElement waitForElementVisible(By locator, long timeout, long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(pollingTime));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlight(element);
		return element;
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeout
	 */
	public void clickElementWhenReady(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		} catch(TimeoutException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible. 
	 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		
		try {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}catch(TimeoutException e) {
			return Collections.emptyList();
		}
	}
	
	public String waitForTitleContains(String partialTitle, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		if(wait.until(ExpectedConditions.titleContains(partialTitle))) {
			return driver.getTitle();
		}
		} catch(TimeoutException t) {
			System.out.println("title is not found after" + timeout + "seconds");
		}
		return null;
	}
	
	/**
	 * An expectation for checking the title of a page.
	 * @param partialTitle
	 * @param timeout
	 * @return
	 */
	//below step annotation is from allure report
	@Step("wait For Title Is : {0} within timeout {1} ")
	public String waitForTitleIs(String title, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		if(wait.until(ExpectedConditions.titleIs(title))) {
			return driver.getTitle();
		}
		} catch(TimeoutException t) {
			System.out.println("title is not found after" + timeout + "seconds");
		}
		return null;
	}
	
	public String waitForURLContains(String partialURL, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		if(wait.until(ExpectedConditions.urlContains(partialURL))) {
			return driver.getCurrentUrl();
		}
		} catch(TimeoutException t) {
			System.out.println("URL does not contain" + partialURL + "after" + timeout + "seconds");
		}
		return null;
	}
	
	public String waitForURL(String partialURL, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		if(wait.until(ExpectedConditions.urlToBe(partialURL))) {
			return driver.getCurrentUrl();
		}
		} catch(TimeoutException t) {
			System.out.println("URL does not found after" + timeout + "seconds");
		}
		return null;
	}
	//*************wait for alert**************
	/**
	 * Waits for alert is peasent and switch to alert.
	 * @param timeout
	 * @return
	 */
	public Alert waitForAlert(long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public String getAlertText(long timeout) {
		return waitForAlert(timeout).getText();
	}
	
	public void acceptAlert(long timeout) {
		waitForAlert(timeout).accept();;
	}
	
	public void dismissAlert(long timeout) {
		waitForAlert(timeout).dismiss();;
	}
	
	public void alertSendKeys(String text, long timeout) {
		waitForAlert(timeout).sendKeys(text);
	}
	
	public void waitForAlertUsingFluentfeatures( long timeout, long pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoAlertPresentException.class)
				.withMessage("element is not found");
		
		wait.until(ExpectedConditions.alertIsPresent());
	}
	//*************wait for frame**************
	public void waitForFrameByLocatorAndSwitchToIt(By frameLocator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameByIndexAndSwitchToIt(int frameIndex, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public void waitForFrameByLocatorAndSwitchToIt(String frameIdOrName, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdOrName));
	}
	
	public void waitForFrameByLocatorAndSwitchToIt(WebElement frameElement, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	public void waitForFrameByLocatorAndSwitchToItUsingFluentfeatures(By frameLocator, long timeout, long pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchFrameException.class)
				.withMessage("element is not found");
		
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	//***************wait for window********
	public boolean waitForWindow(int numberOfWindows, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		    return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
		}catch(TimeoutException e) {
			System.out.println("number of windows does not match....");
			return false;
		}
	}
	
	public boolean isPageLoaded(long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		String isPageLoaded = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'")).toString();
		return Boolean.parseBoolean(isPageLoaded);
	}
	
	public WebElement waitForElementVisibleUsingFluentFeatures(By locator, long timeout, long pollingTime) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("element is not found");
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	
		
	
}
