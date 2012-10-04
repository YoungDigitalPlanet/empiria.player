package eu.ydp.empiria.test.selenium.choice;

public class Choice16AnswerAndShowAnswersTestCase extends ChoiceTestCaseBase {


	public void testAnswerAndShowAnswers1() throws Exception{
		performTestAnswerAndShowAnswers(IN_OPTION_1);
	}

	public void testAnswerAndShowAnswers2() throws Exception{
		performTestAnswerAndShowAnswers(IN_OPTION_2);
	}

	public void testAnswerAndShowAnswers3() throws Exception{
		performTestAnswerAndShowAnswers(IN_OPTION_3);
	}
	
	private void performTestAnswerAndShowAnswers(String selectedOption) throws Exception{
		openTestSingle("1");
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+SINGLE_OPTION_BUTTON);
		// click show answers
		clickShowAnswers();
		// check whether there are no unlocked options
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED));
		// check whether the first option is selected 
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+SINGLE_OPTION_SELECTED_DISABLED));
		// check whether the other options are not selected
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+SINGLE_OPTION_SELECTED_DISABLED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+SINGLE_OPTION_SELECTED_DISABLED));
	}

	
}
