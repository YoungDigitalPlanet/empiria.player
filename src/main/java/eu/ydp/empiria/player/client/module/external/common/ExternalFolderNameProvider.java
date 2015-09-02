package eu.ydp.empiria.player.client.module.external.common;

public interface ExternalFolderNameProvider {

    String getExternalFolderName();

    String getExternalRelativePath(String file);
}
