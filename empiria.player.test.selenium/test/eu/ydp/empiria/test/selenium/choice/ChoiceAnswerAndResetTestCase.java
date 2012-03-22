package eu.ydp.empiria.test.selenium.choice;

public class ChoiceAnswerAndResetTestCase extends ChoiceTestCaseBase {

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
		openTest(testIndex);
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+OPTION_BUTTON);
		// switch pages
		clickReset();
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_DISABLED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED_DISABLED));
		// check whether the first option is selected 
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+OPTION));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+OPTION));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+OPTION));
	}
}
