package eu.ydp.empiria.test.selenium.choice;

public class ChoiceAnswerAndCheckLockTestCase extends ChoiceTestCaseBase {

	public void testAnswerAndCheckLock12() throws Exception{
		performTestAnswerAndCheck(IN_OPTION_1, IN_OPTION_2, IN_OPTION_3);
	}
	public void testAnswerAndCheckLock13() throws Exception{
		performTestAnswerAndCheck(IN_OPTION_1, IN_OPTION_3, IN_OPTION_2);
	}
	public void testAnswerAndCheckLock21() throws Exception{
		performTestAnswerAndCheck(IN_OPTION_2, IN_OPTION_1, IN_OPTION_3);
	}
	public void testAnswerAndCheckLock23() throws Exception{
		performTestAnswerAndCheck(IN_OPTION_2, IN_OPTION_3, IN_OPTION_1);
	}
	public void testAnswerAndCheckLock31() throws Exception{
		performTestAnswerAndCheck(IN_OPTION_3, IN_OPTION_1, IN_OPTION_2);
	}
	public void testAnswerAndCheckLock32() throws Exception{
		performTestAnswerAndCheck(IN_OPTION_3, IN_OPTION_2, IN_OPTION_1);
	}
	
	private void performTestAnswerAndCheck(String selectedOption, String notSelectedOption1, String notSelectedOption2){
		openTest("1");
		waitForPageLoad(0);
		// select answer
		clickAndOut(IN_MODULE+selectedOption+OPTION_BUTTON);
		// check
		clickCheck();
		// select answer
		clickAndOut(IN_MODULE+notSelectedOption1+OPTION_BUTTON);
		
		// check whether there are no unlocked options
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED));
		// check whether the first option is selected 
		verifyTrue(selenium.isElementPresent(IN_MODULE+selectedOption+OPTION_SELECTED_DISABLED));
		// check whether the other options are not selected
		verifyTrue(selenium.isElementPresent(IN_MODULE+notSelectedOption1+OPTION_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+notSelectedOption2+OPTION_DISABLED));
	}

}
