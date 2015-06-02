package com.microinsurance.base;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

import com.microinsurance.util.ErrorUtil;
import com.microinsurance.util.Xls_Reader;
public class TestBase {
	
	public static List<WebElement> Statuses = null;
	public static List<WebElement> EmailIDs = null;
	public static List<WebElement> Actions = null;
	public static Xls_Reader xls =new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\microinsurance\\xls\\Test Suite1.xlsx"); 
	static TestBase k=null;
	public Properties CONFIG=null;
	public Properties OR=null;
	public Properties SQL=null;
	public static WebDriver driver= null;
	public static Logger APP_LOGS = null;
	public Connection connection = null;
	public Statement stmt = null;
	public List<String> emails=null;
	public static boolean isBrowserOpened = false;
	public static String Email=null;
	public static String email;
	Hashtable<String, String> data=new Hashtable<String, String> ();
	
	private TestBase(){
		System.out.println("Initializing Keywords");
		// initialize properties files
		try {
			// config
			APP_LOGS= Logger.getLogger("devpinoyLogger");
			CONFIG = new Properties();
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\microinsurance\\config\\config.properties");
			CONFIG.load(fs);
			// OR
			OR = new Properties();
			fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\microinsurance\\config\\OR.properties");
			OR.load(fs);
			
			SQL = new Properties();
			fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\microinsurance\\config\\sql.properties");
			SQL.load(fs);
			//xls =new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\microinsurance\\xls\\Test Suite1.xlsx");

		} catch (Exception e) {
			// Error is found
			e.printStackTrace();
		}
	}
	
	
	
	
	public String openBrowser(String browserType) {
		APP_LOGS.debug("Executing openBrowser");
		if (!isBrowserOpened) {
			if (CONFIG.getProperty("browserType").equals("Mozilla"))
				driver = new FirefoxDriver();
			else if (CONFIG.getProperty("browserType").equals("IE"))
				driver = new InternetExplorerDriver();
			else if (CONFIG.getProperty("browserType").equals("CHROME"))
				driver = new ChromeDriver();
			isBrowserOpened = true;

			driver.manage().window().maximize();
			final String waitTime = CONFIG.getProperty("default_implicitWait");
			driver.manage().timeouts().implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
			}
		return "Pass";
		}
	
	
	public String clear(String identifier) {
		APP_LOGS.debug("Executing navigate");
		try {
			if (identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(OR.getProperty(identifier))).clear();
			else if (identifier.endsWith("_id"))
				driver.findElement(By.id(OR.getProperty(identifier))).clear();
			else if (identifier.endsWith("_name"))
				driver.findElement(By.name(OR.getProperty(identifier))).clear();
		} catch (Exception e) {
			return "Fail - not able to clear";
		}
		k.log("Pass");
		return "Pass";
	}

	
	
	public  String navigate(String URLKey) {
		APP_LOGS.debug("Executing navigate");
		try {
			driver.get(CONFIG.getProperty(URLKey));
		} catch (Exception e) {
			return "Fail - not able to navigate";
		}
		k.log("Pass");
		return "Pass";

	}
	
	public String click(String identifier) {
		APP_LOGS.debug("Executing click");
		try {
			if (identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(OR.getProperty(identifier))).click();
			else if (identifier.endsWith("_id"))
				driver.findElement(By.id(OR.getProperty(identifier))).click();
			else if (identifier.endsWith("_name"))
				driver.findElement(By.name(OR.getProperty(identifier))).click();
			else if (identifier.endsWith("_linktext"))
				driver.findElement(By.linkText(OR.getProperty(identifier))).click();
		} catch (NoSuchElementException e) {
			Assert.fail("Element not found - " + identifier);
		}
		k.log("Pass");
		return "Pass";
	}
	
	/**
	 * 
	 * @param identifier
	 * @param data
	 * @return
	 */
	public String input(String identifier, String data) throws NoSuchElementException{
		APP_LOGS.debug("Executing input");
		try {
			if (identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(OR.getProperty(identifier))).sendKeys(data.split("\\.")[0]);
			else if (identifier.endsWith("_id"))
				driver.findElement(By.id(OR.getProperty(identifier))).sendKeys(data.split("\\.")[0]);
			else if (identifier.endsWith("_name"))
				driver.findElement(By.name(OR.getProperty(identifier))).sendKeys(data.split("\\.")[0]);
		} catch (Throwable t) {
			Assert.fail("Element not found - " + identifier);
			ErrorUtil.addVerificationFailure(t);
		}
		k.log("Pass");
		return "Pass";
	}
	
