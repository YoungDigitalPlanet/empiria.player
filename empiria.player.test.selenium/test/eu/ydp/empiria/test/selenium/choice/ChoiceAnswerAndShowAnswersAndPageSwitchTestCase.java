package eu.ydp.empiria.test.selenium.choice;

public class ChoiceAnswerAndShowAnswersAndPageSwitchTestCase extends ChoiceTestCaseBase {

	public void testAnswerAndShowAnswersAndPageSwitch1() throws Exception{
		performTestAnswerAndShowAnswersAndPageSwitch(IN_OPTION_1, IN_OPTION_2, IN_OPTION_3);
	}

	public void testAnswerAndShowAnswersAndPageSwitch2() throws Exception{
		performTestAnswerAndShowAnswersAndPageSwitch(IN_OPTION_2, IN_OPTION_1, IN_OPTION_3);
	}

	public void testAnswerAndShowAnswersAndPageSwitch3() throws Exception{
		performTestAnswerAndShowAnswersAndPageSwitch(IN_OPTION_3, IN_OPTION_1, IN_OPTION_2);
	}
	
	private void performTestAnswerAndShowAnswersAndPageSwitch(String selectedOption, String notSelectedOption1, String notSelectedOption2) throws Exception{
		openTest("1");
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+OPTION_BUTTON);
		// show answers
		clickShowAnswers();
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_DISABLED));
		// check whether there is no disabled option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED_DISABLED));
		// check whether the first option is selected 
		verifyTrue(selenium.isElementPresent(IN_MODULE+selectedOption+OPTION_SELECTED));
		// check whether the other options are not selected
		verifyFalse(selenium.isElementPresent(IN_MODULE+notSelectedOption1+OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+notSelectedOption2+OPTION_SELECTED));
	}
}
