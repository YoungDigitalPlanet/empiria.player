package eu.ydp.empiria.player.client.controller.xml;

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
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

@SuppressWarnings("PMD")
public class XMLDataProviderJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

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
	private XMLDataProvider instance;
	private final ItemData itemData = mock(ItemData.class);
	private final XmlData xmlData = mock(XmlData.class);

	@Before
	public void before() {
		setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
		instance = injector.getInstance(XMLDataProvider.class);
		doReturn(itemData).when(dataSourceManager).getItemData(Mockito.anyInt());
		itemData.data = xmlData;
		when(pageScopeFactory.getCurrentPageScope()).thenReturn(pageScope);
		doReturn(1).when(pageScope).getPageIndex();
	}

	@Test
	public void get() throws Exception {
		assertEquals(xmlData, instance.get());
		verify(pageScope).getPageIndex();
	}
}
