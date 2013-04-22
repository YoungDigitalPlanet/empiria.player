package eu.ydp.empiria.player.client.module.sourcelist;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

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
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListViewMock;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParserMock;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
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

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(SourceListPresenter.class).toInstance(spy(new SourceListPresenterMock()));
			binder.bind(SourceListView.class).toInstance(spy(new SourceListViewMock()));
			binder.bind(ModuleSocket.class).toInstance(mock(ModuleSocket.class));
			binder.bind(SourceListModuleStructure.class).toInstance(mock(SourceListModuleStructure.class));
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
		setUp(new Class<?>[] { SourceListPresenter.class, SourceListView.class }, new CustomGuiceModule());
		instance = injector.getInstance(SourceListModule.class);
		presenter = injector.getInstance(SourceListPresenter.class);
		moduleSocket = injector.getInstance(ModuleSocket.class);
		sourceListModuleStructure = injector.getInstance(SourceListModuleStructure.class);
		reflectionsUtils = new ReflectionsUtils();

	}

	@Test
	public void testFactoryMethod() {
		assertNotNull(instance.getNewInstance());
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
	public void containsValueTest() {
		when(presenter.containsValue(Mockito.anyString())).thenReturn(true);
		assertTrue(instance.containsValue("test"));
		when(presenter.containsValue(Mockito.anyString())).thenReturn(false);
		assertFalse(instance.containsValue("test"));
	}

}
