package autotest2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
        try {
            Thread.sleep(2000);   
         } catch (Exception e) {
        } 
        //scroll down to the last friend
        List<WebElement> totalFriendName;
        while(true){
            List<WebElement> friendName = driver.findElements(By.xpath("//div[@class='fsl fwb fcb']/a"));
            if(friendName.size() == 0){continue;}
            System.out.println("total friends: "+ friendName.size());
            
            
            try {
                Thread.sleep(10000);   
            } catch (Exception e) {
            }

            WebElement currentLast =  friendName.get(friendName.size() - 1);
            
            int yCoordinate = currentLast.getLocation().y;
               
            
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollTo(0,"+yCoordinate+")");  
            
            
            totalFriendName = driver.findElements(By.xpath("//div[@class='fsl fwb fcb']/a"));
            if(totalFriendName.size() == 40){ break; }
        }
        for(WebElement name : totalFriendName)
        {
            //go to friend profile
            String nameF = name.getText();
            WebElement q = driver.findElement(By.xpath("//input[@name='q']"));
            q.sendKeys(nameF);
            q.submit();
            try{Thread.sleep(3000);}catch(Exception e){}
            driver.findElement(By.xpath("//a[@class='profileLink']")).click();
            
            
            //fetch number of friends
            WebElement friendsOfThisUser = driver.findElement(By.xpath("//a[@text()='Friends']"));
            String x = friendsOfThisUser.getText();
            System.out.println(x);
            
            //fetch email
            driver.findElement(By.xpath("//a[@text()=''About]")).click();
            
            //fetch phone number
            
            
            //go back to my friends list
            driver.get("https://facebook.com");
            
            //wait untill the page loads
            try {
                Thread.sleep(2000);
            } catch (Exception e) {

            }
            
            //wait until page loads
            try {
                Thread.sleep(7000);
            } catch (Exception e) {
            }

            //goto profile 
            if (profile.isEnabled() && profile.isDisplayed()) {
                profile.click();
            } else {
                System.out.println("Element not found! Signing in again...");
                username.sendKeys("billishe");
                password.sendKeys(getPass());
                Login.click();
                
                try {
                Thread.sleep(7000);
                } catch (Exception e) {}
                profile.click();
            }
        }
    }
}
