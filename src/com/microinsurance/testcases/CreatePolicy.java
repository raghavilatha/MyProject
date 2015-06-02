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
public class CreatePolicy {
	
	/**
	 * @param data
	 */
	@Test(dataProvider = "getcreatePolicyData")
	public void createPolicy(Hashtable<String, String> data) {

		if (!TestUtil.isTestCaseExecutable("createPolicy", TestBase.xls))
			throw new SkipException("Skipping the test as Runmode is NO");
		if (!data.get("RunMode").equals("Y"))
			throw new SkipException(
					"Skipping the test as data set Runmode is NO");

		TestBase k = TestBase.getTestBaseInstance();
		
		

	}
	
	
	@Test(dataProvider = "getapprovePolicyData")
	public void approvePolicy(Hashtable<String, String> data) {

		if (!TestUtil.isTestCaseExecutable("approvePolicy", TestBase.xls))
			throw new SkipException("Skipping the test as Runmode is NO");

		TestBase k = TestBase.getTestBaseInstance();
		
	}
	
	

	
	@DataProvider
	public Object[][] getcreatePolicyData() {
		return TestUtil.getData("createPolicy", TestBase.xls);
	}
	
	@DataProvider
	public Object[][] getapprovePolicyData() {
		return TestUtil.getData("approvePolicy", TestBase.xls);
	}

	

	

}
