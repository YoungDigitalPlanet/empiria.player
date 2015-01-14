package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.structure.*;
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
		SourceBean source = slide.getSource();

		setTitle(title);
		setNarration(narration);
		setSource(source);
	}

	private void setSource(SourceBean source) {
		String src = source.getSrc();
		if (!Strings.isNullOrEmpty(src)) {
			view.setImage(src);
		}
	}

	private void setTitle(String title) {
		view.clearSlideTitle();
		if (!Strings.isNullOrEmpty(title)) {
			view.setSlideTitle(title);
		}
	}

	private void setNarration(String narration) {
		view.clearNarration();
		if (!Strings.isNullOrEmpty(narration)) {
			view.setNarration(narration);
		}
	}
}
