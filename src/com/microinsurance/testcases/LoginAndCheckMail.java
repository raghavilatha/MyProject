package com.microinsurance.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.microinsurance.base.TestBase;

public class LoginAndCheckMail {
	//String email=null;
public static WebDriver driver;

VerifySuperUser V=new VerifySuperUser();
    @Test
	public void LoginTest() throws InterruptedException, IOException{
		try{
			TestBase k =TestBase.getTestBaseInstance(); 
		WebDriver driver= new FirefoxDriver();
		driver.get("http://172.30.65.30:1911/miweb/login");
		
		    driver.findElement(By.id("emailId")).sendKeys("superuser@tarangtech.com");
		    
		    driver.findElement(By.id("password")).sendKeys("password");
		    
		    driver.findElement(By.id("captchValue")).sendKeys("qqqq");
		    driver.findElement(By.id("submitButtonId")).click();
		    
		/* List<WebElement> EmailIDs = driver.findElements(By.xpath("//table[@id='offermngmnt']/tbody/tr/td[1]"));
			List<WebElement> Statuses = driver.findElements(By.xpath("//table[@id='offermngmnt']/tbody/tr/td[4]"));
			List<WebElement> Actions = driver.findElements(By.xpath("//table[@id='offermngmnt']/tbody/tr/td[5]"));
			Select select= new Select(driver.findElement(By.name("offermngmnt_length")));
            select.selectByVisibleText("100");
			
		    for(int i=0;i<EmailIDs.size();i++){
		    	if(EmailIDs.get(i).getText().equalsIgnoreCase("raghavilathareddy@gmail.com") && Statuses.get(i).getText().equalsIgnoreCase("Active")){
		    	System.out.println(EmailIDs.get(i).getText() +".........." + Statuses.get(i).getText());
				WebElement cell= Actions.get(i);
				cell.findElement(By.linkText("Action")).click();
				cell.findElement(By.linkText("Suspend")).click();
				driver.findElement(By.xpath("//*[@id='reasontxt']")).sendKeys("fdfdfdfd");
				driver.findElement(By.xpath("//*[@id='myModal']/div/div[2]/a")).click();
				System.out.println(driver.findElement(By.tagName("body")).getText());
				
				break;
		    
			}}*/
			/* for(int i=1; i<EmailIDs.size();i++) {
		     if(Statuses.get(i).getText().equalsIgnoreCase("Active")){
				   WebElement cell= Actions.get(i);
				   cell.findElement(By.linkText("Action")).click();
				   cell.findElement(By.linkText("Edit")).click();
				   if(driver.findElement(By.xpath("//*[@id='isMoneyCollectionAgent']")).isSelected())
				   //driver.findElement(By.xpath("//*[@id='isMoneyCollectionAgent']")).click();
					Assert.assertTrue(driver.findElement(By.xpath("//*[@id='isMoneyCollectionAgent']")).isSelected());
				    String href=driver.findElement(By.xpath("//*[@id='uploadiv']/a")).getAttribute("href");  
				    System.out.println(href);
				    
				    String command="\"C:/Users/avudurir/Downloads/Download.exe\""+" "+href;  
				    System.out.println("command is "+command);  
				      ArrayList<String> argList = new ArrayList<String>();  
				      argList.add(href);  
				        
				      //Running the windows command from Java  
				      Runtime.getRuntime().exec(command);       
				   Assert.assertNotNull(driver.findElement(By.id("email")).getAttribute("readonly"));
				   //Assert.assertFalse(driver.findElement(By.id("email")).isEnabled());
				    //Assert.assertFalse(driver.findElement(By.id("isMoneyCollectionAgent")).isEnabled());
			
				    		
                   driver.findElement(By.xpath("//*[@id='editUserButtonId']")).click();
                  // Assert.assertFalse(driver.findElement(By.xpath("//*[@id='prodmgtdiv']/label[1]")).isEnabled());
					
	                
		     	 //
		    
		   
		    //VerifySuperUser V=new VerifySuperUser();
		    Hashtable<String, String> data= new Hashtable<String, String> ();
		   
		   @Test(dataProvider = "getCheckerData")
		   executeKeywords("addChecker", data);   
		   
		    //table1.put(key, value)
		    //table1.put(xls.getCellData("Test Data", cNum, colStartRowNum),xls.getCellData("Test Data", cNum, rNum));
			
		    */
		    
		}
		
		catch(StaleElementReferenceException e){
			System.out.println(e.getMessage());
			}
		
 
/*@DataProvider
public Object[][] getCheckerData() {
	return TestUtil.getData("addChecker", Keywords.xls);
	
}*/

}
}	
						
				
					
			
			 
				  
				   
			 
	
		   


