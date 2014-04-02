package eu.ydp.empiria.player.client.module.info.handler;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.module.item.ReportFeedbacks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackValueHandlerTest {

	@InjectMocks
	private FeedbackValueHandler testObj;

	@Mock
	private SessionDataSupplier sessionDataSupplier;
	@Mock
	private DataSourceDataSupplier dataSourceDataSupplier;
	@Mock
	private ResultForPageIndexProvider resultForPageIndexProvider;

	@Mock
	private ContentFieldInfo contentFieldInfo;
	@Mock
	private ReportFeedbacks reportFeedbacks;
	private final String EXPECTED_FEEDBACK = "feedback";
	private int itemIndex = 1;


	@Before
	public void setUp() {
		Integer result = 10;
		when(resultForPageIndexProvider.getFor(itemIndex)).thenReturn(result);
		when(dataSourceDataSupplier.getItemFeedbacks(itemIndex)).thenReturn(reportFeedbacks);
		when(reportFeedbacks.getValueForProgress(result)).thenReturn(EXPECTED_FEEDBACK);
	}

	@Test
	public void shouldGetValue() {
		// when
		String actualFeedback = testObj.getValue(contentFieldInfo, itemIndex);

		// then
		assertThat(actualFeedback).isEqualTo(EXPECTED_FEEDBACK);
	}
}
