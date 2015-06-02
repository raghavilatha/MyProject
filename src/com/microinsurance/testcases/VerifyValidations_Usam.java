package com.microinsurance.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.microinsurance.base.TestBase;
import com.microinsurance.util.TestUtil;
/**
 * 
 * @author raghavi
 *
 */
public class VerifyValidations_Usam{
	
	/**
	 * @param data
	 */
	@Test(dataProvider = "getaddUser_ValidationsData")
	public void addUser_Validations(Hashtable<String, String> data) {

		if (!TestUtil.isTestCaseExecutable("addUser_Validations", TestBase.xls))
			throw new SkipException("Skipping the test as Runmode is NO");
		if (!data.get("RunMode").equals("Y"))
			throw new SkipException(
					"Skipping the test as data set Runmode is NO");

		TestBase k = TestBase.getTestBaseInstance();
		
		
		k.log("*******Started addUser_Validations********");
		
			
		k.log("******addUser_Validations finished******");

	}
	
	
	@DataProvider
	public Object[][] getaddUser_ValidationsData() {
		return TestUtil.getData("addUser_Validations", TestBase.xls);
	}

	

}
