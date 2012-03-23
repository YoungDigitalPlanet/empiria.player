package eu.ydp.empiria.test.selenium.choice;


public class Choice11AnswerTestCase extends ChoiceTestCaseBase {


	public void testAnswerSingle1() throws Exception{
		performTestAnswer(false,IN_OPTION_1, IN_OPTION_2, IN_OPTION_3);
	}

	public void testAnswerSingle2() throws Exception{
		performTestAnswer(false, IN_OPTION_2, IN_OPTION_1, IN_OPTION_3);
	}

	public void testAnswerSingle3() throws Exception{
		performTestAnswer(false, IN_OPTION_3, IN_OPTION_1, IN_OPTION_2);
	}

	public void testAnswerMulti1() throws Exception{
		performTestAnswer(true,IN_OPTION_1, IN_OPTION_2, IN_OPTION_3);
	}

	public void testAnswerMulti2() throws Exception{
		performTestAnswer(true, IN_OPTION_2, IN_OPTION_1, IN_OPTION_3);
	}

	public void testAnswerMulti3() throws Exception{
		performTestAnswer(true, IN_OPTION_3, IN_OPTION_1, IN_OPTION_2);
	}
	
	private void performTestAnswer(boolean multi, String selectedOption, String notSelectedOption1, String notSelectedOption2) throws Exception{
		if (multi)
			openTestMulti("1");
		else
			openTestSingle("1");
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+SINGLE_OPTION_BUTTON);
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED_DISABLED));
		// check whether there is no disabled option
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED_DISABLED));
		// check whether the first option is selected 
		verifyTrue(selenium.isElementPresent(IN_MODULE+selectedOption+SINGLE_OPTION_SELECTED));
		// check whether the other options are not selected
		verifyFalse(selenium.isElementPresent(IN_MODULE+notSelectedOption1+SINGLE_OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+notSelectedOption2+SINGLE_OPTION_SELECTED));
	}

	
}
