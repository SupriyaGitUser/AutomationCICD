package test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.Cart;
import pages.CheckOut;
import pages.LandingPage;
import pages.Orders;
import pages.OrdersPage;
import pages.ProductCatalogue;
import testComponents.BaseTest;
import testComponents.Retry;

public class SubmitOrderTest extends BaseTest{
	
	String productName = "ZARA COAT 3";
	
	@Test(dataProvider="getData",groups="purchase",retryAnalyzer=Retry.class)
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{
		
		ProductCatalogue prodCat = landingPage.loginApplication(input.get("email"),input.get("password"));
		prodCat.addToCart(input.get("product"));		
		Cart cart = prodCat.clickCart();
		Boolean match =cart.findProducts(input.get("product"));
		Assert.assertTrue(match);		
		CheckOut checkout = cart.checkout();
		checkout.selectCountry();		
		Orders orders = checkout.clickPlaceOrder();
		orders.verifyThankYou();
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void verifyOrderHistory()
	{
		ProductCatalogue prodCat = landingPage.loginApplication("supriya@test1.com", "Welcome1@");
		OrdersPage orderPage = prodCat.clickOrders();
		Boolean match = orderPage.findOrders(productName);
		Assert.assertTrue(match);
	}
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{

		List<HashMap<String,String>> data =	getJsonDataToMap("C:\\Users\\supri\\eclipse-workspace\\SeleniumFrameworkDesign\\src\\data\\purchase.json");		
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}

	
	
	/*@DataProvider
	public Object[][] getData()
	{
		return new Object[][] {{"supriya@test1.com", "Welcome1@","ZARA COAT 3"},{"supriya@test1.com", "Welcome1@","ADIDAS ORIGINAL"}};
	}*/
	
	/*
	 * 		HashMap<String,String> map = new HashMap<String,String>();
		map.put("email", "supriya@test1.com");
		map.put("password", "Welcome1@");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("email", "supriya@test1.com");
		map1.put("password", "Welcome1@");
		map1.put("product", "ADIDAS ORIGINAL");
		
	 */

}
