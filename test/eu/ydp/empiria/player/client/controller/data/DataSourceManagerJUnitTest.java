package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.controller.data.events.DataLoaderEventListener;
import junit.framework.TestCase;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DataSourceManagerJUnitTest extends TestCase {

	public void testMainPreloaderAddedAndRemoved() {
		DataSourceManager dsm = new DataSourceManager(new AssessmentDataSourceManager());

		ForIsWidget mockRootPanel = mock(ForIsWidget.class);
		IsWidget mockImage = mock(IsWidget.class);

		dsm.loadMainDocument("", mockRootPanel, mockImage);
		verify(mockRootPanel).add(any(IsWidget.class));

		DataLoaderEventListener mockListener = mock(DataLoaderEventListener.class);
		dsm.setDataLoaderEventListener(mockListener);

		dsm.onLoadFinished(mockImage, mockRootPanel);
		verify(mockRootPanel).remove(any(int.class));
	}
}
