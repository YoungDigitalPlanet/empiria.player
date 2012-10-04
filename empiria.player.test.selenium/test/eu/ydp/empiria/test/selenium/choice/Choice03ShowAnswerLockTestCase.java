package eu.ydp.empiria.test.selenium.choice;


public class Choice03ShowAnswerLockTestCase extends ChoiceTestCaseBase {

	public void testChoiceShowAnswerLockSingle() throws Exception {
		openTestSingle("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click the second answer
		clickAndOut(IN_MODULE+IN_OPTION_2+SINGLE_OPTION_BUTTON);
		// check whether the first answer is still selected
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+SINGLE_OPTION_SELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+SINGLE_OPTION_NOTSELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+SINGLE_OPTION_NOTSELECTED_DISABLED));
	}


	public void testChoiceShowAnswerLockMulti() throws Exception {
		openTestMulti("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickShowAnswers();
		// click the second answer
		clickAndOut(IN_MODULE+IN_OPTION_2+SINGLE_OPTION_BUTTON);
		// check whether the first answer is still selected
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+MULTI_OPTION_SELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+MULTI_OPTION_NOTSELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+MULTI_OPTION_NOTSELECTED_DISABLED));
	}
	
}
