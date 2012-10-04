package eu.ydp.empiria.test.selenium.choice;

public class Choice10CheckAndResetAndPageSwitchTestCase extends ChoiceTestCaseBase {

	public void testCheckAndResetAndPageSwitchSingle() throws Exception{
		openTestSingle("1");
		waitForPageLoad(0);
		// select check
		clickCheck();
		// reset
		clickReset();
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		// verify
		verifyOptionsSingle();
	}


	public void testCheckAndResetAndPageSwitchSingleShuffle() throws Exception{
		openTestSingle("3");
		waitForPageLoad(0);
		// select check
		clickCheck();
		// reset
		clickReset();
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		// verify
		verifyOptionsSingle();
	}
	
	private void verifyOptionsSingle(){
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED_DISABLED));
		// check whether there is no selected option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED_DISABLED));
	}

	public void testCheckAndResetAndPageSwitchMulti() throws Exception{
		openTestMulti("1");
		waitForPageLoad(0);
		// select check
		clickCheck();
		// reset
		clickReset();
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		// verify
		verifyOptionsMulti();
	}

	public void testCheckAndResetAndPageSwitchMultiShuffle() throws Exception{
		openTestMulti("3");
		waitForPageLoad(0);
		// select check
		clickCheck();
		// reset
		clickReset();
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		// verify
		verifyOptionsMulti();
	}
	
	private void verifyOptionsMulti(){
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_NOTSELECTED_DISABLED));
		// check whether there is no selected option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_SELECTED_DISABLED));
	}

}
