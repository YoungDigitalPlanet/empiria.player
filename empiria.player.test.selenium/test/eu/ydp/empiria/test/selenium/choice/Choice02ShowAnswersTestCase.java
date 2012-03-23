package eu.ydp.empiria.test.selenium.choice;


public class Choice02ShowAnswersTestCase extends ChoiceTestCaseBase {


	public void testChoiceShowAnswerSingle1() throws Exception {
		openTestSingle("1");
		waitForPageLoad(0);
		clickShowAnswers();
		// check options
		verifyOptionsSingle();
	}
	
	public void testChoiceShowAnswerSingle2() throws Exception {
		openTestSingle("2");
		waitForPageLoad(0);
		navigateNextPage();
		waitForPageLoad(1);
		clickShowAnswers();
		// check options
		verifyOptionsSingle();
	}
	
	private void verifyOptionsSingle(){
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+SINGLE_OPTION_SELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+SINGLE_OPTION_NOTSELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+SINGLE_OPTION_NOTSELECTED_DISABLED));		
	}


	public void testChoiceShowAnswerMulti1() throws Exception {
		openTestMulti("1");
		waitForPageLoad(0);
		clickShowAnswers();
		// check options
		verifyOptionsMulti();
	}
	
	public void testChoiceShowAnswerMulti2() throws Exception {
		openTestMulti("2");
		waitForPageLoad(0);
		navigateNextPage();
		waitForPageLoad(1);
		clickShowAnswers();
		// check options
		verifyOptionsMulti();
	}
	
	private void verifyOptionsMulti(){
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+MULTI_OPTION_SELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+MULTI_OPTION_NOTSELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+MULTI_OPTION_NOTSELECTED_DISABLED));		
	}
}
