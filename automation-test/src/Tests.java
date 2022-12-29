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
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



public class Tests {
    
    
    // Used by multiple methods in the class
    private WebDriver driver; 
    private WebDriverWait wait;
    private Duration timeoutInSeconds = Duration.ofSeconds(60);
    private Duration pollingTimeInMilliseconds = Duration.ofMillis(10);
    // Used in Test(s): OpenBrowser
    private String tradmeUrl = "https://www.trademe.co.nz/";
    private String trademeSiteTitle = "Buy & Sell on NZ's #1 Auction & Classifieds Site | Trade Me";
    // Used in Test(s): TrademeMotors
    private String trademeMotorsUrl = "https://www.trademe.co.nz/a/motors";
    private String trademeMotorsSiteTitle = "Cars And Vehicles For Sale | Trade Me Motors";
    // Used in Test(s): SearchUsedCars
    private String keywordsText = "blue";
    private String makeText = "Honda";
    private String modelText = "Civic";
    private List<String> bodyStyleText = List.of(" Hatchback ", " Coupe ");
    private String searchResultHeader = "Honda Civic for sale"; 
    // Used in Test(s): SearchUsedCars, ExistingListing
    private String existingListingUrl = "https://www.trademe.co.nz/a/motors/cars/honda/civic/listing/3900249796";



    private void waitUntilSelectOptionsPopulated(final Select select)
    {
        new FluentWait<WebDriver>(driver)
            .withTimeout(timeoutInSeconds)
            .pollingEvery(pollingTimeInMilliseconds)
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
        System.setProperty("webdriver.chrome.driver", "/Users/hussain/repos/java-se-cucumber-tests/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, timeoutInSeconds);
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
        driver.get(tradmeUrl);
	    Reporter.log("Using https://www.trademe.co.nz/ for testing", true);
	    String actualTitle = driver.getTitle();
	    Assert.assertEquals(actualTitle, trademeSiteTitle);
    } 

    @Test (priority = 1)
	public void TradmeMotors() 
    {
		driver.get(trademeMotorsUrl);
	    driver.findElement(By.linkText("Motors")).click();
        wait.until(ExpectedConditions.titleContains(trademeMotorsSiteTitle));
	    String actualTitle = driver.getTitle();
	    Assert.assertEquals(actualTitle, trademeMotorsSiteTitle);

    }


    @Test (priority = 2)
	public void SearchUsedCars()
    {
        driver.get(trademeMotorsUrl);
        wait.until(ExpectedConditions.titleContains(trademeMotorsSiteTitle));
        // Select Used Cars
        driver.findElement(By.linkText("Used")).click();
        // Enter blue for keywords
        WebElement keywords =  driver.findElement(By.name("keyword"));
        keywords.sendKeys(keywordsText);
        // select Honda as the Make 
        WebElement make = driver.findElement(By.name("selectedMake"));
        Select makeChosen = new Select(make);
        makeChosen.selectByVisibleText(makeText);

        // Select Civic as the Model     
        WebElement model = driver.findElement(By.name("searchParams.model"));
        Select modelChosen = new Select(model);
        waitUntilSelectOptionsPopulated(modelChosen);
        modelChosen.selectByVisibleText(modelText);

        // Select Body Types
        WebElement bodyStyle = driver.findElement(By.className("tm-motors-search-bar__dropdown-multi-select-text"));
        // Expand the Body Style drop-down box
        bodyStyle.click();
        // Select all the options needed
        for (String tmpStr : bodyStyleText)
        {
            String xpathStr = "//span[text()='" + tmpStr + "']";
            System.out.println(xpathStr);
            driver.findElement(By.xpath(xpathStr)).click();
        }
        // driver.findElement(By.xpath("//span[text()=' Hatchback ']")).click();
        // driver.findElement(By.xpath("//span[text()=' Coupe ']")).click();
        // Close the Body Style drop-down box
        bodyStyle.click();

        // Click the Search button
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        String headerText = driver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals(headerText, searchResultHeader);

        Reporter.log("Testing so far");

        // Select the 3rd option
        //TO-DO confirm results are always returned
        driver.findElement(By.xpath("(//div[@class='o-card']//a)[3]")).click();
        wait.until(ExpectedConditions.urlToBe(existingListingUrl));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, existingListingUrl);
       
    } 

    // Used in Test(s): ExistingListing
    private String numberPlateExpected = "MNE951";
    private String kilometersExpected = "36,698km";
    private String bodyExpected = "Hatchback";
    private String seatsExpected = "5";
    @Test (priority = 3)
	public void ExistingListing()
    {
		driver.get(existingListingUrl);
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='tm-motors-vehicle-attributes__tag--content'])[1]")));
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='o-button2--primary o-button2']")));
        
        WebElement numberPlate = driver.findElement(By.xpath(("(//span[@class='o-tag__content']//div)[7]")));
        String actualNumberPlate = numberPlate.getText();
        System.out.println(actualNumberPlate);
        Assert.assertTrue(actualNumberPlate.contains(numberPlateExpected));

        WebElement vehicleOdometer = driver.findElement(By.xpath(("(//span[@class='o-tag__content']//div)[1]")));
        String actualKilometers = vehicleOdometer.getText().trim();
        System.out.println(actualKilometers);
        Assert.assertEquals(actualKilometers, kilometersExpected);

        WebElement body = driver.findElement(By.xpath(("(//span[@class='o-tag__content']//div)[2]")));
        String actualBody = body.getText().trim();
        System.out.println(actualBody);
        Assert.assertEquals(actualBody, bodyExpected);

        WebElement seats = driver.findElement(By.xpath(("(//span[@class='o-tag__content']//div)[3]")));
        String actualSeats = seats.getText().trim();
        System.out.println(actualSeats);
        Assert.assertEquals(actualSeats, seatsExpected);
    }

}