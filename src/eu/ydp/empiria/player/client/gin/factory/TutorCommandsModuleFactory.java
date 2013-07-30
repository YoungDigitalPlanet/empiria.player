package eu.ydp.empiria.player.client.gin.factory;

import javax.inject.Named;

import eu.ydp.empiria.player.client.animation.Animation;
import eu.ydp.empiria.player.client.module.tutor.EndHandler;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.util.geom.Size;

public interface TutorCommandsModuleFactory {
	@Named("animation")
	TutorCommand createAnimationCommand(Animation animation, EndHandler handler);

	@Named("image")
	TutorCommand createShowImageCommand(String assetPath, Size size);

}
