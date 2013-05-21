package eu.ydp.empiria.player.client.controller.item;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

@SuppressWarnings("PMD")
public class ItemDataProviderJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit{


	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(DataSourceManager.class).toInstance(dataSourceManager);
			binder.bind(PageScopeFactory.class).toInstance(pageScopeFactory);
		}
	}

	private final DataSourceManager dataSourceManager = mock(DataSourceManager.class);
	private final PageScopeFactory pageScopeFactory = mock(PageScopeFactory.class);
	private final CurrentPageScope pageScope = spy(new CurrentPageScope(new Page()));
	private ItemDataProvider instance;
	private final ItemData itemData = mock(ItemData.class);

	@Before
	public void before() {
		setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
		instance = injector.getInstance(ItemDataProvider.class);
		doReturn(itemData).when(dataSourceManager).getItemData(Mockito.anyInt());
		when(pageScopeFactory.getCurrentPageScope()).thenReturn(pageScope);
		doReturn(1).when(pageScope).getPageIndex();
	}

	@Test
	public void get() throws Exception {
		assertEquals(itemData, instance.get());
		verify(pageScope).getPageIndex();
	}

}
