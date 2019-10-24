/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autotest2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
/**
 *
 * @author Billion Shiferaw
 */
public class Autotest2 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        facebookFriendsToCls();
    }
    public static void facebookFriendsToCls() throws FileNotFoundException{
        
        /*
            setting up the chrome driver signing in and going to profile and then friends
        */

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
        try {Thread.sleep(2000);} catch (Exception e) {}  
        driver.get("https://facebook.com");
        
        //wait untill the page loads
        try {Thread.sleep(2000);} catch (Exception e) {}  
        
        //find and fill in username and password fields
        WebElement username = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("pass"));
        WebElement Login = driver.findElement(By.id("u_0_q"));
        username.sendKeys("yourusername");
        password.sendKeys(getPass());
        Login.click();
        
        //wait until page loads
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
        
        /*
          Finiding and fetching data of friends  
        */

        List<WebElement> totalFriendName;
        List<WebElement> totalFriendemail;
        List<WebElement> totalFriendphone;
        List<WebElement> totalFriendsOfFriends;

        //totalFriendName
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
            try{Thread.sleep(3000);}catch(Exception e){}
            WebElement friendsOfThisUser = driver.findElement(By.xpath("//a[@data-tab-key='Friends']"));
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
        
        //update the datalist
        String[][] dataArray = new String[200][4];
        
        for (int i = 0; i < totalFriendName.size(); i++) {
            
            dataArray[i][0] = totalFriendName.get(0).getText();
            dataArray[i][1] = totalFriendemail.get(0).getText();
            dataArray[i][2] = totalFriendphone.get(0).getText();
            dataArray[i][3] = totalFriendsOfFriends.get(0).getText();
            
        }
        
        //make a cls document
        Workbook wb = new HSSFWorkbook();
        FileOutputStream fileOut = new FileOutputStream("facebookToXls.xls");
        

        CreationHelper createHelper = wb.getCreationHelper();
        org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet("friendsList");

        
        Row row1 = sheet.createRow((short) 0);  // Create the first Row
        
        row1.createCell(0).setCellValue(
        createHelper.createRichTextString("Name"));  // inserting first row cell value
        
        row1.createCell(1).setCellValue(
        createHelper.createRichTextString("E-mail"));

        row1.createCell(3).setCellValue(
        createHelper.createRichTextString("Phone No"));

        row1.createCell(4).setCellValue(
        createHelper.createRichTextString("No of Friends"));

        //update the cls fields to reflect fetched data
        for (int i = 1;i < totalFriendName.size(); i++) {
            int j = i - 1;
            String name = dataArray[j][0];
            String email = dataArray[j][1];
            String phoneNo = dataArray[j][2];
            String numberOfFriends = dataArray[j][3];

            Row row = sheet.createRow((short) i);
            row.createCell(0).setCellValue(
            createHelper.createRichTextString(name));
            row.createCell(3).setCellValue(
            createHelper.createRichTextString(email));
            row.createCell(2).setCellValue(
            createHelper.createRichTextString(phoneNo));
            row.createCell(2).setCellValue(
            createHelper.createRichTextString(numberOfFriends));    

        }
        

        // Write the output to a file
        try {
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(Autotest2.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    public static String getPass()
    {
        Scanner sc = new Scanner(System.in);
        String password = sc.nextLine();
        return password;
    }
}

