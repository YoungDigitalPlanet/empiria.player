package eu.ydp.empiria.test.selenium.choice;

public class ChoiceShowAnswerAndResetAndPageSwitchTestCase extends ChoiceTestCaseBase {
	
	public void testChoiceShowAnswerAndResetAndPageSwitch() throws Exception {
		openTest("1");
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
		// check whether module is present
		verifyTrue(selenium.isElementPresent(IN_MODULE));
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_DISABLED));
		// check whether there is no selected option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED_DISABLED));
	}
}
