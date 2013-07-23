package eu.ydp.empiria.player.client.module.tutor.presenter;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;

public class TutorPresenterImpl implements TutorPresenter {

	private final TutorView tutorView;
	
	@Inject
	public TutorPresenterImpl(@ModuleScoped TutorView tutorView) {
		this.tutorView = tutorView;
	}

	@Override
	public void init() {
		tutorView.bindUi();
	}

	@Override
	public void clicked() {
		// TODO YPUB-5476
	}


}
