package eu.ydp.empiria.player.client.resources;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class EmpiriaPaths {

	@Inject
	DataSourceManager dataSourceManager;

	public String getCommonsPath() {
		AssessmentData assessmentData = dataSourceManager.getAssessmentData();
		XmlData data = assessmentData.getData();
		String baseURL = data.getBaseURL();
		return baseURL + "/common";
	}
}
