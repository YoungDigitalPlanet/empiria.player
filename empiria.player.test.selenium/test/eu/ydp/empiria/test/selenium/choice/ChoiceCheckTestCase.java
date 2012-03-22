package eu.ydp.empiria.test.selenium.choice;


public class ChoiceCheckTestCase extends ChoiceTestCaseBase {
	
	public void testChoiceCheck() throws Exception {
		openTest("1");
		waitForPageLoad(0);
		// select show answers (first answer is correct)
		clickCheck();
		// check whether the module is locked
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION));
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED));
		// check whether there is no selected option		
		verifyFalse(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_SELECTED_DISABLED));
		// check whether there is not selected option
		verifyTrue(selenium.isElementPresent(IN_MODULE+IN_ANYWHERE+OPTION_DISABLED));
	}
	
}
