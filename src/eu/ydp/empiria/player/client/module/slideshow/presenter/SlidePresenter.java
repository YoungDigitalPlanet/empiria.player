package eu.ydp.empiria.player.client.module.slideshow.presenter;

import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideView;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.slideshow.structure.*;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SlidePresenter {

	private final SlideView view;

	@Inject
	public SlidePresenter(@ModuleScoped SlideView view) {
		this.view = view;
	}

	public void setTitle(String title) {
		view.setSlideTitle(title);
	}

	public void setNarration(String narration) {
		view.setNarration(narration);
	}

	public void setImageSrc(SourceBean src) {
		view.setImage(src.getSrc());
	}

	public void replaceView(SlideBean slide) {
		setTitle(slide.getTitle());
		setNarration(slide.getNarration());
		setImageSrc(slide.getSource());
	}
}
