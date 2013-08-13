package eu.ydp.empiria.player.client.module.button.download.presenter;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.button.download.structure.ButtonBean;
import eu.ydp.empiria.player.client.module.button.download.view.ButtonModuleView;

@RunWith(MockitoJUnitRunner.class)
public class ButtonModulePresenterTest {
	private static final String ID = "idd";
	private static final String ALT = "alt";
	private static final String URL = "http";
	@Mock private ButtonModuleView view;
	@InjectMocks private ButtonModulePresenter instance;
	private ButtonBean bean;


	@Before
	public void before() {
		bean = new ButtonBean();
		bean.setHref(URL);
		bean.setAlt(ALT);
		bean.setId(ID);
	}

	@Test
	public void init(){
		instance.setBean(bean);
		instance.init();
		verify(view).setUrl(eq(URL));
		verify(view).setDescription(eq(ALT));
		verify(view).setId(eq(ID));
		verifyNoMoreInteractions(view);
	}

	@Test
	public void asWidget(){
		instance.asWidget();
		verify(view).asWidget();
	}

}
