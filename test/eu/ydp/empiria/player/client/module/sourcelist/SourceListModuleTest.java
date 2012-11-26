package eu.ydp.empiria.player.client.module.sourcelist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListViewMock;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParserMock;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.gwtutil.xml.XMLParser;

@SuppressWarnings("PMD")
public class SourceListModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private SourceListModule instance;
	private SourceListPresenter presenter;
	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(SourceListPresenter.class).toInstance(spy(new SourceListPresenterMock()));
			binder.bind(SourceListView.class).toInstance(spy(new SourceListViewMock()));
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
	public void before(){
		setUp(new Class<?>[] { SourceListPresenter.class,SourceListView.class }, new CustomGuiceModule());
		instance = injector.getInstance(SourceListModule.class);
		presenter = injector.getInstance(SourceListPresenter.class);

	}

	@Test
	public void testFactoryMethod(){
		assertNotNull(instance.getNewInstance());
	}

	@Test
	public void presenterSetBeanTest(){
		instance.initModule(XMLParser.parse(SourceListJAXBParserMock.XML).getDocumentElement());
		Mockito.verify(presenter).setBean(Mockito.any(SourceListBean.class));
	}

	@Test
	public void getViewTest(){
		instance.initModule(XMLParser.parse(SourceListJAXBParserMock.XML).getDocumentElement());
		assertEquals(presenter.asWidget(),instance.getView());
	}

	@Test
	public void initPresenterTest(){
		instance.initModule(XMLParser.parse(SourceListJAXBParserMock.XML).getDocumentElement());
		assertEquals(presenter.asWidget(),instance.getView());
		Mockito.verify(presenter).createAndBindUi();
	}

	@Test
	public void containsValueTest() {
		when(presenter.containsValue(Mockito.anyString())).thenReturn(true);
		assertTrue(instance.containsValue("test"));
		when(presenter.containsValue(Mockito.anyString())).thenReturn(false);
		assertFalse(instance.containsValue("test"));
	}

}
