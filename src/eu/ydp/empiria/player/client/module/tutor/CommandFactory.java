package eu.ydp.empiria.player.client.module.tutor;

import java.util.Iterator;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.animation.Animation;
import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.animation.AnimationFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorCommandConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.gin.factory.TutorCommandsModuleFactory;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.empiria.player.client.util.geom.Size;

public class CommandFactory {

	private TutorConfig tutorConfig;
	private TutorView moduleView;
	private AnimationFactory animationFactory;
	private TutorCommandsModuleFactory commandsModuleFactory;

	@Inject
	public CommandFactory(@ModuleScoped TutorConfig tutorConfig, @ModuleScoped TutorView moduleView, AnimationFactory animationFactory,
			TutorCommandsModuleFactory commandsModuleFactory) {
		this.tutorConfig = tutorConfig;
		this.moduleView = moduleView;
		this.animationFactory = animationFactory;
		this.commandsModuleFactory = commandsModuleFactory;
	}

	public TutorCommand createCommand(ActionType actionType, EndHandler handler) {
		Iterable<TutorCommandConfig> commandsConfig = tutorConfig.getCommandsForAction(actionType);
		Iterator<TutorCommandConfig> commandsIterator = commandsConfig.iterator();
		if (!commandsIterator.hasNext()) {
			throw new RuntimeException("Empty config");
		}
		TutorCommandConfig commandConfig = commandsIterator.next();
		return createCommandFromConfig(commandConfig, handler);
	}

	private TutorCommand createCommandFromConfig(TutorCommandConfig commandConfig, EndHandler handler) {
		CommandType type = commandConfig.getType();
		String asset = commandConfig.getAsset();
		switch (type) {
		case ANIMATION:
			return createAnimationCommand(asset, handler);
		case IMAGE:
			return createImageCommand(asset);
		default:
			throw new RuntimeException("Command type not supported");
		}
	}

	private TutorCommand createAnimationCommand(String assetName, final EndHandler handler) {
		TutorPersonaProperties tutorPersonaProperties = getPersonaProperties();

		int animationFps = tutorPersonaProperties.getAnimationFps();
		Size animationSize = tutorPersonaProperties.getAnimationSize();

		String assetPath = createAssetPath(assetName);

		AnimationConfig config = new AnimationConfig(animationFps, animationSize, assetPath);

		Animation animation = animationFactory.getAnimation(config, moduleView);

		return commandsModuleFactory.createAnimationCommand(animation, handler);
	}

	private TutorCommand createImageCommand(String assetName) {
		String assetPath = createAssetPath(assetName);

		return commandsModuleFactory.createShowImageCommand(moduleView, assetPath);
	}

	private String createAssetPath(String assetName) {
		TutorPersonaProperties personaProperties = getPersonaProperties();
		String personaName = personaProperties.getName();

		return Joiner.on("_").join(personaName, assetName);
	}

	private TutorPersonaProperties getPersonaProperties() {
		return tutorConfig.getTutorPersonaProperties(0);
	}
}
