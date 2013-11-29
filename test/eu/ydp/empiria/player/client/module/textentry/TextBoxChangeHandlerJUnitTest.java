package eu.ydp.empiria.player.client.module.textentry;

import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.TextBox;

import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

public class TextBoxChangeHandlerJUnitTest {

	@Mock
	private DroppableObject<TextBox> object;
	@Mock
	private TextBox textBox;
	@Mock
	private PresenterHandler presenterHandler;
	
	@InjectMocks
	private TextBoxChangeHandler testObj;

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
		when(object.getOriginalWidget()).thenReturn(textBox);
	}

	@Test
	public void bindNoPresenterHandlerTest(){
		// when
		testObj.bind(object, null);
		
		// then
		verifyZeroInteractions(object);
	}

	@Test
	public void bindTest(){
		// when
		testObj.bind(object, presenterHandler);
		
		// then
		verify(textBox).addBlurHandler(Mockito.eq(testObj));
		verify(object).addDropHandler(Mockito.eq(testObj));
	}


	@Test
	public void onDropTest(){
		// given
		DropEvent event = mock(DropEvent.class);

		// when
		testObj.bind(object, presenterHandler);
		testObj.onDrop(event);
		
		// then
		verify(presenterHandler).onBlur(Mockito.any(BlurEvent.class));
	}

	@Test
	public void onBlurTest(){
		// given
		BlurEvent blurEvent = mock(BlurEvent.class);

		// when
		testObj.bind(object, presenterHandler);
		testObj.onBlur(blurEvent);
		
		// then
		verify(presenterHandler).onBlur(Mockito.eq(blurEvent));
		verifyNoMoreInteractions(presenterHandler);
	}

}
