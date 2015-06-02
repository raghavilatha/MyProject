 	package com.microinsurance.testcases;

	import java.util.Hashtable;

import org.ini4j.Config;
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
	public class Actions_Usam extends VerifySuperUser {
		;
		/**
		 * @param data
		 */
		@Test(dataProvider = "getsuspendUserData")
		public void testBrowser(Hashtable<String, String> data) {
			if (!TestUtil.isTestCaseExecutable("testBrowser", TestBase.xls))
				throw new SkipException("Skipping the test as Runmode is NO");
			
         System.out.println("*************Test 1*******************");
         TestBase b = TestBase.getTestBaseInstance();
         b.openBrowser(Config.getSystemProperty("browserType"));
         

		}
		
		
		@DataProvider
		public Object[][] getsuspendUserData() {
			return TestUtil.getData("testBrowser", TestBase.xls);
		}

		

	}


