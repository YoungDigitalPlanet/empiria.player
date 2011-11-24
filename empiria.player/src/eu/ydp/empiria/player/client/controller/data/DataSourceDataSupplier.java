package eu.ydp.empiria.player.client.controller.data;

public interface DataSourceDataSupplier {
	int getItemsCount();
	String getItemTitle(int itemIndex);
	String getAssessmentTitle();
}
