package eu.ydp.empiria.test.selenium.choice;


public class Choice01PresentTestCase extends ChoiceTestCaseBase {
	
	public void testChoicePresentSingle1() throws Exception {
		openTestSingle("1");
		waitForPageLoad(0);
		assertTexts();
	}
	
	public void testChoicePresentSingle2() throws Exception {
		openTestSingle("2");
		waitForPageLoad(0);
		navigateNextPage();
		waitForPageLoad(1);
		assertTexts();
	}
	
	public void testChoicePresentSingle3() throws Exception {
		openTestSingle("3");
		waitForPageLoad(0);
		assertTexts();
	}
	
	public void testChoicePresentSingle4() throws Exception {
		openTestSingle("4");
		waitForPageLoad(0);
		navigateNextPage();
		waitForPageLoad(1);
		assertTexts();
	}
	
	public void testChoicePresentMulti1() throws Exception {
		openTestMulti("1");
		waitForPageLoad(0);
		assertTexts();
	}
	
	public void testChoicePresentMulti2() throws Exception {
		openTestMulti("2");
		waitForPageLoad(0);
		navigateNextPage();
		waitForPageLoad(1);
		assertTexts();
	}
	
	public void testChoicePresentMulti3() throws Exception {
		openTestMulti("3");
		waitForPageLoad(0);
		assertTexts();
	}
	
	public void testChoicePresentMulti4() throws Exception {
		openTestMulti("4");
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
