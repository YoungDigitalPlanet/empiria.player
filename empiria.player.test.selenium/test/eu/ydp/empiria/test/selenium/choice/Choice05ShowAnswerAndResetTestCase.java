package eu.ydp.empiria.test.selenium.choice;

public class Choice05ShowAnswerAndResetTestCase extends ChoiceTestCaseBase {
	
	public void testChoiceShowAnswerAndResetSingle() throws Exception {
		openTestSingle("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click reset
		clickReset();
		// verify
		verifyOptionsSingle();
	}
	
	public void testChoiceShowAnswerAndResetSingleShuffle() throws Exception {
		openTestSingle("3");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click reset
		clickReset();
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
	
	public void testChoiceShowAnswerAndResetMulti() throws Exception {
		openTestMulti("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click reset
		clickReset();
		// verify
		verifyOptionsMulti();
	}
	
	public void testChoiceShowAnswerAndResetMultiShuffle() throws Exception {
		openTestMulti("3");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click reset
		clickReset();
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
