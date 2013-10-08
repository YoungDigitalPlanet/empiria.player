package eu.ydp.empiria.player.client.module.button.download.view;

import static org.mockito.Matchers.eq;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest({Composite.class,Widget.class,Element.class,DivElement.class})
public class ButtonModuleViewImplTest {
	@Mock private DivElement description;
	@Mock private Element element;
	@Mock private Anchor anchor;
	@InjectMocks private final ButtonModuleViewImpl instance = spy(new ButtonModuleViewImpl());

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
		MockitoAnnotations.initMocks(this);
		doReturn(element).when(instance).getElement();
	}

	@Test
	public void setId(){
		String id = "id";
		instance.setId(id);
		verify(element).setId(eq(id));
	}

	@Test
	public void setDescription(){
		String descriptionText = "ddesc";
		instance.setDescription(descriptionText);
		verify(description).setInnerText(eq(descriptionText));
	}

	@Test
	public void setUrl(){
		String url = "url";
		instance.setUrl(url);
		verify(anchor).setHref(eq(url));
	}
	
	@Test
	public void addAnchorClickHandler() {
//		given
		ClickHandler clickHandler = mock(ClickHandler.class);
		
//		when
		instance.addAnchorClickHandler(clickHandler);
		
//		then
		verify(anchor).addClickHandler(clickHandler);
	}
}
