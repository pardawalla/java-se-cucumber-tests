import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.lang.System;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class Tests {
    
    
    //public WebDriver object so that it can be used anywhere within class1
    public WebDriver driver; 
    WebDriverWait wait;
    
    @BeforeClass
    public void Setup()
    {
        Long timeOutInMilliseconds = (long) 500;
        System.setProperty("webdriver.chrome.driver", "/Users/hussain/repos/java-se-cucumber-tests/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofMillis(timeOutInMilliseconds));
    }

    @AfterSuite
    public void TearDown()
    {
        driver.quit();
    }
	
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
		driver.get("https://www.trademe.co.nz/");
	    driver.findElement(By.linkText("Motors")).click();
        String expectedTitle = "Cars And Vehicles For Sale | Trade Me Motors";
        wait.until(ExpectedConditions.titleContains(expectedTitle));
	    String originalTitle = driver.getTitle();
	    Assert.assertEquals(originalTitle, expectedTitle);

        driver.findElement(By.linkText("Used")).click();
        //driver.findElement(By.id("tg-517106a7-79e9-51dd-9bf6-765f33bfec15" )).click();
        WebElement keywords =  driver.findElement(By.name("keyword"));
        keywords.sendKeys("blue");
        WebElement make = driver.findElement(By.name("selectedMake"));
        WebElement model = driver.findElement(By.name("searchParams.model"));
        WebElement bodyStyle = driver.findElement(By.className("tm-motors-search-bar__dropdown-multi-select-text"));


        Reporter.log("Testing so far");

    } 

}
