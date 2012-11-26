package eu.ydp.empiria.player.client.util.dom.drag;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.util.dom.drag.html5.HTML5DragDrop;
import eu.ydp.gwtutil.client.util.BrowserNativeInterface;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest({ Element.class, Widget.class, HTML5DragDrop.class })
@SuppressWarnings("PMD")
public class HTML5DragDropTest {

	HTML5DragDrop instance;
	Widget widget;

	public void before(String userAgent) {
		UserAgentChecker.setNativeInterface(fillMock(userAgent));
		widget = mock(Widget.class);
		Element element = mock(Element.class);
		when(widget.getElement()).thenReturn(element);
		IModule module = mock(IModule.class);
		instance = new HTML5DragDrop(widget, module,DragDropType.DRAG, false);

	}

	@Before
	public void before() {
		before("Mozilla/5.0 (Windows NT 6.1; rv:15.0) Gecko/20120716 Firefox/15.0a2");
	}

	private BrowserNativeInterface fillMock(String userAgent) {
		BrowserNativeInterface nativeInterface = Mockito.mock(BrowserNativeInterface.class);
		Mockito.when(nativeInterface.getUserAgentStrting()).thenReturn(userAgent);
		Mockito.when(nativeInterface.isLocal()).thenReturn(false);
		Mockito.when(nativeInterface.isUserAgent(Mockito.any(String.class), Mockito.any(String.class))).then(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Pattern pattern = Pattern.compile(String.valueOf(args[0]));
				return pattern.matcher(String.valueOf(args[1])).find();
			}
		});
		return nativeInterface;
	}

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	public void verifyDragHandlers(HTML5DragDrop instance, Widget widget) {
		DragEndHandler dragEndHandler = mock(DragEndHandler.class);
		instance.addDragEndHandler(dragEndHandler);
		verify(widget).addDomHandler(Mockito.eq(dragEndHandler), Mockito.any(Type.class));
		Mockito.reset(widget);
		// drag enter
		DragEnterHandler dragEnterHandler = mock(DragEnterHandler.class);
		instance.addDragEnterHandler(dragEnterHandler);
		verify(widget).addDomHandler(Mockito.eq(dragEnterHandler), Mockito.any(Type.class));
		Mockito.reset(widget);
		// drag leave
		DragLeaveHandler dragLeaveHandler = mock(DragLeaveHandler.class);
		instance.addDragLeaveHandler(dragLeaveHandler);
		verify(widget).addDomHandler(Mockito.eq(dragLeaveHandler), Mockito.any(Type.class));
		Mockito.reset(widget);
		// drag over
		DragOverHandler dragOverHandler = mock(DragOverHandler.class);
		instance.addDragOverHandler(dragOverHandler);
		verify(widget).addDomHandler(Mockito.eq(dragOverHandler), Mockito.any(Type.class));
		Mockito.reset(widget);
		// drag start
		DragStartHandler dragStartHandler = mock(DragStartHandler.class);
		instance.addDragStartHandler(dragStartHandler);
		verify(widget).addDomHandler(Mockito.eq(dragStartHandler), Mockito.any(Type.class));
		Mockito.reset(widget);

	}

	@Test
	public void dragStartStopAddEventTest() {
		verifyDragHandlers(instance, widget);
	}

//	@Test ie disable html5 drag drop emulated instead
//	public void ie8_ie9_DragStartStopAddEventTest() {
//		before("Mozilla/5.0 (Windows; U; MSIE 9.0; WIndows NT 9.0; en-US))");
//		// ie mouse down
//		verify(widget).addDomHandler(Mockito.any(MouseDownHandler.class), Mockito.any(Type.class));
//		verifyDragHandlers(instance, widget);
//	}

	@Test
	public void dropAddEventTest() {
		DropHandler dropHandler = mock(DropHandler.class);
		instance.addDropHandler(dropHandler);
		verify(widget).addDomHandler(Mockito.eq(dropHandler), Mockito.any(Type.class));
		Mockito.reset(widget);
	}
}
