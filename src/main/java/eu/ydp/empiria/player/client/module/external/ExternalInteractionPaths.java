package eu.ydp.empiria.player.client.module.external;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleStructure;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionPaths {

	private final static String EXTERNAL_ENTRY_POINT = "index.html";

	private EmpiriaPaths empiriaPaths;
	private ExternalInteractionModuleStructure structure;

	@Inject
	public ExternalInteractionPaths(@ModuleScoped ExternalInteractionModuleStructure structure, EmpiriaPaths empiriaPaths) {
		this.structure = structure;
		this.empiriaPaths = empiriaPaths;
	}

	public String getExternalFilePath(String file) {
		String externalBaseName = structure.getBean().getSrc();
		String relativeFilePath = externalBaseName + "/" + file;
		return empiriaPaths.getMediaFilePath(relativeFilePath);
	}

	public String getExternalEntryPointPath() {
		String externalBaseName = structure.getBean().getSrc();
		String relativeFilePath = externalBaseName + "/" + EXTERNAL_ENTRY_POINT;
		return empiriaPaths.getMediaFilePath(relativeFilePath);
	}
}
