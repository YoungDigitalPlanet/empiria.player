package eu.ydp.empiria.player.client.resources;

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
		return ensureEndingSlash(data.getBaseURL());
	}

	public String getCommonsPath() {
		String baseURL = getBasePath();
		String commonsPath = baseURL + "common";
		return ensureEndingSlash(commonsPath);
	}

	public String getCommonsFilePath(String filename) {
		String commonsPath = getCommonsPath();
		return commonsPath + filename;
	}

	private String ensureEndingSlash(String path) {
		if (!path.endsWith(SEPARATOR)) {
			path += SEPARATOR;
		}
		return path;
	}
}
