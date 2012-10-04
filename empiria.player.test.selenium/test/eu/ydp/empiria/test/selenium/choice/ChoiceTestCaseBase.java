package eu.ydp.empiria.test.selenium.choice;

import eu.ydp.empiria.test.selenium.EmpiriaModuleTestCaseBase;

public abstract class ChoiceTestCaseBase extends EmpiriaModuleTestCaseBase {

	public static final String SINGLE_CLASS_MARK_CORRECT = "qp-choice-button-single-markanswers-correct";
	public static final String SINGLE_CLASS_MARK_WRONG = "qp-choice-button-single-markanswers-wrong";
	public static final String SINGLE_CLASS_MARK_NONE = "qp-choice-button-single-markanswers-none";
	public static final String SINGLE_CLASS_MODULE = "qp-choice-module";
	public static final String SINGLE_CLASS_OPTION = "qp-choice-single-button-notselected";
	public static final String SINGLE_CLASS_OPTION_SELECTED = "qp-choice-single-button-selected";
	public static final String SINGLE_CLASS_OPTION_DISABLED = "qp-choice-single-button-notselected-disabled";
	public static final String SINGLE_CLASS_OPTION_SELECTED_DISABLED = "qp-choice-single-button-selected-disabled";
	
	public static final String MULTI_CLASS_MARK_CORRECT = "qp-choice-button-multi-markanswers-correct";
	public static final String MULTI_CLASS_MARK_WRONG = "qp-choice-button-multi-markanswers-wrong";
	public static final String MULTI_CLASS_MARK_NONE = "qp-choice-button-multi-markanswers-none";
	public static final String MULTI_CLASS_MODULE = "qp-choice-module";
	public static final String MULTI_CLASS_OPTION = "qp-choice-multi-button-notselected";
	public static final String MULTI_CLASS_OPTION_SELECTED = "qp-choice-multi-button-selected";
	public static final String MULTI_CLASS_OPTION_DISABLED = "qp-choice-multi-button-notselected-disabled";
	public static final String MULTI_CLASS_OPTION_SELECTED_DISABLED = "qp-choice-multi-button-selected-disabled";
	
	public static final String IN_MODULE = "//div[@class='"+SINGLE_CLASS_MODULE+"']/";
	public static final String IN_OPTION_1 = "div/div[1]/";
	public static final String IN_OPTION_2 = "div/div[2]/";
	public static final String IN_OPTION_3 = "div/div[3]/";
	public static final String IN_ANY_OPTION = "div/div[1-3]/";
	public static final String IN_ANYWHERE = "";

	public static final String SINGLE_OPTION_NOTSELECTED = "/div[@class='"+SINGLE_CLASS_OPTION+"']";
	public static final String SINGLE_OPTION_NOTSELECTED_DISABLED = "/div[@class='"+SINGLE_CLASS_OPTION_DISABLED+"']";
	public static final String SINGLE_OPTION_SELECTED = "/div[@class='"+SINGLE_CLASS_OPTION_SELECTED+"']";
	public static final String SINGLE_OPTION_SELECTED_DISABLED = "/div[@class='"+SINGLE_CLASS_OPTION_SELECTED_DISABLED+"']";
	public static final String SINGLE_OPTION_BUTTON = "div/div[2]";
	
	public static final String SINGLE_MARK_CORRECT = "/div[@class='"+SINGLE_CLASS_MARK_CORRECT+"']";
	public static final String SINGLE_MARK_WRONG = "/div[@class='"+SINGLE_CLASS_MARK_WRONG+"']";
	public static final String SINGLE_MARK_NONE = "/div[@class='"+SINGLE_CLASS_MARK_NONE+"']";

	public static final String MULTI_OPTION_NOTSELECTED = "/div[@class='"+MULTI_CLASS_OPTION+"']";
	public static final String MULTI_OPTION_NOTSELECTED_DISABLED = "/div[@class='"+MULTI_CLASS_OPTION_DISABLED+"']";
	public static final String MULTI_OPTION_SELECTED = "/div[@class='"+MULTI_CLASS_OPTION_SELECTED+"']";
	public static final String MULTI_OPTION_SELECTED_DISABLED = "/div[@class='"+MULTI_CLASS_OPTION_SELECTED_DISABLED+"']";
	public static final String MULTI_OPTION_BUTTON = "div/div[2]";
	
	public static final String MULTI_MARK_CORRECT = "/div[@class='"+MULTI_CLASS_MARK_CORRECT+"']";
	public static final String MULTI_MARK_WRONG = "/div[@class='"+MULTI_CLASS_MARK_WRONG+"']";
	public static final String MULTI_MARK_NONE = "/div[@class='"+MULTI_CLASS_MARK_NONE+"']";

	protected void openTestSingle(String index){
		selenium.open("/index-clean.html?content=test_choice/test_single_"+index+".xml");
	}
	
	protected void openTestMulti(String index){
		selenium.open("/index-clean.html?content=test_choice/test_multi_"+index+".xml");
	}

}