	public String inputKyc(String identifier, String data) {
		APP_LOGS.debug("Executing input");
		try {
			if (identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(OR.getProperty(identifier))).sendKeys(data);
			else if (identifier.endsWith("_id"))
				driver.findElement(By.id(OR.getProperty(identifier))).sendKeys(data);
			else if (identifier.endsWith("_name"))
				driver.findElement(By.name(OR.getProperty(identifier))).sendKeys(data);
		} catch (NoSuchElementException e) {
			Assert.fail("Element not found - " + identifier);
		}
		k.log("Pass");
		return "Pass";
	}
	public String loginbySuperUser() {
		APP_LOGS.debug("Executing input");
		try {
			driver.findElement(By.xpath(OR.getProperty("loginusername_xpath"))).sendKeys(OR.getProperty("adminUsername"));
			driver.findElement(By.xpath(OR.getProperty("loginpassword_xpath"))).sendKeys(OR.getProperty("adminPassword"));
			driver.findElement(By.xpath(OR.getProperty("logincaptaha_xpath"))).sendKeys(OR.getProperty("Captcha"));
			driver.findElement(By.xpath(OR.getProperty("loginbutton_xpath"))).click();
			
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			return "Fail";
		}
		k.log("Pass");
		return "Pass";
	}
	
	/**
	 * 
	 * @param identifier
	 * @param data
	 * @return
	 */
	public String inputEmail(String identifier, String data) {
		APP_LOGS.debug("Executing input");
		try {
			if (identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(OR.getProperty(identifier))).sendKeys(data);
			else if (identifier.endsWith("_id"))
				driver.findElement(By.id(OR.getProperty(identifier))).sendKeys(data);
			else if (identifier.endsWith("_name"))
				driver.findElement(By.name(OR.getProperty(identifier))).sendKeys(data);
		} catch (NoSuchElementException e) {
			Assert.fail("Element not found - " + identifier);
		}
		k.log("Pass");
		return "Pass";
	}
	
	
	public String inputproperties(String identifier,String data){
		APP_LOGS.debug("Executing inputproperties");
		try{
			if(identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(OR.getProperty(identifier))).sendKeys(OR.getProperty(data));
			else if(identifier.endsWith("_id"))
				driver.findElement(By.id(OR.getProperty(identifier))).sendKeys(OR.getProperty(data));
			else if(identifier.endsWith("_name"))
				driver.findElement(By.name(OR.getProperty(identifier))).sendKeys(OR.getProperty(data));
			}catch(NoSuchElementException e){
				Assert.fail("Element not found - "+identifier);
			}
		k.log("Pass");
		return "Pass";			
	}
	
	public String inputconvertfromdecimal(String identifier,String data){
		APP_LOGS.debug("Executing input");
		try{
			 
			
			BigDecimal bd = new BigDecimal(data);
			System.out.println(bd.longValue()); 
			String converteddata= String.valueOf(bd.longValue());
			if(identifier.endsWith("_xpath"))
				driver.findElement(By.xpath(OR.getProperty(identifier))).sendKeys(converteddata);
			else if(identifier.endsWith("_id"))
				driver.findElement(By.id(OR.getProperty(identifier))).sendKeys(converteddata);
			else if(identifier.endsWith("_name"))
				driver.findElement(By.name(OR.getProperty(identifier))).sendKeys(converteddata);
			
			
				}catch(NumberFormatException e){
				Assert.fail("Element not found - "+identifier);
			}
		k.log("Pass");
		return "Pass";			
	}
	
	
	

/*public String selectDropdown(){
	try{
		Select select= new select(driver.findElement(By.name("offermngmnt_length"))).selectByVisibleText("100");
	}
	catch(Exception e){
		k.log(e.getMessage());
	}
	return "Pass";
}*/
	public void log(String msg){
		APP_LOGS.debug(msg);
	}
	
