package eu.ydp.empiria.test.selenium.choice;


public class ChoiceCorrectAndCheckTestCase extends ChoiceTestCaseBase {

	public void testCorrectAndCheck1() throws Exception{
		performTestCorrectAndCheck(IN_OPTION_1, IN_OPTION_2, IN_OPTION_3, MARK_CORRECT);
	}

	public void testCorrectAndCheck2() throws Exception{
		performTestCorrectAndCheck(IN_OPTION_2, IN_OPTION_1, IN_OPTION_3, MARK_WRONG);
	}

	public void testCorrectAndCheck3() throws Exception{
		performTestCorrectAndCheck(IN_OPTION_3, IN_OPTION_1, IN_OPTION_2, MARK_WRONG);
	}
	
	private void performTestCorrectAndCheck(String selectedOption, String notSelectedOption1, String notSelectedOption2, String markForSelection) throws Exception{
		openTest("1");
		waitForPageLoad(0);
		// select answer
		click(IN_MODULE+selectedOption+OPTION_BUTTON);
		// check
		clickCheck();
		// check whether there are no unlocked options
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED));
		// check whether correct/wrong mark is present in the selected option
		verifyTrue(selenium.isElementPresent(IN_MODULE+selectedOption+markForSelection));
		// check whether none mark is present in other options
		verifyTrue(selenium.isElementPresent(IN_MODULE+notSelectedOption1+MARK_NONE));
		verifyTrue(selenium.isElementPresent(IN_MODULE+notSelectedOption2+MARK_NONE));
	}

}
