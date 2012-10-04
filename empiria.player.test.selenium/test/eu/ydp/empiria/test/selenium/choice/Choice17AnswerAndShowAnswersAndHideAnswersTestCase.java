package eu.ydp.empiria.test.selenium.choice;

public class Choice17AnswerAndShowAnswersAndHideAnswersTestCase extends ChoiceTestCaseBase {

	public void testAnswerAndShowAnswersAndHideAnswers1() throws Exception{
		performTestAnswerAndShowAnswersAndHideAnswers(IN_OPTION_1, IN_OPTION_2, IN_OPTION_3);
	}

	public void testAnswerAndShowAnswersAndHideAnswers2() throws Exception{
		performTestAnswerAndShowAnswersAndHideAnswers(IN_OPTION_2, IN_OPTION_1, IN_OPTION_3);
	}

	public void testAnswerAndShowAnswersAndHideAnswers3() throws Exception{
		performTestAnswerAndShowAnswersAndHideAnswers(IN_OPTION_3, IN_OPTION_1, IN_OPTION_2);
	}
	
	private void performTestAnswerAndShowAnswersAndHideAnswers(String selectedOption, String notSelectedOption1, String notSelectedOption2) throws Exception{
		openTestSingle("1");
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+SINGLE_OPTION_BUTTON);
		// show answers
		clickShowAnswers();
		// hide answers
		clickHideAnswers();
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED_DISABLED));
		// check whether there is no disabled option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED_DISABLED));
		// check whether the first option is selected 
		verifyTrue(selenium.isElementPresent(IN_MODULE+selectedOption+SINGLE_OPTION_SELECTED));
		// check whether the other options are not selected
		verifyTrue(selenium.isElementPresent(IN_MODULE+notSelectedOption1+SINGLE_OPTION_NOTSELECTED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+notSelectedOption2+SINGLE_OPTION_NOTSELECTED));
	}

}