	public String logout(){
		try{
		driver.findElement(By.cssSelector(OR.getProperty("sudrop"))).click();
	    driver.findElement(By.linkText(OR.getProperty("logout"))).click();
		}catch(Exception e){
			k.log(e.getMessage());
		}
		return "Pass";
	}
	
	
	public String input() {
		APP_LOGS.debug("Executing input");
		try {
			
		
			} catch (NoSuchElementException e) {
			
		}
		k.log("Pass");
		return "Pass";
	}
	
	
	
	
	public String currentTime(){
		Calendar cal = Calendar.getInstance();
		cal.getTime();								
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		String curentTime= sdf.format(cal.getTime());
	    //System.out.println(curentTime);
		return curentTime;
	    }
	
	
	
	/**************************Application dependent****************************/
	public String checkMail(String mailName){
		APP_LOGS.debug("Executing checkMail");

		try{
		driver.findElement(By.linkText(mailName)).click();
		}catch(Exception e){
			return "Fail-Mail not found";
		}
		
		
		k.log("Pass");
		return "Pass";							
	}
	
	
	
	//******Singleton function*******//
	public static TestBase getTestBaseInstance(){
		if(k == null){
			k = new TestBase();
		}
		
		
		return k;
	}
	
	
		// compare titles
		public String compareTitle(final String expectedVal) {
			APP_LOGS.debug("Executing compareTitle Assertion");
			try {
				Assert.assertEquals(driver.getTitle(), OR.getProperty(expectedVal));
			} catch (final Throwable t) {
				ErrorUtil.addVerificationFailure(t);
				APP_LOGS.debug("Titles do not match");
				APP_LOGS.debug("Actual Value is ="+ driver.getTitle());
				return "fail";
			}
			k.log("Pass");
			return "Pass";
		}
		
	
		public List<WebElement> getEmail(String xpathkey) {
		  try{
			   EmailIDs = driver.findElements(By.xpath(xpathkey));}
		       catch(Exception e){
		    	   k.log(e.getMessage());
		    	   }
		  k.log("Pass");
		  // System.out.println(emails);
		  return EmailIDs;
			    
			}
		
		
		public List<WebElement> getStatus(String xpathkey) {
			try{
			Statuses = driver.findElements(By.xpath(xpathkey));}
			catch(Exception e){
				k.log(e.getMessage());
				   }
			 k.log("Pass");
			 return Statuses;
			    }
		
		public List<WebElement> getActions(String xpathkey) {
			try{
				Actions = driver.findElements(By.xpath(xpathkey));
			}catch(Exception e){
				k.log(e.getMessage());
				   }
			 k.log("Pass");
			 return Actions;
			    }
			
		public String verifyStatus(String email, String status){
		try{
			searchEmail(email);
			getEmail(OR.getProperty("emailtable"));
			getStatus(OR.getProperty("statustable"));
		
			   for(int i=0; i<EmailIDs.size();i++) {
					if(EmailIDs.get(i).getText().equals(email) && Statuses.get(i).getText().equals(status)){
						Assert.assertTrue(2>1);
					}}}catch(Exception e){
				// System.out.println(e.getMessage());
						return "Fail";
				   }
			    k.log("Pass");
			    System.out.println(emails);
				return "Pass";
			    
			}
		
		public String verifyActions(String email,String status, String actions){
			try{
				searchEmail(email);
				getEmail(OR.getProperty("emailtable"));
				getStatus(OR.getProperty("statustable"));
				getActions(OR.getProperty("actions"));
				
				for(int i=0; i<EmailIDs.size();i++) {
					if(EmailIDs.get(i).getText().equals(email) && Statuses.get(i).getText().equals(status)){
						WebElement cell= Actions.get(i);
						cell.findElement(By.linkText("Action")).click();
						String cellText= cell.getText().replace("\n", ",");
						System.out.println(cellText);
						Assert.assertTrue(cellText.equalsIgnoreCase(actions));
						//cell.findElement(By.linkText("Action")).click();
						break;
						}
					
					}}catch(Exception e){
							k.log(e.getMessage());
							return "Fail";
					   }
				    k.log("Pass");
				    System.out.println(emails);
					return "Pass";
				    
				}
		
		
		public String searchEmail(String email){
			try{
				driver.findElement(By.xpath("//*[@id='emailId']")).sendKeys(email);
				driver.findElement(By.xpath("//*[@id='searchEmailId']")).click();
				
				}catch(Exception e){
							k.log(e.getMessage());
							k.log("Unable to find the EmailID");
							return "Fail";
					   }
				    k.log("Pass");
				    System.out.println(emails);
					return "Pass";
				    
				}
		
