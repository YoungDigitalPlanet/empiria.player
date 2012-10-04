package eu.ydp.empiria.test.selenium.choice;

public class Choice19AnswerAndResetTestCase extends ChoiceTestCaseBase {

	public void testAnswerAndReset1() throws Exception{
		performTestAnswerAndReset("1", IN_OPTION_1);
	}

	public void testAnswerAndReset2() throws Exception{
		performTestAnswerAndReset("1", IN_OPTION_2);
	}

	public void testAnswerAndReset3() throws Exception{
		performTestAnswerAndReset("1", IN_OPTION_3);
	}

	public void testAnswerAndResetWithShuffle1() throws Exception{
		performTestAnswerAndReset("3", IN_OPTION_1);
	}

	public void testAnswerAndResetWithShuffle2() throws Exception{
		performTestAnswerAndReset("3", IN_OPTION_2);
	}

	public void testAnswerAndResetWithShuffle3() throws Exception{
		performTestAnswerAndReset("3", IN_OPTION_3);
	}
	
	private void performTestAnswerAndReset(String testIndex, String selectedOption) throws Exception{
		openTestSingle(testIndex);
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+SINGLE_OPTION_BUTTON);
		// switch pages
		clickReset();
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED_DISABLED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED_DISABLED));
		// check whether the first option is selected 
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+SINGLE_OPTION_NOTSELECTED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+SINGLE_OPTION_NOTSELECTED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+SINGLE_OPTION_NOTSELECTED));
	}
}
