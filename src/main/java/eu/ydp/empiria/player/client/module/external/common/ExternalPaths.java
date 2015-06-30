package eu.ydp.empiria.player.client.module.external.common;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;

public class ExternalPaths {

    private final static String EXTERNAL_ENTRY_POINT = "index.html";

    private EmpiriaPaths empiriaPaths;
    private ExternalFolderNameProvider externalFolderNameProvider;

    @Inject
    public ExternalPaths(EmpiriaPaths empiriaPaths) {
        this.empiriaPaths = empiriaPaths;
    }

    public void setExternalFolderNameProvider(ExternalFolderNameProvider externalFolderNameProvider) {
        this.externalFolderNameProvider = externalFolderNameProvider;
    }

    public String getExternalFilePath(String file) {
        String externalBaseName = externalFolderNameProvider.getExternalFolderName();
        String relativeFilePath = externalBaseName + "/" + file;
        return empiriaPaths.getMediaFilePath(relativeFilePath);
    }

    public String getExternalEntryPointPath() {
        String externalBaseName = externalFolderNameProvider.getExternalFolderName();
        String relativeFilePath = externalBaseName + "/" + EXTERNAL_ENTRY_POINT;
        return empiriaPaths.getMediaFilePath(relativeFilePath);
    }
}