		public String approveUser(String approvingemail) {
		   try{
			   searchEmail(approvingemail);
			   getEmail(OR.getProperty("emailtable"));
			   getStatus(OR.getProperty("statustable"));
			   getActions(OR.getProperty("actions"));
			   for(int i=0;i<EmailIDs.size();i++){
			   if(EmailIDs.get(i).getText().equalsIgnoreCase(approvingemail) && Statuses.get(i).getText().equalsIgnoreCase("Pending")){
				   System.out.println(Actions.get(i).getText());
				   WebElement cell= Actions.get(i);
				   cell.findElement(By.linkText("Action")).click();
				   cell.findElement(By.linkText("Approve")).click();
				   driver.findElement(By.xpath(OR.getProperty("appusernextbutton_xpath"))).click();
				   driver.findElement(By.xpath(OR.getProperty("appComments_xpath"))).sendKeys("Approving the User");
				   driver.findElement(By.xpath(OR.getProperty("appove_xpath"))).click();
				   break;
				   }}}
		   catch(Exception e){
			   return "fail";
			   }
		    k.log("Pass");
		    return "Pass";
		}
		
		
		public String approvePolicy()  {
			   try{
				   getEmail(OR.getProperty("policytable"));
				   for(int i=0;i<EmailIDs.size();i++){
				   
				   getStatus(OR.getProperty("policystatus"));
				   getActions(OR.getProperty("policyactions"));
				   
				   if(Statuses.get(i).getText().equalsIgnoreCase("Pending")){
					   System.out.println(i);
					   System.out.println(Statuses.get(i).getText());
					   //System.out.println(Actions.get(i).getText());
					   
					   WebElement cell= Actions.get(i);
					   cell.findElement(By.linkText("Action")).click();
					   cell.findElement(By.linkText("Approve")).click();
					   driver.findElement(By.xpath("//*[@id='viewPolicyButtonId']")).click();
					   driver.findElement(By.xpath("//*[@id='nextButtonId']")).click();
					   driver.findElement(By.xpath("//*[@id='comments']")).sendKeys("Approving the User");
					   driver.findElement(By.xpath("//*[@id='approvePolicyButtonId']")).click();
					   Thread.sleep(500);
					   inputproperties("recordsperpage_policy","entries");
		
					   }}}
			   catch(Throwable t){
				   System.out.println(t.getMessage());
				   ErrorUtil.addVerificationFailure(t);
				   return "fail";
				   
				   }
			    k.log("Pass");
			    return "Pass";
			}
		/**
		 * 
		 * @param status
		 * @return
		 */
		public String UpdateRecord(String sql, String status) {
			   try{
				   String email=executeQuery(sql);
				   searchEmail(email);
				   getEmail(OR.getProperty("emailtable"));
				   getStatus(OR.getProperty("statustable"));
				   getActions(OR.getProperty("actions"));
				   
				   for(int i=0;i<EmailIDs.size();i++){
				   if(EmailIDs.get(i).getText().equalsIgnoreCase(email) && Statuses.get(i).getText().equalsIgnoreCase(status)){
					   System.out.println(Actions.get(i).getText());
					   WebElement cell= Actions.get(i);
					   cell.findElement(By.linkText("Action")).click();
					   cell.findElement(By.linkText("Edit")).click();
					   break;
					   }}}
			   catch(Exception e){
				   return "fail";
				   }
			    k.log("Pass");
			    return "Pass";
			}
		
		
		public String suspendRecord(String sql, String status) {
			   try{
				   String email=executeQuery(sql);
				   //Email= email;
				   searchEmail(email);
				   getEmail(OR.getProperty("emailtable"));
				   getStatus(OR.getProperty("statustable"));
				   getActions(OR.getProperty("actions"));
				   for(int i=0;i<EmailIDs.size();i++){
				   if(EmailIDs.get(i).getText().equalsIgnoreCase(email) && Statuses.get(i).getText().equalsIgnoreCase(status)){
					   System.out.println(Actions.get(i).getText());
					   WebElement cell= Actions.get(i);
					   cell.findElement(By.linkText("Action")).click();
					   cell.findElement(By.linkText("Suspend")).click();
					   Thread.sleep(1000);
					   driver.findElement(By.xpath("//*[@id='reasontxt']")).sendKeys("fdfdfdfd");
					   break;
					   }}
				   Email= email;
				   }
			   catch(Exception e){
				   return "fail";
				   }
			    k.log("Pass");	
			    return "Pass";
			}
		
