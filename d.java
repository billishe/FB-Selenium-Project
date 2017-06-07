package autotest2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.DesiredCapabilities;


import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Autotest2 {


    public static void main(String[] args) throws IOException {
        facebookFriendsToCls();
    }

    public static void facebookFriendsToCls()
    {
         //set system property to work on chromedriver
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        
        //add and enable ultrasurf extension
        ChromeOptions options = new ChromeOptions();
        options.addArguments("load-extension=/Users/carecorner/Library/Application Support/Google/Chrome/Default/Extensions/mjnbclmflcpookeapghfhapeffmpodij/1.3.0_0");
        DesiredCapabilities surfEasy = new DesiredCapabilities();
        surfEasy.setCapability(ChromeOptions.CAPABILITY, options);
        
        //open webdriver with ultrasurf VPN
        WebDriver driver = new ChromeDriver(surfEasy);
        driver.manage().window().maximize();
        driver.get("https://facebook.com");
        
        //wait untill the page loads
        try {
            Thread.sleep(2000);   
        } catch (Exception e) {
            
        }  
        
        //find and fill in username and password fields
        WebElement username = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("pass"));
        WebElement Login = driver.findElement(By.id("u_0_q"));
        username.sendKeys("billishe");
        password.sendKeys(getPass());
        Login.click();
        
        //weait until page loads
        try {
            Thread.sleep(7000);   
         } catch (Exception e) {
        }        
        
        //goto profile 
        WebElement profile = driver.findElement(By.xpath("//*[@id=\"u_0_4\"]/div[1]/div[1]/div/a"));
        if(profile.isEnabled() && profile.isDisplayed()) {
            profile.click();
        }
        else {
            System.out.println("Element not found");
        }
        //wait untill page loads
        try {
            Thread.sleep(2000);   
         } catch (Exception e) {
        } 
        
        //goto friends list
        WebElement friends = driver.findElement(By.xpath("//a[text()='Friends']"));
        friends.click();
        
        //scroll down to the last friend
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0,1233)");   
        
        //update the datalist
        
        
        //make a cls document
        
        
        //update the cls fields to reflect fetched data
        
        
        //save and close file 
        
        
        
        
    }
}