package eu.ydp.empiria.test.selenium.choice;


public class ChoiceCheckAndResetTestCase extends ChoiceTestCaseBase {

	public void testCheckAndReset() throws Exception{
		openTest("1");
		waitForPageLoad(0);
		// select check
		clickCheck();
		Thread.sleep(100);
		// reset
		clickReset();
		Thread.sleep(100);
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_DISABLED));
		// check whether there is no selected option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED_DISABLED));
		//check whether there are not selected, not locked options
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+OPTION));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+OPTION));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+OPTION));
	}
	
}
