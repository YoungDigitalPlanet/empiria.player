package eu.ydp.empiria.player.client.module.ordering.view.items;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockito;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class OrderInteractionViewItemStylesImplJUnitTest {

	private OrderInteractionViewItemStylesImpl instance;
	@Mock
	private StyleNameConstants styleNames;
	@Mock
	private OrderInteractionViewItem viewItem;
	@Mock
	private Widget widget;
	@Captor
	private ArgumentCaptor<String> captor;

	private static final String QP_ORDERED_ITEM = "QP_ORDERED_ITEM";
	private static final String QP_ORDERED_ITEM_CORRECT = "QP_ORDERED_ITEM_CORRECT";
	private static final String QP_ORDERED_ITEM_WRONG = "QP_ORDERED_ITEM_WRONG";
	private static final String QP_ORDERED_ITEM_NONE = "QP_ORDERED_ITEM_NONE";
	private static final String QP_ORDERED_ITEM_DEFAULT = "QP_ORDERED_ITEM_DEFAULT";
	private static final String QP_ORDERED_ITEM_LOCKED = "QP_ORDERED_ITEM_LOCKED";
	private static final String QP_ORDERED_ITEM_SELECTED = "QP_ORDERED_ITEM_SELECTED";

	@Before
	public void before() {
		GwtMockito.initMocks(this);

		when(viewItem.asWidget()).thenReturn(widget);

		when(styleNames.QP_ORDERED_ITEM()).thenReturn(QP_ORDERED_ITEM);
		when(styleNames.QP_ORDERED_ITEM_CORRECT()).thenReturn(QP_ORDERED_ITEM_CORRECT);
		when(styleNames.QP_ORDERED_ITEM_WRONG()).thenReturn(QP_ORDERED_ITEM_WRONG);
		when(styleNames.QP_ORDERED_ITEM_NONE()).thenReturn(QP_ORDERED_ITEM_NONE);
		when(styleNames.QP_ORDERED_ITEM_DEFAULT()).thenReturn(QP_ORDERED_ITEM_DEFAULT);
		when(styleNames.QP_ORDERED_ITEM_LOCKED()).thenReturn(QP_ORDERED_ITEM_LOCKED);
		when(styleNames.QP_ORDERED_ITEM_SELECTED()).thenReturn(QP_ORDERED_ITEM_SELECTED);

		instance = new OrderInteractionViewItemStylesImpl(styleNames);
	}

	@After
	public void tearDown() throws Exception {
		GwtMockito.tearDown();
	}

	Object[] answerTypeStylePairs() {
		return $(
				$(UserAnswerType.CORRECT, QP_ORDERED_ITEM_CORRECT),
				$(UserAnswerType.WRONG, QP_ORDERED_ITEM_WRONG),
				$(UserAnswerType.NONE, QP_ORDERED_ITEM_NONE),
				$(UserAnswerType.DEFAULT, QP_ORDERED_ITEM_DEFAULT));
	}

	@Test
	@Parameters(method = "answerTypeStylePairs")
	public void shouldApplyStylesOnWidgetUserAnswerType(UserAnswerType type, String expectedStyle) {
		// given
		OrderingItem item = new OrderingItem("id", "ans");
		item.setAnswerType(type);

		// when
		instance.applyStylesOnWidget(item, viewItem);

		// then

		verify(widget).setStyleName(captor.capture());
		assertThat(captor.getValue()
						 .split(" ")).containsExactly(QP_ORDERED_ITEM, expectedStyle);
	}

	@Test
	@Parameters(method = "answerTypeStylePairs")
	public void shouldApplyStylesOnWidgetUserAnswerType_isLocked(UserAnswerType type, String expectedStyle) {
		// given
		OrderingItem item = new OrderingItem("id", "ans");
		item.setLocked(true);

		item.setAnswerType(type);

		// when
		instance.applyStylesOnWidget(item, viewItem);

		// then
		verify(widget).setStyleName(captor.capture());
		assertThat(captor.getValue()).contains(expectedStyle)
									 .containsOnlyOnce(QP_ORDERED_ITEM_LOCKED);
	}

	@Test
	@Parameters(method = "answerTypeStylePairs")
	public void shouldApplyStylesOnWidgetUserAnswerType_isSelected(UserAnswerType type, String expectedStyle) {
		// given
		OrderingItem item = new OrderingItem("id", "ans");
		item.setSelected(true);
		item.setAnswerType(type);

		// when
		instance.applyStylesOnWidget(item, viewItem);

		// then
		verify(widget).setStyleName(captor.capture());
		assertThat(captor.getValue()).contains(expectedStyle)
									 .containsOnlyOnce(QP_ORDERED_ITEM_SELECTED);
	}
}
