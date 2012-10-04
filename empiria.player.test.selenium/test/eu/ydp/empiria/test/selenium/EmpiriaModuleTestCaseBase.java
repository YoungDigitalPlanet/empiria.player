package eu.ydp.empiria.test.selenium;

import com.thoughtworks.selenium.*;

public abstract class EmpiriaModuleTestCaseBase extends SeleneseTestCase {
	
	protected final String LOCATOR_CHECK = "css=div.qp-defaultassessmentfooter-check-button";
	protected final String LOCATOR_SHOW_ANSWERS = "css=div.qp-defaultassessmentfooter-showanswers-button";
	protected final String LOCATOR_HIDE_ANSWERS = "css=div.qp-defaultassessmentfooter-hideanswers-button";
	protected final String LOCATOR_RESET = "css=div.qp-defaultassessmentfooter-reset-button";
	
	public void setUp() throws Exception {
		setUp("http://localhost:38090/", "*chrome");
	}
	
	protected void waitForPageLoad(final int index){			
		new Wait("Item not loaded.") {
			public boolean until() {
				return selenium.isElementPresent("css=.qp-page-counter-list")  &&  selenium.getSelectedValue("css=.qp-page-counter-list").equals(String.valueOf(index+1));
			}
		};
	}
	
	protected void navigateNextPage(){
		click("css=div.qp-defaultassessmentfooter-next-button");
	}
	
	protected void navigatePreviousPage(){
		click("css=div.qp-defaultassessmentfooter-prev-button");
	}
	
	protected void click(String locator){
		selenium.mouseOver(locator);
		selenium.mouseDown(locator);
		selenium.mouseUp(locator);			
	}
	
	protected void clickAndOut(String locator){
		click(locator);
		selenium.mouseOut(locator);		
	}

	protected void clickCheck(){
		click(LOCATOR_CHECK);
	}
	
	protected void clickShowAnswers(){
		click(LOCATOR_SHOW_ANSWERS);
	}
	
	protected void clickHideAnswers(){
		click(LOCATOR_HIDE_ANSWERS);
	}
	
	protected void clickReset(){
		click(LOCATOR_RESET);
	}

}