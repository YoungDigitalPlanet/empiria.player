package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.module.item.ReportFeedbacks;

public class FeedbackValueHandler implements FieldValueHandler {

	private final SessionDataSupplier sessionDataSupplier;
	private final DataSourceDataSupplier dataSourceDataSupplier;
	private final ResultForPageIndexProvider resultForPageIndexProvider;


	@Inject
	public FeedbackValueHandler(@Assisted SessionDataSupplier sessionDataSupplier,
	                            @Assisted DataSourceDataSupplier dataSourceDataSupplier,
	                            ResultForPageIndexProvider resultForPageIndexProvider) {

		this.sessionDataSupplier = sessionDataSupplier;
		this.dataSourceDataSupplier = dataSourceDataSupplier;
		this.resultForPageIndexProvider = resultForPageIndexProvider;
		this.resultForPageIndexProvider.setSessionDataSupplier(sessionDataSupplier);
	}

	@Override
	public String getValue(ContentFieldInfo info, int refItemIndex) {
		int result = resultForPageIndexProvider.getFor(refItemIndex);
		ReportFeedbacks pageFeedbacks = dataSourceDataSupplier.getItemFeedbacks(refItemIndex);
		return pageFeedbacks.getValueForProgress(result);
	}
}
