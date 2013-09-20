package eu.ydp.empiria.player.client.gin.factory;

import javax.inject.Named;

import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.gwtutil.client.animation.Animation;
import eu.ydp.gwtutil.client.util.geom.Size;

public interface TutorCommandsModuleFactory {
	@Named("animation")
	TutorCommand createAnimationCommand(Animation animation, TutorEndHandler handler);

	@Named("image")
	TutorCommand createShowImageCommand(TutorView moduleView, String assetPath, Size size, TutorEndHandler handler);

}
