package eu.ydp.empiria.test.selenium.choice;


public class ChoiceShowAnswersTestCase extends ChoiceTestCaseBase {


	public void testChoiceShowAnswer() throws Exception {
		openTest("1");
		waitForPageLoad(0);
		clickShowAnswers();
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+OPTION_SELECTED_DISABLED));
	}
	
	public void testChoiceShowAnswer2() throws Exception {
		openTest("2");
		waitForPageLoad(0);
		navigateNextPage();
		waitForPageLoad(1);
		clickShowAnswers();
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+OPTION_SELECTED_DISABLED));
	}
}
