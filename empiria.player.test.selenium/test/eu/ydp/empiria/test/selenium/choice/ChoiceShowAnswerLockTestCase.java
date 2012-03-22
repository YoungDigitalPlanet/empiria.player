package eu.ydp.empiria.test.selenium.choice;


public class ChoiceShowAnswerLockTestCase extends ChoiceTestCaseBase {

	public void testChoiceShowAnswerLock() throws Exception {
		openTest("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click the second answer
		click(IN_MODULE+IN_OPTION_2+OPTION_BUTTON);
		// check whether the first answer is still selected
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+OPTION_SELECTED_DISABLED));
	}
}
