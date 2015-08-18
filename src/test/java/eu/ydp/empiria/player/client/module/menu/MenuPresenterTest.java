package eu.ydp.empiria.player.client.module.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.menu.view.MenuStyleNameConstants;
import eu.ydp.empiria.player.client.module.menu.view.MenuView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class MenuPresenterTest {

    @InjectMocks
    private MenuPresenter testObj;
    @Mock
    private MenuView view;
    @Mock
    private MenuStyleNameConstants styleNameConstants;
    @Mock
    private ClickEvent clickEvent;
    @Mock
    private FlexTable flexTable;
    @Mock
    private HTMLTable.RowFormatter rowFormatter;
    @Captor
    private ArgumentCaptor<ClickHandler> commandCaptor;
    private String qpMenuHidden = "qp-menu-hidden";
    private String qpMenuTableCurrentRow = "current-row";

    @Before
    public void init() {
        when(styleNameConstants.QP_MENU_HIDDEN()).thenReturn(qpMenuHidden);
        when(styleNameConstants.QP_MENU_TABLE_CURRENT_ROW()).thenReturn(qpMenuTableCurrentRow);

        verify(view).addClickHandler(commandCaptor.capture());
        when(flexTable.getRowFormatter()).thenReturn(rowFormatter);
        testObj.setTable(flexTable);
    }

    @Test
    public void shouldShowMenu_onFirstClick() {
        // given
        ClickHandler clickHandler = commandCaptor.getValue();

        // when
        clickHandler.onClick(clickEvent);

        // then
        verify(view).removeStyleName(qpMenuHidden);
    }

    @Test
    public void shouldHideMenu_onSecondClick() {
        // given
        ClickHandler clickHandler = commandCaptor.getValue();

        // when
        clickHandler.onClick(clickEvent);
        clickHandler.onClick(clickEvent);

        // then
        verify(view).addStyleName(qpMenuHidden);
    }

    @Test
    public void shouldAddStyleToRow_whenIsValid() {
        // given
        int rowCount = 5;
        int rowToMark = 2;
        when(flexTable.getRowCount()).thenReturn(rowCount);

        // when
        testObj.markRow(rowToMark);

        // then
        verify(rowFormatter).addStyleName(rowToMark, qpMenuTableCurrentRow);
    }

    @Test
    public void shouldRemoveStyleFromRow_whenIsValid() {
        // given
        int rowCount = 5;
        int rowToMark = 2;
        when(flexTable.getRowCount()).thenReturn(rowCount);

        // when
        testObj.unmarkRow(rowToMark);

        // then
        verify(rowFormatter).removeStyleName(rowToMark, qpMenuTableCurrentRow);
    }

    @Test
    public void shouldNotChangeStyles_whenRowIsNegative() {
        // given
        int rowCount = 5;
        int rowToMark = -1;
        when(flexTable.getRowCount()).thenReturn(rowCount);

        // when
        testObj.markRow(rowToMark);
        testObj.unmarkRow(rowToMark);

        // then
        verifyZeroInteractions(rowFormatter);
    }

    @Test
    public void shouldNotChangeStyles_whenRowNumberIsBigger_thanRowCount() {
        // given
        int rowCount = 5;
        int rowToMark = 10;
        when(flexTable.getRowCount()).thenReturn(rowCount);

        // when
        testObj.markRow(rowToMark);
        testObj.unmarkRow(rowToMark);

        // then
        verifyZeroInteractions(rowFormatter);
    }
}