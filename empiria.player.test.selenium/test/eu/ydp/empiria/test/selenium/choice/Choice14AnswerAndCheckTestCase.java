package eu.ydp.empiria.test.selenium.choice;

public class Choice14AnswerAndCheckTestCase extends ChoiceTestCaseBase {

	public void testCorrectAndCheck1() throws Exception{
		performTestCorrectAndCheck(IN_OPTION_1, IN_OPTION_2, IN_OPTION_3, SINGLE_MARK_CORRECT);
	}

	public void testCorrectAndCheck2() throws Exception{
		performTestCorrectAndCheck(IN_OPTION_2, IN_OPTION_1, IN_OPTION_3, SINGLE_MARK_WRONG);
	}

	public void testCorrectAndCheck3() throws Exception{
		performTestCorrectAndCheck(IN_OPTION_3, IN_OPTION_1, IN_OPTION_2, SINGLE_MARK_WRONG);
	}
	
	private void performTestCorrectAndCheck(String selectedOption, String notSelectedOption1, String notSelectedOption2, String markForSelection) throws Exception{
		openTestSingle("1");
		waitForPageLoad(0);
		// select answer
		click(IN_MODULE+selectedOption+SINGLE_OPTION_BUTTON);
		// check
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
