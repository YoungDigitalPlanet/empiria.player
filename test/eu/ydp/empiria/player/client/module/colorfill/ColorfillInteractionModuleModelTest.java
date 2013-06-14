package eu.ydp.empiria.player.client.module.colorfill;

import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("PMD")
public class ColorfillInteractionModuleModelTest {

	@InjectMocks
	ColorfillInteractionModuleModel model;

	@Mock
	Response response;

	@Mock
	ResponseModelChangeListener modelChangeListener;

	@Test
	public void setNewUserAnswers() {
		model.initialize(modelChangeListener);
		model.setNewUserAnswers(new ArrayList<String>());

		verify(modelChangeListener).onResponseModelChange();
	}
}
