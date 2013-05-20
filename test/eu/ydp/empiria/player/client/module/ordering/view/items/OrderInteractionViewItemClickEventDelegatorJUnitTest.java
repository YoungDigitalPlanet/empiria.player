package eu.ydp.empiria.player.client.module.ordering.view.items;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.ordering.view.OrderItemClickListener;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest({Widget.class})
public class OrderInteractionViewItemClickEventDelegatorJUnitTest {

	private final OrderInteractionViewItemClickEventDelegator instance = new OrderInteractionViewItemClickEventDelegator();

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Test
	public void bindNoClickListener() throws Exception {
		OrderInteractionViewItem item = mock(OrderInteractionViewItem.class);
		instance.bind(item, null);
		verifyZeroInteractions(item);
	}

	@Test
	public void bind(){
		OrderInteractionViewItem item = mock(OrderInteractionViewItem.class);
		when(item.getItemId()).thenReturn("id");
		Widget widget = mock(Widget.class);
	    when(item.asWidget()).thenReturn(widget);
		ArgumentCaptor<ClickHandler> argumentCaptor = ArgumentCaptor.forClass(ClickHandler.class);
		OrderItemClickListener itemClickListener = mock(OrderItemClickListener.class);
		//test
		instance.bind(item, itemClickListener);
		//verify

		verify(widget).addDomHandler(argumentCaptor.capture(), Mockito.eq(ClickEvent.getType()));
		argumentCaptor.getValue().onClick(null);
		verify(itemClickListener).itemClicked(Mockito.eq("id"));
	}

}
