package eu.ydp.empiria.player.client;

public interface EntryPointEventListener {

	void onNavigateNextItem();

	void onNavigatePreviousItem();

	void onNavigateFinishItem();

	void onNavigateFinishAssessment();

	void onNavigateResetItem();

	void onNavigateContinueItem();

	void onNavigateResetAssessment();

	void onNavigateSummaryAssessment();

	void onNavigateContinueAssessment();

}
