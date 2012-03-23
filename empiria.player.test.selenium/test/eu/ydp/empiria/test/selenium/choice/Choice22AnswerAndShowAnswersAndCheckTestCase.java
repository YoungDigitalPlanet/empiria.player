package eu.ydp.empiria.test.selenium.choice;

public class Choice22AnswerAndShowAnswersAndCheckTestCase extends ChoiceTestCaseBase {

	public void testAnswerAndShowAnswersAndCheck1() throws Exception{
		performTestAnswerAndShowAnswersAndCheck(IN_OPTION_1, IN_OPTION_2, IN_OPTION_3, SINGLE_MARK_CORRECT);
	}

	public void testAnswerAndShowAnswersAndCheck2() throws Exception{
		performTestAnswerAndShowAnswersAndCheck(IN_OPTION_2, IN_OPTION_1, IN_OPTION_3, SINGLE_MARK_WRONG);
	}

	public void testAnswerAndShowAnswersAndCheck3() throws Exception{
		performTestAnswerAndShowAnswersAndCheck(IN_OPTION_3, IN_OPTION_1, IN_OPTION_2, SINGLE_MARK_WRONG);
	}
	
	private void performTestAnswerAndShowAnswersAndCheck(String selectedOption, String notSelectedOption1, String notSelectedOption2, String markForSelection) throws Exception{
		openTestSingle("1");
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+SINGLE_OPTION_BUTTON);
		// click show answers
		clickShowAnswers();
		// click check
		clickCheck();
		// check whether there are no unlocked options
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED));
		// check whether correct/wrong mark is present in the selected option
		verifyTrue(selenium.isElementPresent(IN_MODULE+selectedOption+markForSelection));
		// check whether none mark is present in other options
		verifyTrue(selenium.isElementPresent(IN_MODULE+notSelectedOption1+SINGLE_MARK_NONE));
		verifyTrue(selenium.isElementPresent(IN_MODULE+notSelectedOption2+SINGLE_MARK_NONE));
	}

}
