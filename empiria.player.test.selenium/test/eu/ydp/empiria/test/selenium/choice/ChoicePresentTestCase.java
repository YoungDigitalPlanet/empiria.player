package eu.ydp.empiria.test.selenium.choice;


public class ChoicePresentTestCase extends ChoiceTestCaseBase {
	
	public void testChoicePresent1() throws Exception {
		openTest("1");
		waitForPageLoad(0);
		assertTexts();
	}
	
	public void testChoicePresent2() throws Exception {
		openTest("2");
		waitForPageLoad(0);
		navigateNextPage();
		waitForPageLoad(1);
		assertTexts();
	}
	
	public void testChoicePresent3() throws Exception {
		openTest("3");
		waitForPageLoad(0);
		assertTexts();
	}
	
	public void testChoicePresent4() throws Exception {
		openTest("4");
		waitForPageLoad(0);
		navigateNextPage();
		waitForPageLoad(1);
		assertTexts();
	}
	
	private void assertTexts(){
		assertTrue(selenium.isTextPresent("Prompt with math inside"));
		assertTrue(selenium.isTextPresent("ChoiceA"));
		assertTrue(selenium.isTextPresent("xx"));
		assertTrue(selenium.isTextPresent("yy"));
		assertTrue(selenium.isTextPresent("ChoiceB"));
		assertTrue(selenium.isTextPresent("ChoiceC"));		
	}
}
