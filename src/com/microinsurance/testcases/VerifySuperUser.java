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
public class VerifySuperUser {
	
	/**
	 * @param data
	 */
	@Test(dataProvider = "getLoginData")
	public void loginSuperUser(Hashtable<String, String> data) {

		if (!TestUtil.isTestCaseExecutable("loginSuperUser", TestBase.xls))
			throw new SkipException("Skipping the test as Runmode is NO");
		
		if (!data.get("RunMode").equals("Y"))
			throw new SkipException(
					"Skipping the test as data set Runmode is NO");

		TestBase k = TestBase.getTestBaseInstance();
		String emailfromdb = k.executeQuery("verify_superuser");
		if (emailfromdb.isEmpty()) {
			

	}
	
	
	
	}

	/**
	 * 
	 * @param data
	 */
	
	@DataProvider
	public Object[][] getLoginData() {
		return TestUtil.getData("loginSuperUser", TestBase.xls);
	}

	

}
