package eu.ydp.empiria.player.client.controller.multiview;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.view.ViewStyleNameConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collection;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class MultiPageControllerStyleManagerTest {

    @InjectMocks
    private MultiPageControllerStyleManager testObj;
    @Mock
    private ViewStyleNameConstants styleNames;
    @Mock
    private FlowPanel keyPanel1;
    @Mock
    private FlowPanel keyPanel2;

    final String UNSELECTED_STYLE = "QP_PAGE_UNSELECTED";
    final String PAGE_SELECTED_STYLE = "QP_PAGE_SELECTED";
    final String PREVIOUS_PAGE_STYLE = "QP_PAGE_PREV";
    final String NEXT_PAGE_STYLE = "QP_PAGE_NEXT";

    @Before
    public void setUp() throws Exception {
        when(styleNames.QP_PAGE_UNSELECTED()).thenReturn(UNSELECTED_STYLE);
        when(styleNames.QP_PAGE_SELECTED()).thenReturn(PAGE_SELECTED_STYLE);
        when(styleNames.QP_PAGE_PREV()).thenReturn(PREVIOUS_PAGE_STYLE);
        when(styleNames.QP_PAGE_NEXT()).thenReturn(NEXT_PAGE_STYLE);
    }

    @Test
    public void shouldClearPagesStyles() {
        // given
        Collection<FlowPanel> keyPanels = Lists.newArrayList(keyPanel1, keyPanel2);

        // when
        testObj.clearPagesStyles(keyPanels);

        // then
        verify(keyPanel1).removeStyleName(UNSELECTED_STYLE);
        verify(keyPanel1).removeStyleName(PAGE_SELECTED_STYLE);
        verify(keyPanel1).removeStyleName(PREVIOUS_PAGE_STYLE);
        verify(keyPanel1).removeStyleName(NEXT_PAGE_STYLE);

        verify(keyPanel2).removeStyleName(UNSELECTED_STYLE);
        verify(keyPanel2).removeStyleName(PAGE_SELECTED_STYLE);
        verify(keyPanel2).removeStyleName(PREVIOUS_PAGE_STYLE);
        verify(keyPanel2).removeStyleName(NEXT_PAGE_STYLE);
    }

    @Test
    public void shouldSetStylesForNextPage() {
        // given
        boolean isChangeToNextPage = true;

        // when
        testObj.setPageStyles(keyPanel1, isChangeToNextPage);

        // then
        verify(keyPanel1).addStyleName(UNSELECTED_STYLE);
        verify(keyPanel1).addStyleName(NEXT_PAGE_STYLE);
    }

    @Test
    public void shouldSetStylesForPreviousPage() {
        // given
        boolean isChangeToNextPage = false;

        // when
        testObj.setPageStyles(keyPanel1, isChangeToNextPage);

        // then
        verify(keyPanel1).addStyleName(UNSELECTED_STYLE);
        verify(keyPanel1).addStyleName(PREVIOUS_PAGE_STYLE);
    }
}
