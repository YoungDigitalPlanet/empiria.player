package eu.ydp.empiria.player.client.controller.item;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseNodeParser;

@SuppressWarnings("PMD")
@RunWith(MockitoJUnitRunner.class)
public class ItemResponseManagerProviderJUnitTest {
	@Mock
	private Provider<ItemXMLWrapper> itemXMLWrapperProvider;
	@Mock
	private ResponseNodeParser responseNodeParser;

	private ItemResponseManagerProvider instance;
	private ItemXMLWrapper itemXMLWrapper;

	@Before
	public void before() {
		itemXMLWrapper = mock(ItemXMLWrapper.class);
		when(itemXMLWrapperProvider.get()).thenReturn(itemXMLWrapper);
		instance = new ItemResponseManagerProvider(itemXMLWrapperProvider, responseNodeParser);
	}

	@Test
	public void get() throws Exception {
		assertThat(instance.get()).isNotNull();
		verify(itemXMLWrapper).getResponseDeclarations();
	}

}
