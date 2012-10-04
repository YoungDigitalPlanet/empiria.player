package eu.ydp.empiria.test.selenium.choice;

public class Choice21AnswerAndSwitchPageAndAnswerAndSwitchPageTestCase extends ChoiceTestCaseBase {

	public void testAnswerAndPageSwitchAndAnswerAndPageSwitch1() throws Exception{
		performTestAnswerAndPageSwitchAndAnswerAndPageSwitch(IN_OPTION_1, IN_OPTION_2, IN_OPTION_3);
	}
	
	public void testAnswerAndPageSwitchAndAnswerAndPageSwitch2() throws Exception{
		performTestAnswerAndPageSwitchAndAnswerAndPageSwitch(IN_OPTION_1, IN_OPTION_3, IN_OPTION_2);
	}

	public void testAnswerAndPageSwitchAndAnswerAndPageSwitch3() throws Exception{
		performTestAnswerAndPageSwitchAndAnswerAndPageSwitch(IN_OPTION_2, IN_OPTION_1, IN_OPTION_3);
	}

	public void testAnswerAndPageSwitchAndAnswerAndPageSwitch4() throws Exception{
		performTestAnswerAndPageSwitchAndAnswerAndPageSwitch(IN_OPTION_2, IN_OPTION_3, IN_OPTION_1);
	}

	public void testAnswerAndPageSwitchAndAnswerAndPageSwitch5() throws Exception{
		performTestAnswerAndPageSwitchAndAnswerAndPageSwitch(IN_OPTION_3, IN_OPTION_1, IN_OPTION_2);
	}

	public void testAnswerAndPageSwitchAndAnswerAndPageSwitch6() throws Exception{
		performTestAnswerAndPageSwitchAndAnswerAndPageSwitch(IN_OPTION_3, IN_OPTION_2, IN_OPTION_1);
	}
	
	private void performTestAnswerAndPageSwitchAndAnswerAndPageSwitch(String selectedOption, String notSelectedOption1, String notSelectedOption2) throws Exception{
		openTestSingle("1");
		waitForPageLoad(0);
		// select answer
		click(IN_MODULE+notSelectedOption1+SINGLE_OPTION_BUTTON);
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		// select answer
		click(IN_MODULE+selectedOption+SINGLE_OPTION_BUTTON);
		// switch pages
		navigateNextPage();
		waitForPageLoad(1);
		navigatePreviousPage();
		waitForPageLoad(0);
		// check whether the module is unlocked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED_DISABLED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED_DISABLED));
		// check whether the first option is selected 
		verifyTrue(selenium.isElementPresent(IN_MODULE+selectedOption+SINGLE_OPTION_SELECTED));
		// check whether the other options are not selected
		verifyFalse(selenium.isElementPresent(IN_MODULE+notSelectedOption1+SINGLE_OPTION_SELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+notSelectedOption2+SINGLE_OPTION_SELECTED));
	}

}
