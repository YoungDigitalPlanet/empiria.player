package eu.ydp.empiria.player.client.module.sourcelist;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParserMock;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.empiria.player.client.module.view.HasDimensions;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.xml.XMLParser;

@SuppressWarnings("PMD")
public class SourceListModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private SourceListModule instance;
	private SourceListPresenter presenter;
	private ModuleSocket moduleSocket;
	private ReflectionsUtils reflectionsUtils;
	private SourceListModuleStructure sourceListModuleStructure;
	private final static String sourcelistId = "id1";
	private SourceListBean bean;

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(SourceListPresenter.class).toInstance(mock(SourceListPresenter.class));
			binder.bind(ModuleSocket.class).toInstance(mock(ModuleSocket.class));
			binder.bind(SourceListModuleStructure.class).toInstance(mock(SourceListModuleStructure.class));
			binder.bind(SourcelistManager.class).annotatedWith(PageScoped.class).toInstance(mock(SourcelistManager.class));
		}
	}

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Before
	public void before() {
		GuiceModuleConfiguration moduleConfiguration = new GuiceModuleConfiguration();
		moduleConfiguration.addAllClassToOmit(SourceListPresenter.class, SourceListView.class);
		setUpAndOverrideMainModule(moduleConfiguration, new CustomGuiceModule());
		instance = injector.getInstance(SourceListModule.class);
		presenter = injector.getInstance(SourceListPresenter.class);
		moduleSocket = injector.getInstance(ModuleSocket.class);
		sourceListModuleStructure = injector.getInstance(SourceListModuleStructure.class);
		bean = mock(SourceListBean.class);
		when(bean.getSourcelistId()).thenReturn(sourcelistId);
		when(sourceListModuleStructure.getBean()).thenReturn(bean);
		reflectionsUtils = new ReflectionsUtils();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void initModuleTest() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		// given
		reflectionsUtils.setValueInObjectOnField("moduleSocket", instance, moduleSocket);
		String idModule = "moduleId";
		reflectionsUtils.setValueInObjectOnField(idModule, instance, idModule);

		YJsonArray state = Mockito.mock(YJsonArray.class);

		when(moduleSocket.getStateById(anyString())).thenReturn(state);

		Element documentElement = XMLParser.parse(SourceListJAXBParserMock.XML).getDocumentElement();
		// when
		instance.initModule(documentElement);
		// then
		InOrder inOrder = inOrder(sourceListModuleStructure, presenter);

		inOrder.verify(sourceListModuleStructure).createFromXml(anyString(), any(YJsonArray.class));
		inOrder.verify(sourceListModuleStructure).getBean();
		inOrder.verify(presenter).setBean(Mockito.any(SourceListBean.class));
		inOrder.verify(presenter).createAndBindUi();

		assertEquals(presenter.asWidget(), instance.getView());
	}

	@Test
	public void testGetItemValue() throws Exception {
		String itemId = "id";
		SourcelistItemValue itemValue = new SourcelistItemValue(SourcelistItemType.IMAGE, "value", itemId);
		instance.getItemValue(itemId);
		verify(presenter).getItemValue(eq(itemId));
	}

	@Test
	public void testUseItem() throws Exception {
		String itemId = "id";
		instance.useItem(itemId);
		verify(presenter).useItem(eq(itemId));
	}

	@Test
	public void testRestockItem() throws Exception {
		String itemId = "id";
		instance.restockItem(itemId);
		verify(presenter).restockItem(eq(itemId));
	}

	@Test
	public void testUseAndRestockItems() throws Exception {
		List<String> items = mock(List.class);

		instance.useAndRestockItems(items);
		verify(presenter).useAndRestockItems(eq(items));
		verifyZeroInteractions(items);
	}

	@Test
	public void testGetView() throws Exception {
		instance.getView();
		verify(presenter).asWidget();
	}

	@Test
	public void testGetIdentifier() throws Exception {
		// given
		Element documentElement = XMLParser.parse(SourceListJAXBParserMock.XML).getDocumentElement();
		instance.initModule(documentElement);
		// when
		String identifier = instance.getIdentifier();
		// then
		assertThat(identifier).isEqualTo(sourcelistId);
	}

	@Test
	public void testLockSourceList() throws Exception {
		instance.lockSourceList();
		verify(presenter).lockSourceList();
		verifyNoMoreInteractions(presenter);
	}

	@Test
	public void testUnlockSourceList() throws Exception {
		instance.unlockSourceList();
		verify(presenter).unlockSourceList();
		verifyNoMoreInteractions(presenter);
	}

	@Test
	public void getItemSize() throws Exception {
		HasDimensions dimension = mock(HasDimensions.class);
		doReturn(dimension).when(presenter).getMaxItemSize();
		HasDimensions itemSize = instance.getItemSize();
		assertThat(dimension).isSameAs(itemSize);

	}

}
