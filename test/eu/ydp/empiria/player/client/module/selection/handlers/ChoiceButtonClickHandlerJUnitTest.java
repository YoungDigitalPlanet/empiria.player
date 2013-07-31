package eu.ydp.empiria.player.client.module.selection.handlers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.event.dom.client.ClickEvent;

import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;

public class ChoiceButtonClickHandlerJUnitTest {

	private ChoiceButtonClickHandler handler;
	private GroupAnswersController groupAnswerController;
	private String buttonId;
	private SelectionModulePresenter selectionModulePresenter;

	@Before
	public void setUp() throws Exception {
		groupAnswerController = mock(GroupAnswersController.class);
		selectionModulePresenter = mock(SelectionModulePresenter.class);
		handler = new ChoiceButtonClickHandler(groupAnswerController, buttonId, selectionModulePresenter);
	}

	@After
	public void tearDown() throws Exception {
		Mockito.verifyNoMoreInteractions(groupAnswerController, selectionModulePresenter);
	}

	@Test
	public void testOnClick() {
		ClickEvent event = mock(ClickEvent.class);

		//then
		handler.onClick(event);

		verify(groupAnswerController).selectToggleAnswer(buttonId);
		verify(selectionModulePresenter).updateGroupAnswerView(groupAnswerController);
	}

}
