import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Function;

import java.lang.System;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



public class Tests {
    
    
    //public WebDriver object so that it can be used anywhere within class1
    public WebDriver driver; 
    WebDriverWait wait;

    private void waitUntilSelectOptionsPopulated(final Select select)
    {
        new FluentWait<WebDriver>(driver)
            .withTimeout(Duration.ofSeconds(60))
            .pollingEvery(Duration.ofMillis(10))
            .until(new Function<WebDriver, Boolean>() 
            {
                public Boolean apply(WebDriver d) 
                {
                    return (select.getOptions().size() > 1);
                }

            });
    }
    
    @BeforeClass
    public void Setup()
    {
        Long timeOutInMilliseconds = (long) 500;
        System.setProperty("webdriver.chrome.driver", "/Users/hussain/repos/java-se-cucumber-tests/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofMillis(timeOutInMilliseconds));
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
		driver.get("https://www.trademe.co.nz/");
	    driver.findElement(By.linkText("Motors")).click();
        String expectedTitle = "Cars And Vehicles For Sale | Trade Me Motors";
        wait.until(ExpectedConditions.titleContains(expectedTitle));
	    String originalTitle = driver.getTitle();
	    Assert.assertEquals(originalTitle, expectedTitle);

    }


    @Test (priority = 2)
	public void SearchUsedCars()
    {
        driver.get("https://www.trademe.co.nz/a/motors");
        String expectedTitle = "Cars And Vehicles For Sale | Trade Me Motors";
        wait.until(ExpectedConditions.titleContains(expectedTitle));
        // Select Used Cars
        driver.findElement(By.linkText("Used")).click();
        // Enter blue for keywords
        WebElement keywords =  driver.findElement(By.name("keyword"));
        keywords.sendKeys("blue");
        // select Honda as the Make 
        WebElement make = driver.findElement(By.name("selectedMake"));
        Select makeChosen = new Select(make);
        makeChosen.selectByVisibleText("Honda");

        // Select Civic as the Model     
        WebElement model = driver.findElement(By.name("searchParams.model"));
        Select modelChosen = new Select(model);
        waitUntilSelectOptionsPopulated(modelChosen);
        modelChosen.selectByVisibleText("Civic");

        // Select Body Types
        WebElement bodyStyle = driver.findElement(By.className("tm-motors-search-bar__dropdown-multi-select-text"));
        bodyStyle.click();
        driver.findElement(By.xpath("//span[text()=' Hatchback ']")).click();
        driver.findElement(By.xpath("//span[text()=' Coupe ']")).click();
        bodyStyle.click();

        driver.findElement(By.xpath("//button[@type='submit']")).click();


        // driver.findElement(By.xpath("(//div[@class='tm-motors-search-card__body'])[3]")).click();
        // driver.findElement(By.xpath("(//div[@class='tm-motors-search-card__body-modelDetail ng-star-inserted'])[1]")).click();
        
        wait.until(ExpectedConditions.textToBe(By.xpath("//h3[@class='tm-search-header-result-count__heading ng-star-inserted']"), "Showing 18 results for 'blue'"));
        String myText = driver.findElement(By.xpath("//h3[@class='tm-search-header-result-count__heading ng-star-inserted']")).getText();
        System.out.println(myText);


        Reporter.log("Testing so far");

    } 

}
