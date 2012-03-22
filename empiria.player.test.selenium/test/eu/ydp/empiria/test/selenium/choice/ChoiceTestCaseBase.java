package eu.ydp.empiria.test.selenium.choice;

import eu.ydp.empiria.test.selenium.EmpiriaModuleTestCaseBase;

public abstract class ChoiceTestCaseBase extends EmpiriaModuleTestCaseBase {

	protected final String CLASS_MARK_CORRECT = "qp-choice-button-single-markanswers-correct";
	protected final String CLASS_MARK_WRONG = "qp-choice-button-single-markanswers-wrong";
	protected final String CLASS_MARK_NONE = "qp-choice-button-single-markanswers-none";
	protected final String CLASS_MODULE = "qp-choice-module";
	protected final String CLASS_OPTION = "qp-choice-single-button-notselected";
	protected final String CLASS_OPTION_SELECTED = "qp-choice-single-button-selected";
	protected final String CLASS_OPTION_DISABLED = "qp-choice-single-button-notselected-disabled";
	protected final String CLASS_OPTION_SELECTED_DISABLED = "qp-choice-single-button-selected-disabled";
	
	protected final String IN_MODULE = "//div[@class='"+CLASS_MODULE+"']/";
	protected final String IN_OPTION_1 = "div/div[1]/";
	protected final String IN_OPTION_2 = "div/div[2]/";
	protected final String IN_OPTION_3 = "div/div[3]/";
	protected final String IN_ANY_OPTION = "div/div[1-3]/";
	protected final String IN_ANYWHERE = "";

	protected final String OPTION = "/div[@class='"+CLASS_OPTION+"']";
	protected final String OPTION_DISABLED = "/div[@class='"+CLASS_OPTION_DISABLED+"']";
	protected final String OPTION_SELECTED = "/div[@class='"+CLASS_OPTION_SELECTED+"']";
	protected final String OPTION_SELECTED_DISABLED = "/div[@class='"+CLASS_OPTION_SELECTED_DISABLED+"']";
	protected final String OPTION_BUTTON = "div/div[2]";
	
	protected final String MARK_CORRECT = "/div[@class='"+CLASS_MARK_CORRECT+"']";
	protected final String MARK_WRONG = "/div[@class='"+CLASS_MARK_WRONG+"']";
	protected final String MARK_NONE = "/div[@class='"+CLASS_MARK_NONE+"']";

	protected void openTest(String index){
		selenium.open("/index-clean.html?content=test_choice/test_"+index+".xml");
	}

}
