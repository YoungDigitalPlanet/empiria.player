package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParserMock;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;

@SuppressWarnings("PMD")
public class SourceListPresenterTest extends AbstractTestBaseWithoutAutoInjectorInit {
	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(SourceListPresenter.class).to(SourceListPresenterImpl.class);
			binder.bind(SourceListView.class).toInstance(spy(new SourceListViewMock()));
		}
	}

	private SourceListPresenter instance;
	private SourceListView view;
	private SourceListJAXBParserMock sourceListJAXBParserMock;

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
		setUp(new Class<?>[] { SourceListPresenter.class,SourceListView.class }, new CustomGuiceModule());
		instance = injector.getInstance(SourceListPresenter.class);
		view = injector.getInstance(SourceListView.class);
		sourceListJAXBParserMock = new SourceListJAXBParserMock();
	}

	@Test
	public void getViewTest() {
		assertEquals(instance.asWidget(), view.asWidget());
	}

	@Test
	public void setBeanTest() {
		SourceListBean bean = sourceListJAXBParserMock.create().parse(SourceListJAXBParserMock.XML);
		instance.setBean(bean);
		Mockito.verify(view).setBean(bean);
	}

	@Test
	public void containsValueTest() {
		SourceListBean bean = sourceListJAXBParserMock.create().parse(SourceListJAXBParserMock.XML);
		instance.setBean(bean);
		instance.createAndBindUi();
		when(view.containsValue(Mockito.anyString())).thenReturn(true);
		assertTrue(instance.containsValue("test"));
		when(view.containsValue(Mockito.anyString())).thenReturn(false);
		assertFalse(instance.containsValue("test"));
	}


}
