package com.Qa.openCart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Qa.openCart.base.BaseTest;
import com.Qa.openCart.constants.AppConstants;
import com.Qa.openCart.constants.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//below are allure report annotations
@Epic("GCO-23418: design login page for open cart")
@Feature("feature 50: Login Page Feature")
@Story("US 101: design the varoius features of open cart login page")
@Owner("Pavan Patil")
public class LoginPageTest extends BaseTest{
	
	//below @Description and @Severity annotations are allure report annotations
	@Description("checking login page title...")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);
		//Assert.assertEquals(actualTitle, "Account Login", "===title is not matched===");
	}
	
	//below @Description and @Severity annotations are allure report annotations
	@Description("checking login Page URL Test")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageURLTest() {
		String actualurl = loginPage.getLoginPageURL();
		Assert.assertTrue(actualurl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND);
		//Assert.assertTrue(actualurl.contains("route=account/login"), "==URL is not matched");
	}
	
	//below @Description and @Severity annotations are allure report annotations
	@Description("checking forgot Pwd Link")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist(), AppError.ELEMENT_NOT_FOUND);
	}
	
	@Test(priority = Integer.MAX_VALUE)
	public void doLogin() {
		//String accPageTitle = loginPage.doLogin("septbatch2024@open.com","Selenium@12345");
		String accPageTitle = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(accPageTitle, AppConstants.HOME_PAGE_TITLE , AppError.TITLE_NOT_FOUND);
	}
	
	
	@Test(description = "checking logo on home page")
	public void logoTest() {
		Assert.assertTrue(commonsPage.isLogoDisplayed(), AppError.LOGO_NOT_FOUND);
	}
	
	@DataProvider
	public Object[][] getFooterData(){
		return new Object[][] {
			{"About Us"},
			{"Contact Us"},
			{"Specials"},
			{"Order History"}
		};
	}
	
	//below @Description and @Severity annotations are allure report annotations
	@Description("checking footers Link")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "getFooterData", description = "checking important footer links on home page", enabled = true)
	public void footerTest(String footerLink) {
		Assert.assertTrue(commonsPage.checkFooterLink(footerLink));
	}
	

}
