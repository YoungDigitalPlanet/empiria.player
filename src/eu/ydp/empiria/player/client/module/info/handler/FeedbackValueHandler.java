package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.module.item.ProgressToStringRangeMap;

public class FeedbackValueHandler implements FieldValueHandler {

	private final DataSourceDataSupplier dataSourceDataSupplier;
	private final ResultForPageIndexProvider resultForPageIndexProvider;

	@Inject
	public FeedbackValueHandler(@Assisted ResultForPageIndexProvider resultForPageIndexProvider,
	                            @Assisted DataSourceDataSupplier dataSourceDataSupplier) {

		this.dataSourceDataSupplier = dataSourceDataSupplier;
		this.resultForPageIndexProvider = resultForPageIndexProvider;
	}

	@Override
	public String getValue(ContentFieldInfo info, int refItemIndex) {
		int result = resultForPageIndexProvider.get(refItemIndex);
		ProgressToStringRangeMap pageFeedbacks = dataSourceDataSupplier.getItemFeedbacks(refItemIndex);
		return pageFeedbacks.getValueForProgress(result);
	}
}
