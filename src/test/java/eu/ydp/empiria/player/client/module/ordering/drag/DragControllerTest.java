package eu.ydp.empiria.player.client.module.ordering.drag;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;

@RunWith(MockitoJUnitRunner.class)
public class DragControllerTest {

	private static final String ID = "ID";
	private static final String ID_SELECTOR = ".ID";

	@InjectMocks
	private DragController testobj;

	@Mock
	private OrderingItemsDao orderingItemsDao;
	@Mock
	private OrderInteractionView interactionView;
	@Mock
	private Sortable sortable;
	@Mock
	private SortCallback callback;

	@Before
	public void setup() {
		when(interactionView.getMainPanelUniqueCssClass()).thenReturn(ID);
	}

	@Test
	public void shouldInitializeSortable() {
		// given
		OrderInteractionOrientation orientation = OrderInteractionOrientation.VERTICAL;

		// when
		testobj.init(orientation);

		// then
		verify(sortable).init(ID_SELECTOR, orientation, callback);
	}

	@Test
	public void shouldEnableDrag() {
		// when
		testobj.enableDrag();

		// then
		verify(sortable).enable(ID_SELECTOR);
	}

	@Test
	public void shouldDisableDrag() {
		// when
		testobj.disableDrag();

		// then
		verify(sortable).disable(ID_SELECTOR);
	}

}
