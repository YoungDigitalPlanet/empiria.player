package eu.ydp.empiria.test.selenium.choice;

public class ChoiceCheckAndResetAndPageSwitchTestCase extends ChoiceTestCaseBase {

	public void testCheckAndResetAndPageSwitch() throws Exception{
		openTest("1");
		waitForPageLoad(0);
		// select check
		clickCheck();
		Thread.sleep(100);
		// reset
		clickReset();
		Thread.sleep(100);
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		Thread.sleep(100);
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_DISABLED));
		// check whether there is no selected option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED_DISABLED));
	}

}
