package eu.ydp.empiria.test.selenium.choice;


public class Choice07CheckTestCase extends ChoiceTestCaseBase {
	
	public void testChoiceCheckSingle() throws Exception {
		openTestSingle("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickCheck();
		//verify
		verifyOptionsSingle();
	}
	
	public void testChoiceCheckSingleShuffle() throws Exception {
		openTestSingle("3");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickCheck();
		//verify
		verifyOptionsSingle();
	}
	
	private void verifyOptionsSingle(){
		// check whether the module is locked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_NOTSELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED));
		// check whether there is no selected option		
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+SINGLE_OPTION_SELECTED_DISABLED));
		// check whether there is not selected option
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+SINGLE_OPTION_NOTSELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+SINGLE_OPTION_NOTSELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+SINGLE_OPTION_NOTSELECTED_DISABLED));
	}
	
	public void testChoiceCheckMulti() throws Exception {
		openTestMulti("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickCheck();
		//verify
		verifyOptionsMulti();
	}
	
	public void testChoiceCheckMultiShuffle() throws Exception {
		openTestMulti("3");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickCheck();
		//verify
		verifyOptionsMulti();
	}
	
	private void verifyOptionsMulti(){
		// check whether the module is locked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_NOTSELECTED));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_SELECTED));
		// check whether there is no selected option		
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+MULTI_OPTION_SELECTED_DISABLED));
		// check whether there is not selected option
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_1+MULTI_OPTION_NOTSELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_2+MULTI_OPTION_NOTSELECTED_DISABLED));
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_OPTION_3+MULTI_OPTION_NOTSELECTED_DISABLED));
	}
}
