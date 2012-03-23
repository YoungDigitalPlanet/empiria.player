package eu.ydp.empiria.test.selenium.choice;

public class Choice23AnswerAndCheckAndShowAnswersTestCase extends ChoiceTestCaseBase {

	public void testAnswerAndCheckAndShowAnswers1() throws Exception{
		performTestAnswerAndCheckAndShowAnswers(IN_OPTION_1, IN_OPTION_2, IN_OPTION_3, SINGLE_MARK_CORRECT);
	}

	public void testAnswerAndCheckAndShowAnswers2() throws Exception{
		performTestAnswerAndCheckAndShowAnswers(IN_OPTION_2, IN_OPTION_1, IN_OPTION_3, SINGLE_MARK_WRONG);
	}

	public void testAnswerAndCheckAndShowAnswers3() throws Exception{
		performTestAnswerAndCheckAndShowAnswers(IN_OPTION_3, IN_OPTION_1, IN_OPTION_2, SINGLE_MARK_WRONG);
	}
	
	private void performTestAnswerAndCheckAndShowAnswers(String selectedOption, String notSelectedOption1, String notSelectedOption2, String markForSelection) throws Exception{
		openTestSingle("1");
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+SINGLE_OPTION_BUTTON);
		// click check
		clickCheck();
		// click show answers
		clickShowAnswers();
		// check options
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+SINGLE_OPTION_SELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+SINGLE_OPTION_NOTSELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+SINGLE_OPTION_NOTSELECTED_DISABLED));
	}
}
