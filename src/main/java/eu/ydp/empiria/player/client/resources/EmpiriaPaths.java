package eu.ydp.empiria.player.client.resources;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.communication.*;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class EmpiriaPaths {

	@Inject
	DataSourceManager dataSourceManager;
	private static final String SEPARATOR = "/";

	private String getScriptPath() {
		ItemData itemData = dataSourceManager.getItemData(0);
		return ensureTrailingSlash(itemData.getData().getBaseURL());
	}

	private String getMediaPath() {
		String scriptPath = getScriptPath();
		String mediaPath = scriptPath + "media";
		return ensureTrailingSlash(mediaPath);
	}

	public String getMediaFilePath(String filename){
		return getMediaPath() + filename;
	}

	public String getBasePath() {
		AssessmentData assessmentData = dataSourceManager.getAssessmentData();
		XmlData data = assessmentData.getData();
		return ensureTrailingSlash(data.getBaseURL());
	}

	public String getCommonsPath() {
		String baseURL = getBasePath();
		String commonsPath = baseURL + "common";
		return ensureTrailingSlash(commonsPath);
	}

	public String getPathRelativeToCommons(String path) {
		String commonsPath = getCommonsPath();
		String relativePath = commonsPath + path;
		return ensureTrailingSlash(relativePath);
	}

	public String getCommonsFilePath(String filename) {
		String commonsPath = getCommonsPath();
		return commonsPath + filename;
	}

	private String ensureTrailingSlash(String path) {
		if (!path.endsWith(SEPARATOR)) {
			path += SEPARATOR;
		}
		return path;
	}
}
