package eu.ydp.empiria.player.client.module.ordering.drag;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.gin.module.ModuleScopedLazyProvider;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;

@RunWith(MockitoJUnitRunner.class)
public class SortCallbackImplTest {

	@InjectMocks
	private SortCallbackImpl testObj;
	@Mock
	private ModuleScopedLazyProvider<OrderInteractionPresenter> presenterProvider;
	@Mock
	private OrderingItemsDao orderingItemsDao;
	@Mock
	private TouchController touchController;

	@Test
	public void shouldTestName() {
		// given
		List<String> items = Lists.newArrayList("a", "b", "c");
		final List<String> EXPECTED_ITEMS_ORDER = ImmutableList.of("a", "c", "b");
		when(orderingItemsDao.getItemsOrder()).thenReturn(items);
		OrderInteractionPresenter presenter = mock(OrderInteractionPresenter.class);
		when(presenterProvider.get()).thenReturn(presenter);
		final int FROM = 1;
		final int TO = 2;

		// when
		testObj.sortStoped(FROM, TO);

		// then
		verify(presenter).updateItemsOrder(EXPECTED_ITEMS_ORDER);
	}

	@Test
	public void shouldLockSwype() {
		// when
		testObj.setSwypeLock(true);
		// then
		verify(touchController).setSwypeLock(true);
	}

	@Test
	public void shouldNotLockSwype() {
		// when
		testObj.setSwypeLock(false);
		// then
		verify(touchController).setSwypeLock(false);
	}

}
