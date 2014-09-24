package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SelectionModuleViewImplTest {

	private Grid grid;
	private Panel panel;
	private ClickHandler clickHandler;
	private SelectionButtonGridElementImpl firstButtonGridElement;
	private SelectionButtonGridElementImpl secondButtonGridElement;
	private SelectionElementGenerator gridGenerator;

	private SelectionModuleViewImpl selectionView;

	@Before
	public void setup() {
		gridGenerator = mock(SelectionElementGenerator.class);

		selectionView = new SelectionModuleViewImpl(gridGenerator);

		selectionView.selectionGrid = grid = mock(Grid.class);
		selectionView.mainPanel = panel = mock(Panel.class);
		selectionView.promptWidget = mock(Widget.class);
		clickHandler = mock(ClickHandler.class);
		firstButtonGridElement = mock(SelectionButtonGridElementImpl.class);
		secondButtonGridElement = mock(SelectionButtonGridElementImpl.class);
	}

	@Test
	public void testSetGridSize() {
		// when
		int rows = 5;
		int cols = 7;

		selectionView.setGridSize(rows, cols);

		// then
		verify(grid).resize(rows + 1, cols + 1);
	}

	@Test
	public void testSetItemDisplayedName() {
		// when
		SelectionGridElementPosition position = new SelectionGridElementPosition(0, 0);
		SelectionItemGridElementImpl gridElement = mock(SelectionItemGridElementImpl.class);
		XMLContent item = new XMLContent() {
			@Override
			public Element getValue() {
				return mock(Element.class);
			}
		};

		when(gridGenerator.createItemDisplayElement(Matchers.any(Element.class))).thenReturn(gridElement);

		selectionView.setItemDisplayedName(item, position);
		// then

		verify(gridGenerator).createItemDisplayElement(Matchers.any(Element.class));
		verify(grid).setWidget(0, 0, gridElement.asWidget());
	}

	@Test
	public void testSetChoiceOptionDisplayedName() {
		// when
		SelectionGridElementPosition position = new SelectionGridElementPosition(0, 0);
		SelectionChoiceGridElementImpl gridElement = mock(SelectionChoiceGridElementImpl.class);
		XMLContent item = new XMLContent() {
			@Override
			public Element getValue() {
				return mock(Element.class);
			}
		};

		when(gridGenerator.createChoiceDisplayElement(Matchers.any(Element.class))).thenReturn(gridElement);

		selectionView.setChoiceOptionDisplayedName(item, position);
		// then

		verify(gridGenerator).createChoiceDisplayElement(Matchers.any(Element.class));
		verify(grid).setWidget(0, 0, gridElement.asWidget());
	}

	@Test
	public void testCreateButtonForItemChoicePair() {
		// when
		SelectionGridElementPosition position = new SelectionGridElementPosition(0, 0);
		String styleName = "styleName";

		when(gridGenerator.createChoiceButtonElement(styleName)).thenReturn(firstButtonGridElement);

		selectionView.createButtonForItemChoicePair(position, styleName);
		// then

		verify(gridGenerator).createChoiceButtonElement(styleName);
		verify(grid).setWidget(0, 0, firstButtonGridElement.asWidget());
	}

	@Test
	public void testAddClickHandlerToButton() {
		// when
		addButtonsToGrid();
		SelectionGridElementPosition firstPosition = new SelectionGridElementPosition(0, 0);
		SelectionGridElementPosition secondPosition = new SelectionGridElementPosition(1, 1);

		selectionView.addClickHandlerToButton(firstPosition, clickHandler);
		selectionView.addClickHandlerToButton(secondPosition, clickHandler);
		// then

		verify(firstButtonGridElement).addClickHandler(clickHandler);
		verify(secondButtonGridElement).addClickHandler(clickHandler);
	}

	@Test
	public void testSelectButton() {
		// when
		addButtonsToGrid();
		SelectionGridElementPosition firstPosition = new SelectionGridElementPosition(0, 0);
		SelectionGridElementPosition secondPosition = new SelectionGridElementPosition(1, 1);

		selectionView.selectButton(firstPosition);
		selectionView.selectButton(secondPosition);
		// then

		verify(firstButtonGridElement).select();
		verify(secondButtonGridElement).select();
	}

	@Test
	public void testUnselectButton() {
		// when
		addButtonsToGrid();
		SelectionGridElementPosition firstPosition = new SelectionGridElementPosition(0, 0);
		SelectionGridElementPosition secondPosition = new SelectionGridElementPosition(1, 1);

		selectionView.unselectButton(firstPosition);
		selectionView.unselectButton(secondPosition);
		// then

		verify(firstButtonGridElement).unselect();
		verify(secondButtonGridElement).unselect();
	}

	@Test
	public void testLockButton() {
		// when
		addButtonsToGrid();
		SelectionGridElementPosition firstPosition = new SelectionGridElementPosition(0, 0);
		SelectionGridElementPosition secondPosition = new SelectionGridElementPosition(1, 1);

		boolean lock = true;

		selectionView.lockButton(firstPosition, lock);
		selectionView.lockButton(secondPosition, lock);
		// then

		verify(firstButtonGridElement).setButtonEnabled(!lock);
		verify(secondButtonGridElement).setButtonEnabled(!lock);
	}

	@Test
	public void testUpdateButtonStyle() {
		// when
		addButtonsToGrid();
		SelectionGridElementPosition firstPosition = new SelectionGridElementPosition(0, 0);
		SelectionGridElementPosition secondPosition = new SelectionGridElementPosition(1, 1);

		UserAnswerType answerType = UserAnswerType.CORRECT;

		selectionView.updateButtonStyle(firstPosition, answerType);
		selectionView.updateButtonStyle(secondPosition, answerType);
		// then

		verify(firstButtonGridElement).updateStyle(answerType);
		verify(secondButtonGridElement).updateStyle(answerType);
	}

	@Test
	public void testAsWidget() {
		// then
		Assert.assertEquals(selectionView.asWidget(), panel);

	}

	private void addButtonsToGrid() {
		SelectionGridElementPosition firstPosition = new SelectionGridElementPosition(0, 0);
		SelectionGridElementPosition secondPosition = new SelectionGridElementPosition(1, 1);
		String firstStyleName = "styleName1";
		String secondStyleName = "styleName2";

		when(gridGenerator.createChoiceButtonElement(firstStyleName)).thenReturn(firstButtonGridElement);
		when(gridGenerator.createChoiceButtonElement(secondStyleName)).thenReturn(secondButtonGridElement);

		selectionView.createButtonForItemChoicePair(firstPosition, firstStyleName);
		selectionView.createButtonForItemChoicePair(secondPosition, secondStyleName);
	}
}
