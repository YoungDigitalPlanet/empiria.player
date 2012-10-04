package eu.ydp.empiria.test.selenium.choice;

public class Choice06ShowAnswerAndResetAndPageSwitchTestCase extends ChoiceTestCaseBase {

	public void testChoiceShowAnswerAndResetAndPageSwitchSingle() throws Exception {
		openTestSingle("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click reset
		clickReset();
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		// verify
		verifyOptionsSingle();
	}

	
	public void testChoiceShowAnswerAndResetAndPageSwitchShuffleSingle() throws Exception {
		openTestSingle("3");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click reset
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
		// check whether module is present
		verifyTrue(selenium.isElementPresent(IN_MODULE));
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED_DISABLED));
		// check whether there is no selected option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED_DISABLED));		
	}

	public void testChoiceShowAnswerAndResetAndPageSwitchMulti() throws Exception {
		openTestMulti("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click reset
		clickReset();
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		// verify
		verifyOptionsMulti();
	}

	
	public void testChoiceShowAnswerAndResetAndPageSwitchMultiShuffle() throws Exception {
		openTestMulti("3");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click reset
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
		// check whether module is present
		verifyTrue(selenium.isElementPresent(IN_MODULE));
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_NOTSELECTED_DISABLED));
		// check whether there is no selected option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_SELECTED_DISABLED));		
	}

}
