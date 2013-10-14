package eu.ydp.empiria.player.client.module.bonus;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.variables.processor.FeedbackActionConditions;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackMediator;

@RunWith(MockitoJUnitRunner.class)
public class BonusModuleTest {

	@InjectMocks
	private BonusModule module;

	@Mock
	private FeedbackActionConditions actionConditions;
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

	private void mockAllOk() {
		when(actionConditions.isPageAllOkWithoutPreviousErrors()).thenReturn(true);
	}

	private void mockNotAllOk() {
		when(actionConditions.isPageAllOkWithoutPreviousErrors()).thenReturn(false);
	}
}
