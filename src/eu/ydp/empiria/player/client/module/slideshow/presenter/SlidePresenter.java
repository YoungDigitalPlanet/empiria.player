package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SlidePresenter {

	private final SlideView view;

	@Inject
	public SlidePresenter(@ModuleScoped SlideView view) {
		this.view = view;
	}

	public void replaceViewData(SlideBean slide) {
		String title = slide.getTitle();
		String narration = slide.getNarration();
		String source = slide.getSource().getSrc();

		view.setSlideTitle(title);
		view.setNarration(narration);
		view.setImage(source);
	}
}
