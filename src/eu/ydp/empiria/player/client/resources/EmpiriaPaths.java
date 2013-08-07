package eu.ydp.empiria.player.client.resources;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class EmpiriaPaths {

	@Inject
	DataSourceManager dataSourceManager;
	private static final String SEPARATOR = "/";

	public String getBasePath() {
		AssessmentData assessmentData = dataSourceManager.getAssessmentData();
		XmlData data = assessmentData.getData();
		return data.getBaseURL();
	}

	public String getCommonsPath() {
		String baseURL = getBasePath();
		return Joiner.on(SEPARATOR).join(baseURL, "common");
	}

	public String getCommonsFilePath(String filename) {
		String commonsPath = getCommonsPath();
		return Joiner.on(SEPARATOR).join(commonsPath, filename);
	}
}
