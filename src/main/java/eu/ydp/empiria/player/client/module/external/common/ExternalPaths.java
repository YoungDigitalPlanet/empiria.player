package eu.ydp.empiria.player.client.module.external.common;

public class ExternalPaths {

    private final static String EXTERNAL_ENTRY_POINT = "index.html";

    private ExternalFolderNameProvider externalFolderNameProvider;

    public void setExternalFolderNameProvider(ExternalFolderNameProvider externalFolderNameProvider) {
        this.externalFolderNameProvider = externalFolderNameProvider;
    }

    public String getExternalFilePath(String file) {
        String externalBaseName = externalFolderNameProvider.getExternalFolderName();
        String relativeFilePath = externalBaseName + "/" + file;
        return externalFolderNameProvider.getExternalRelativePath(relativeFilePath);
    }

    public String getExternalEntryPointPath() {
        return getExternalFilePath(EXTERNAL_ENTRY_POINT);
    }
}
