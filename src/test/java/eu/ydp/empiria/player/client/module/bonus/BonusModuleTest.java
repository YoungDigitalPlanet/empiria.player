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

package eu.ydp.empiria.player.client.module.bonus;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.variables.processor.FeedbackActionConditions;
import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackMediator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BonusModuleTest {

    @InjectMocks
    private BonusModule module;

    @Mock
    private FeedbackActionConditions actionConditions;
    @Mock
    private OutcomeAccessor outcomeAccessor;
    @Mock
    private PowerFeedbackMediator mediator;
    @Mock
    private BonusProvider bonusProvider;
    @Mock
    private BonusPopupPresenter bonusPopupPresenter;

    @Test
    public void shouldNotReturnAnyView() {
        // when
        Widget view = module.getView();

        // then
        assertThat(view).isNull();
    }

    @Test
    public void shouldRegisterInMediator() {
        // given
        Element element = mock(Element.class);

        // when
        module.initModule(element);

        // then
        verify(mediator).registerBonus(module);
    }

    @Test
    public void shouldCloseBonusOnTerminate() {
        // when
        module.terminatePowerFeedback();

        // then
        verify(bonusPopupPresenter).closeClicked();
    }

    @Test
    public void shouldExecuteBonus_firstTime_pageAllOk() {
        // given
        Bonus bonus = mock(Bonus.class);
        when(bonusProvider.next()).thenReturn(bonus);

        mockAllOk();

        // when
        module.processUserInteraction();

        // then
        verify(bonus).execute();
    }

    @Test
    public void shouldNotExecuteBonus_firstTime_pageNotAllOk() {
        // given
        Bonus bonus = mock(Bonus.class);
        when(bonusProvider.next()).thenReturn(bonus);
        mockNotAllOk();

        // when
        module.processUserInteraction();

        // then
        verify(bonus, never()).execute();
    }

    @Test
    public void shouldExecuteBonus_OnlyOnce() {
        // given
        Bonus bonus = mock(Bonus.class);
        when(bonusProvider.next()).thenReturn(bonus);
        mockAllOk();

        // when
        module.processUserInteraction();
        module.processUserInteraction();

        // then
        verify(bonus).execute();
    }

    @Test
    public void shouldExecuteBonus_TwiceAfterReset() {
        // given
        Bonus bonus = mock(Bonus.class);
        when(bonusProvider.next()).thenReturn(bonus);
        mockAllOk();

        // when
        module.processUserInteraction();
        module.resetPowerFeedback();
        module.processUserInteraction();

        // then
        verify(bonus, times(2)).execute();
    }

    @Test
    public void shouldNotMarkAsDoneOnNotOk() {
        // given
        Bonus bonus = mock(Bonus.class);
        when(bonusProvider.next()).thenReturn(bonus);

        // when
        mockNotAllOk();
        module.processUserInteraction();
        mockAllOk();
        module.processUserInteraction();

        // then
        verify(bonus).execute();
    }

    @Test
    public void shouldShowOnceWhenHadNoPreviousErrors() {
        // given
        Bonus bonus = mock(Bonus.class);
        when(bonusProvider.next()).thenReturn(bonus);

        // when
        mockNotAllOk();
        module.processUserInteraction();

        mockAllOk();
        module.processUserInteraction();

        // then
        verify(bonus, times(1)).execute();
    }

    @Test
    public void shouldNotShowBonusWhenHadPreviousErrors() {
        // given
        Bonus bonus = mock(Bonus.class);
        when(bonusProvider.next()).thenReturn(bonus);

        // when
        mockNotAllOk();
        when(outcomeAccessor.getCurrentPageMistakes()).thenReturn(1);
        module.processUserInteraction();

        mockAllOk();
        module.processUserInteraction();

        // then
        verify(bonus, never()).execute();
    }

    @Test
    public void shouldShowBonusAfterResetWithPreviousErrors() {
        // given
        Bonus bonus = mock(Bonus.class);
        when(bonusProvider.next()).thenReturn(bonus);

        // when
        mockNotAllOk();
        when(outcomeAccessor.getCurrentPageMistakes()).thenReturn(1);
        module.processUserInteraction();

        module.resetPowerFeedback();

        mockAllOk();
        module.processUserInteraction();

        // then
        verify(bonus, times(1)).execute();

    }

    private void mockAllOk() {
        when(actionConditions.isPageAllOkWithoutPreviousErrors()).thenReturn(true);
    }

    private void mockNotAllOk() {
        when(actionConditions.isPageAllOkWithoutPreviousErrors()).thenReturn(false);
    }
}
