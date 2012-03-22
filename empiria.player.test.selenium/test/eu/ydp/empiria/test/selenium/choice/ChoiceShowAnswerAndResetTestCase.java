package eu.ydp.empiria.test.selenium.choice;


public class ChoiceShowAnswerAndResetTestCase extends ChoiceTestCaseBase {
	
	public void testChoiceShowAnswerAndReset() throws Exception {
		openTest("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click reset
		clickReset();
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_DISABLED));
		// check whether there is no selected option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED_DISABLED));
	}
	
}
