/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.report.table.ReportTable;
import eu.ydp.empiria.player.client.module.menu.view.MenuStyleNameConstants;
import eu.ydp.empiria.player.client.module.menu.view.MenuView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private ReportTable reportTable;
    @Captor
    private ArgumentCaptor<ClickHandler> commandCaptor;
    private String qpMenuHidden = "qp-menu-hidden";
    private String qpMenuTableCurrentRow = "current-row";

    @Before
    public void init() {
        when(styleNameConstants.QP_MENU_HIDDEN()).thenReturn(qpMenuHidden);
        when(styleNameConstants.QP_MENU_TABLE_CURRENT_ROW()).thenReturn(qpMenuTableCurrentRow);

        verify(view).addClickHandler(commandCaptor.capture());
        testObj.setReportTable(reportTable);
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
        int pageToMark = 2;

        // when
        testObj.markPage(pageToMark);

        // then
        verify(reportTable).addRowStyleName(pageToMark, qpMenuTableCurrentRow);
    }

    @Test
    public void shouldRemoveStyleFromRow_whenIsValid() {
        // given
        int pageToMark = 2;

        // when
        testObj.unmarkPage(pageToMark);

        // then
        verify(reportTable).removeRowStyleName(pageToMark, qpMenuTableCurrentRow);
    }
}