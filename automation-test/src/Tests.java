import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.lang.System;



public class Tests {
    
    
    //public WebDriver object so that it can be used anywhere within class1
    public WebDriver driver; 
    
    @BeforeClass
    public void Setup()
    {
        System.setProperty("webdriver.chrome.driver", "/Users/hussain/repos/java-se-cucumber-tests/drivers/chromedriver");
        driver = new ChromeDriver();
    }

    // @AfterSuite
    // public void TearDown()
    // {
    //     driver.quit();
    // }
	
	@Test (priority = 0)
	public void OpenBrowser() 
    {
		Reporter.log("This test verifies the current selenium compatibility with TestNG by launching the chrome driver"); 
        Reporter.log("Launching Google Chrome Driver version 108.0.5359.124 for this test"); 
        driver.get("https://www.trademe.co.nz/");
	    Reporter.log("Using https://www.trademe.co.nz/ for testing", true);
	    String expectedTitle = "Buy & Sell on NZ's #1 Auction & Classifieds Site | Trade Me";
	    String originalTitle = driver.getTitle();
	    Assert.assertEquals(originalTitle, expectedTitle);
    } 

    @Test (priority = 1)
	public void TradmeMotors() 
    {
		Reporter.log("This test verifies the current selenium compatibility with TestNG by launching the chrome driver");
	    driver.findElement(By.linkText("Motors")).click();
    } 

}
