package eu.ydp.empiria.player.client.controller.item;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseNodeParser;

@SuppressWarnings("PMD")
public class ItemResponseManagerProviderJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(ItemXMLWrapper.class).toProvider(itemXMLWrapperProvider);
			binder.bind(ResponseNodeParser.class).toInstance(mock(ResponseNodeParser.class));
		}
	}

	private final Provider<ItemXMLWrapper> itemXMLWrapperProvider = mock(Provider.class);
	private final ItemResponseManager itemResponseManager = mock(ItemResponseManager.class);
	private ItemResponseManagerProvider instance;
	private ItemXMLWrapper itemXMLWrapper;
	@Before
	public void before() {
		setUp(new GuiceModuleConfiguration(),new CustomGinModule());
		itemXMLWrapper = mock(ItemXMLWrapper.class);
		when(itemXMLWrapperProvider.get()).thenReturn(itemXMLWrapper);
		instance = injector.getInstance(ItemResponseManagerProvider.class);
	}

	@Test
	public void getResponseManager() throws Exception {
		assertThat(instance.getResponseManager()).isNotNull();
		verify(itemXMLWrapper).getResponseDeclarations();
	}

	@Test
	public void get() throws Exception {
		assertThat(instance.get()).isNotNull();
		verify(itemXMLWrapper).getResponseDeclarations();
	}

}
