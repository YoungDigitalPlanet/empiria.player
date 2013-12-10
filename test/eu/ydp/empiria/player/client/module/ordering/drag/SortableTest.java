package eu.ydp.empiria.player.client.module.ordering.drag;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;

@RunWith(MockitoJUnitRunner.class)
public class SortableTest {

	@InjectMocks
	private Sortable testObj;
	@Mock
	private SortableNative sortableNative;

	@Test
	public void shouldinitNative() {
		// given
		final String id = "ID";
		OrderInteractionOrientation orderInteractionOrientation = OrderInteractionOrientation.HORIZONTAL;
		DragCallback callback = mock(DragCallback.class);

		// when
		testObj.init(id, orderInteractionOrientation, callback);

		// then
		verify(sortableNative).init(id, orderInteractionOrientation.getAxis(), callback);
	}

	@Test
	public void shouldEnableNative() {
		// given
		final String id = "ID";

		// when
		testObj.enable(id);

		// then
		verify(sortableNative).enable(id);
	}

	@Test
	public void shouldDisableNative() {
		// given
		final String id = "ID";

		// when
		testObj.disable(id);

		// then
		verify(sortableNative).disable(id);
	}
}
