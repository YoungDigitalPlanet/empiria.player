package eu.ydp.empiria.test.selenium.choice;


public class Choice09CheckAndResetTestCase extends ChoiceTestCaseBase {

	public void testCheckAndResetSingle() throws Exception{
		openTestSingle("1");
		waitForPageLoad(0);
		// select check
		clickCheck();
		// reset
		clickReset();
		// VERIFY
		verifyOptionsSingle();
	}

	public void testCheckAndResetSingleShuffle() throws Exception{
		openTestSingle("3");
		waitForPageLoad(0);
		// select check
		clickCheck();
		// reset
		clickReset();
		// VERIFY
		verifyOptionsSingle();
	}
	
	private void verifyOptionsSingle(){
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED_DISABLED));
		// check whether there is no selected option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED_DISABLED));
		//check whether there are not selected, not locked options
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+SINGLE_OPTION_NOTSELECTED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+SINGLE_OPTION_NOTSELECTED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+SINGLE_OPTION_NOTSELECTED));
	}

	public void testCheckAndResetMulti() throws Exception{
		openTestMulti("1");
		waitForPageLoad(0);
		// select check
		clickCheck();
		// reset
		clickReset();
		// VERIFY
		verifyOptionsMulti();
	}

	public void testCheckAndResetMultiShuffle() throws Exception{
		openTestMulti("3");
		waitForPageLoad(0);
		// select check
		clickCheck();
		// reset
		clickReset();
		// VERIFY
		verifyOptionsMulti();
	}
	
	private void verifyOptionsMulti(){
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_NOTSELECTED_DISABLED));
		// check whether there is no selected option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_SELECTED_DISABLED));
		//check whether there are not selected, not locked options
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+MULTI_OPTION_NOTSELECTED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+MULTI_OPTION_NOTSELECTED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+MULTI_OPTION_NOTSELECTED));
	}
	
}
