package eu.ydp.empiria.player.client.module.ordering.view.items;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest({ Widget.class })
public class OrderInteractionViewItemStylesImplJUnitTest extends AbstractTestBase {

	private OrderInteractionViewItemStyles instance;
	private StyleNameConstants styleNames;
	private final OrderInteractionViewItem viewItem = mock(OrderInteractionViewItem.class);
	private final Widget widget = mock(Widget.class);

	Map<UserAnswerType, String> stylesMap = new ImmutableMap.Builder<UserAnswerType, String>().put(UserAnswerType.CORRECT, "qp-ordered-item-correct")
			.put(UserAnswerType.WRONG, "qp-ordered-item-wrong").put(UserAnswerType.NONE, "qp-ordered-item-none")
			.put(UserAnswerType.DEFAULT, "qp-ordered-item-default").build();

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
		instance = injector.getInstance(OrderInteractionViewItemStylesImpl.class);
		styleNames = injector.getInstance(StyleNameConstants.class);
		when(viewItem.asWidget()).thenReturn(widget);
	}

	@Test
	public void postConstruct() throws Exception {
		verify(styleNames).QP_ORDERED_ITEM_CORRECT();
		verify(styleNames).QP_ORDERED_ITEM_WRONG();
		verify(styleNames).QP_ORDERED_ITEM_NONE();
		verify(styleNames).QP_ORDERED_ITEM_DEFAULT();
		verifyNoMoreInteractions(styleNames);
	}

	@Test
	public void applyStylesOnWidgetUserAnswerType() throws Exception {
		OrderingItem item = new OrderingItem("id", "ans");
		for (UserAnswerType type : UserAnswerType.values()) {
			item.setAnswerType(type);
			instance.applyStylesOnWidget(item, viewItem);
			ArgumentCaptor<String> styleNameCaptor = ArgumentCaptor.forClass(String.class);
			verify(widget).setStyleName(styleNameCaptor.capture());
			assertThat(styleNameCaptor.getValue().split(" ")).containsExactly(styleNames.QP_ORDERED_ITEM(), stylesMap.get(type));
			reset(widget);
		}
	}

	@Test
	public void applyStylesOnWidgetUserAnswerTypeAndIsLocked() throws Exception {
		OrderingItem item = new OrderingItem("id", "ans");
		item.setLocked(true);
		String nameOfrequiredStyle = "qp-ordered-item-locked";
		assertStyleNames(item, nameOfrequiredStyle);
	}

	@Test
	public void applyStylesOnWidgetUserAnswerTypeAndIsSelected() throws Exception {
		OrderingItem item = new OrderingItem("id", "ans");
		item.setSelected(true);
		String nameOfrequiredStyle = "qp-ordered-item-selected";
		assertStyleNames(item, nameOfrequiredStyle);
	}

	private void assertStyleNames(OrderingItem item, String nameOfrequiredStyle) {
		for (UserAnswerType type : UserAnswerType.values()) {
			item.setAnswerType(type);
			instance.applyStylesOnWidget(item, viewItem);
			ArgumentCaptor<String> styleName = ArgumentCaptor.forClass(String.class);
			verify(widget).setStyleName(styleName.capture());

			assertThat(styleName.getValue()).contains(stylesMap.get(type)).containsOnlyOnce(nameOfrequiredStyle);
			reset(widget);
		}
	}
}
