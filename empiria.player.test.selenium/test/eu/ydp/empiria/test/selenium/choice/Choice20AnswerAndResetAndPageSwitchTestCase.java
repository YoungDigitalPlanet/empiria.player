package eu.ydp.empiria.test.selenium.choice;

public class Choice20AnswerAndResetAndPageSwitchTestCase extends ChoiceTestCaseBase {

	public void testAnswerAndResetAndPageSwitch1() throws Exception{
		performTestAnswerAndResetAndPageSwitch("1", IN_OPTION_1);
	}

	public void testAnswerAndResetAndPageSwitch2() throws Exception{
		performTestAnswerAndResetAndPageSwitch("1", IN_OPTION_2);
	}

	public void testAnswerAndResetAndPageSwitch3() throws Exception{
		performTestAnswerAndResetAndPageSwitch("1", IN_OPTION_3);
	}

	public void testAnswerAndResetAndPageSwitchWithShuffle1() throws Exception{
		performTestAnswerAndResetAndPageSwitch("3", IN_OPTION_1);
	}

	public void testAnswerAndResetAndPageSwitchWithShuffle2() throws Exception{
		performTestAnswerAndResetAndPageSwitch("3", IN_OPTION_2);
	}

	public void testAnswerAndResetAndPageSwitchWithShuffle3() throws Exception{
		performTestAnswerAndResetAndPageSwitch("3", IN_OPTION_3);
	}
	
	private void performTestAnswerAndResetAndPageSwitch(String testIndex, String selectedOption) throws Exception{
		openTestSingle(testIndex);
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+SINGLE_OPTION_BUTTON);
		// switch pages
		clickReset();
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED_DISABLED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED_DISABLED));
		// check whether the first option is selected 
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+SINGLE_OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+SINGLE_OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+SINGLE_OPTION_SELECTED));
	}
}