	/**
	 * 
	 * @param sql
	 * @param email
	 * @return
	 */
	public String dbconnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("MySQL JDBC Driver Registered!");
			connection = DriverManager.getConnection("jdbc:mysql://172.30.65.30:3306/MI_DB", "testing","Tarang01");
			if (connection != null) {
				System.out.println("You made it, take control your database now!");
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find the database driver "+ e.getMessage());
			return "fail";
			} catch (SQLException e) {
			System.out.println("Could not connect to the database "+ e.getMessage());
			return "fail";
			}
		return "Pass";
		}
	
	public String closeConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if (connection != null) {
				connection.close();
				}} catch (ClassNotFoundException e) {
			System.out.println("Could not find the database driver "+ e.getMessage());
			return "fail";
			} catch (SQLException e) {
			System.out.println("Could not connect to the database "+ e.getMessage());
			return "fail";
			}
		return "Pass";
		}
	
	
	
	public String updatePasswordthroughDb(String sql, String email) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("MySQL JDBC Driver Registered!");
			dbconnection();
			PreparedStatement ps = connection.prepareStatement(SQL.getProperty(sql));
			ps.setString(1, email);
			ps.execute();
			System.out.println("Merchant Admin got created");
			closeConnection();
			} catch (ClassNotFoundException e) {
			System.out.println("Could not updatePassword "+ e.getMessage());
			return "fail";
			} catch (SQLException e) {
			System.out.println("Could not connect to the database "+ e.getMessage());
			return "fail";
			}
		return "Pass";
		}
	
    /**
     * 
     * @param sql1
     * @return 
     */
	public String executeQuery(String sql1) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbconnection();
			PreparedStatement ps = connection.prepareStatement(SQL.getProperty(sql1));
			ResultSet rs = ps.executeQuery();
			emails = new ArrayList<String>();
			while (rs.next()) {
				String emailid = rs.getString("EMAIL");
				System.out.println(emailid);
				emails.add(emailid);
				//System.out.println(emailid);
				email = emails.get(0);
				connection.close();
				
			}
			} catch (SQLException e) {
				k.log("Could not connect to the database " + e.getMessage());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();}
		
		//System.out.println(email);
		return email;

	}
	
	
	
	public String isUserExists(String sql){
		APP_LOGS.debug("Executing verifyText");
		try{
			if(executeQuery(sql).isEmpty()){
				
			if(sql.equalsIgnoreCase("getPendingUser")){
				
				
				/*@Test(dataProvider = "getCheckerData")
			    executeKeywords("addChecker", data);*/}
			else if(sql.equalsIgnoreCase("getActiveUser")){
				createActiveUser();
			}
			
			
			}}catch(Exception e){
				k.log("Fail");
				System.out.println(e.getMessage());
				}
		k.log("Pass");
		return "Pass";	
	}
	
	
	private void createActiveUser() {
		// TODO Auto-generated method stub
		
	}


	private void createMoneyCollectionAgent() {
		// TODO Auto-generated method stub
		
	}


	/*Assertion functions*/
	public String verifySuccessMessage(String data){
		APP_LOGS.debug("Executing verifyText");
		try{
			//System.out.println(driver.findElement(By.tagName("body")).getText());
			//System.out.println(data);
			Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains(OR.getProperty(data)));
			}
		catch(Exception e){
				k.log("Fail");
				}
		k.log("Pass");
		return "Pass";					
}
	
	public String verifyValidationMessages(String data){
		APP_LOGS.debug("Executing verifyText");
		try{
			Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains(data));
			}catch(Throwable t){
				k.log("Fail");
				ErrorUtil.addVerificationFailure(t);
				}
		k.log("Pass");
		return "Pass";					
}

      /*public String assertTrue(String data){
	     APP_LOGS.debug("Executing verifyText");
	    try{
	       Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty(data))).getText().contains(s));
	} catch(Exception e){
		k.log("Fail");
	}
	k.log("Pass");
	return "Pass";					
      }*/

	public String assertEquals(String identifier, String data) {
		APP_LOGS.debug("Executing verifyText");
		try {
			
			if (identifier.endsWith("_xpath"))
				Assert.assertEquals(driver.findElement(By.xpath(OR.getProperty(identifier))).getText(), OR.getProperty(data));
			else if (identifier.endsWith("_id"))
				Assert.assertEquals(driver.findElement(By.id(OR.getProperty(identifier))).getText(), data);
			else if (identifier.endsWith("_name"))
				Assert.assertEquals(driver.findElement(By.name(OR.getProperty(identifier))).getText(), data);
			} catch (Throwable t) {
			k.log("Fail");
			ErrorUtil.addVerificationFailure(t);
		}
		k.log("Pass");
		return "Pass";
	}

	public String assertEquals_Attribute(String identifier, String data) {
		APP_LOGS.debug("Executing verifyText");
		try {
			if (identifier.endsWith("_xpath"))
				Assert.assertEquals(driver.findElement(By.xpath(OR.getProperty(identifier))).getAttribute("value"), data);
			else if (identifier.endsWith("_id"))
				Assert.assertEquals(driver.findElement(By.id(OR.getProperty(identifier))).getAttribute("value"), data);
			else if (identifier.endsWith("_name"))
				Assert.assertEquals(driver.findElement(By.name(OR.getProperty(identifier))).getAttribute("value"), data);
		} catch (Throwable t) {
			k.log("Fail");
			ErrorUtil.addVerificationFailure(t);
		}
		k.log("Pass");
		return "Pass";
	}
	
	public String isElementPresence(String identifier) {
		int count = 0;
		try {
			
			if(identifier.endsWith("_xpath"))
				count = driver.findElements(By.xpath(OR.getProperty(identifier))).size();
			else if(identifier.endsWith("_id"))
				count = driver.findElements(By.id(OR.getProperty(identifier))).size();
			else if(identifier.endsWith("_name"))
				count = driver.findElements(By.name(OR.getProperty(identifier))).size();
			else if(identifier.endsWith("_css"))
				count = driver.findElements(By.cssSelector(OR.getProperty(identifier))).size();
			
			Assert.assertTrue(count > 0, "No element present");
		} catch (final Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			APP_LOGS.debug("No element present");
			return "false";
		}
		return "Pass";
	}
	
	
		public String isElementSelected(String identifier){
		
		try {
			System.out.println(OR.getProperty("identifier"));
			if(identifier.endsWith("_xpath"))
				Assert.assertTrue(driver.findElement(By.xpath("//*[@id='isMoneyCollectionAgent']")).isSelected());
			else if(identifier.endsWith("_id"))
				Assert.assertTrue(driver.findElement(By.id(OR.getProperty("identifier"))).isSelected());
			else if(identifier.endsWith("_name"))
				Assert.assertTrue(driver.findElement(By.name(OR.getProperty("identifier"))).isSelected());
			else if(identifier.endsWith("_css"))
				Assert.assertTrue(driver.findElement(By.xpath(OR.getProperty("identifier"))).isSelected());
			}
		catch (StaleElementReferenceException e) {
			//ErrorUtil.addVerificationFailure(t);
			System.out.println(e.getMessage());
			APP_LOGS.debug("No element Selected");
			return "false";
		}
		return "Pass";
	}
	
		
		public String acceptAlert() {
			
			try {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				}
			catch (final Throwable t) {
				ErrorUtil.addVerificationFailure(t);
				return "false";
			}
			return "Pass";
		}
		
		
		public String isElementDisabled(String identifier) throws NoSuchElementException{
			
			try {
				System.out.println(OR.getProperty("identifier"));
				if(identifier.endsWith("_xpath"))
					Assert.assertFalse(driver.findElement(By.xpath(OR.getProperty(identifier))).isEnabled(), "Element is enabled");
				else if(identifier.endsWith("_id"))
					Assert.assertFalse(driver.findElement(By.id(OR.getProperty(identifier))).isEnabled(), "Element is enabled");
				else if(identifier.endsWith("_name"))
					Assert.assertFalse(driver.findElement(By.name(OR.getProperty(identifier))).isEnabled(), "Element is enabled");
				else if(identifier.endsWith("_css"))
					Assert.assertFalse(driver.findElement(By.cssSelector(OR.getProperty(identifier))).isEnabled(), "Element is enabled");
				}
			catch (Throwable t) {
				ErrorUtil.addVerificationFailure(t);
				k.log(identifier +"Element is enabled");
				return "false";
			}
			return "Pass";
		}
		
		
	public String isElementReadonly(String identifier) throws NoSuchElementException{
			
			try {
				System.out.println(OR.getProperty("identifier"));
				if(identifier.endsWith("_xpath"))
					Assert.assertNotNull(driver.findElement(By.xpath(OR.getProperty(identifier))).getAttribute("readonly"));
				else if(identifier.endsWith("_id"))
					Assert.assertNotNull(driver.findElement(By.id(OR.getProperty(identifier))).getAttribute("readonly"));
				else if(identifier.endsWith("_name"))
					Assert.assertNotNull(driver.findElement(By.name(OR.getProperty(identifier))).getAttribute("readonly"));
				else if(identifier.endsWith("_css"))
					Assert.assertNotNull(driver.findElement(By.xpath(OR.getProperty(identifier))).getAttribute("readonly"));
				}
			catch (Throwable t) {
				ErrorUtil.addVerificationFailure(t);
				k.log(identifier +"Element is not in Read only mode");
				return "false";
			}
			return "Pass";
		}
	
		 
		 public static String datepicker(String dateTime){
			 try{
				 driver.findElement(By.cssSelector(".textbox-icon.combo-arrow")).click();
			      //button to move next in calendar
				  
			      WebElement nextLink = driver.findElement(By.xpath("html/body/div[3]/div/div[1]/div/div[1]/div[4]"));
			      //button to click in center of calendar header
			      //WebElement midLink = driver.findElement(By.xpath("//div[@id='datetimepicker_dateview']//div[@class='k-header']//a[contains(@class,'k-nav-fast')]"));
			      //button to move previous month in calendar
			      WebElement previousLink = driver.findElement(By.xpath("html/body/div[3]/div/div[1]/div/div[1]/div[3]"));
			      //Split the date time to get only the date part
			      String date_dd_MM_yyyy[] = (dateTime.split(" "));
			      //get the year difference between current year and year to set in calander
			      int yearDiff = Integer.parseInt(date_dd_MM_yyyy[2])- Calendar.getInstance().get(Calendar.YEAR);
			      //midLink.click();
			      if(yearDiff!=0){
			    	  //if you have to move next year
			    	  if(yearDiff>0){
			    		  for(int i=0;i< yearDiff;i++){
			    			  System.out.println("Year Diff->"+i);
			    			  nextLink.click();
			    		  }}
			    	  //if you have to move previous year
			    	  else if(yearDiff<0){
			    		  for(int i=0;i< (yearDiff*(-1));i++){
			    			  System.out.println("Year Diff->"+i);
			    			  previousLink.click();
			                    }}}
			        Thread.sleep(1000);
			        //Get all months from calendar to select correct one
			        driver.findElement(By.xpath("html/body/div[3]/div/div[1]/div/div[1]/div[5]/span")).click();
			        List<WebElement> list_AllMonthToBook = driver.findElements(By.xpath("html/body/div[3]/div/div[1]/div/div[2]/div/div[2]/table/tbody/tr/td"));
			        list_AllMonthToBook.get(Integer.parseInt(date_dd_MM_yyyy[1])-1).click();
			        Thread.sleep(1000);
			        
			        //get all dates from calendar to select correct one   
			        List<WebElement> list_AllDateToBook = driver.findElements(By.xpath("html/body/div[3]/div/div[1]/div/div[2]/table/tbody/tr/td"));
			        ArrayList<String> EmailIDs1 = new ArrayList<String>();
			        for (WebElement webElement : list_AllDateToBook) {
			        	EmailIDs1.add(webElement.getText());
			    	   }	
			        //System.out.println(EmailIDs1);
			        int index1= EmailIDs1.indexOf("1");
			        //int index2= EmailIDs1.indexOf("31");
			        for(int i=index1 ;i<EmailIDs1.size();i++){
			        	if (list_AllDateToBook.get(i).getText().equalsIgnoreCase(date_dd_MM_yyyy[0])){
			        		//System.out.println(list_AllDateToBook.get(i).getText());
			        		list_AllDateToBook.get(i).click();
			        		}}}catch(StaleElementReferenceException | InterruptedException |NumberFormatException t) {
		        			APP_LOGS.debug("Unable to select the Start date-------------" );
		        			ErrorUtil.addVerificationFailure(t);
		            }
			return "Pass";
		
		        }

	
	
	
}
	
	


