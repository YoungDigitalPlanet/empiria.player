package eu.ydp.empiria.player.client.module.progressbonus.presenter;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.module.progressbonus.view.ProgressBonusView;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class ProgressBonusPresenterTest {

	@InjectMocks
	ProgressBonusPresenter presenter;
	@Mock
	ProgressBonusView view;

	@Test
	public void shouldSetImageOnView() {
		// given
		ShowImageDTO image = new ShowImageDTO("PATH", new Size(200, 300));

		// when
		presenter.showImage(image);

		// then
		verify(view).showImage(image);
	}
}
