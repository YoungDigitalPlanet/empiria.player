package eu.ydp.empiria.player.client.controller.variables.objects.response;

import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;

public enum CountMode {
	SINGLE("qp-countmode",EmpiriaStyleNameConstants.EMPIRIA_COUNTMODE_SINGLE),
	CORRECT_ANSWERS("qp-countmode",EmpiriaStyleNameConstants.EMPIRIA_COUNTMODE_CORRECT_ANSWERS);


	private String attributeName;
	private String globalCssClassName;

	CountMode(String globalCssClassName, String attributeName){
		this.attributeName = attributeName;
		this.globalCssClassName = globalCssClassName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public String getGlobalCssClassName() {
		return globalCssClassName;
	}
}
