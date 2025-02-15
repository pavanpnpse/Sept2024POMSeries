package com.Qa.openCart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Qa.openCart.base.BaseTest;
import com.Qa.openCart.constants.AppConstants;
import com.Qa.openCart.constants.AppError;

public class HomePageTest extends BaseTest{
	
	@BeforeClass
	public void homePageSetup() {
		//loginPage.doLogin("septbatch2024@open.com","Selenium@12345");
		loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	
	@Test
	public void homePageTitleTest() {
		//loginPage.doLogin("septbatch2024@open.com","Selenium@12345");
		//Assert.assertEquals(homePage.getHomePageTitle(), "My Account", "==title is not matched==");
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstants.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
	}
	
	@Test
	public void homePageURLTest() {
		//loginPage.doLogin("septbatch2024@open.com","Selenium@12345");
		Assert.assertTrue(homePage.getHomePageURL().contains(AppConstants.HOME_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND);
	}
	
	@Test
	public void logoutLinkExistTest() {
		Assert.assertTrue(homePage.isLogoutLinkExist(), AppError.ELEMENT_NOT_FOUND);
	}
	
	@Test
	public void headersTest() {
		List<String> actualHeaders = homePage.getHeadersList();
		System.out.println("home page headers list===" + actualHeaders);
	}
	
	@DataProvider
	public Object[][] getSearchData() {
		return new Object[][] {
			{"macbook", 4},
			{"imac", 1},
			{"samsung", 2},
			{"canon", 1},
			{"airtel", 0}
			//added exepected value = 4 to deliberately fail a case to test attach screenshot in report.
			//if you want to pass this case then updated expected value = 3
		};
	}
	
	@Test(dataProvider="getSearchData")
	public void searchTest(String searchKey, int resultCount) {
		homePage.doSearch(searchKey);
		Assert.assertEquals(searchResultsPage.getProductResultsCount(), resultCount);
	}
}
