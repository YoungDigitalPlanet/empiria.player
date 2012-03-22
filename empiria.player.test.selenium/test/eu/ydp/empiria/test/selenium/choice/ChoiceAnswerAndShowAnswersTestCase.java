package eu.ydp.empiria.test.selenium.choice;

public class ChoiceAnswerAndShowAnswersTestCase extends ChoiceTestCaseBase {


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
		openTest("1");
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+OPTION_BUTTON);
		// click show answers
		clickShowAnswers();
		// check whether there are no unlocked options
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED));
		// check whether the first option is selected 
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+OPTION_SELECTED_DISABLED));
		// check whether the other options are not selected
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+OPTION_SELECTED_DISABLED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+OPTION_SELECTED_DISABLED));
	}

	
}
