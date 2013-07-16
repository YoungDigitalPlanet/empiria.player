package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import javax.annotation.Nullable;

import com.google.common.base.Function;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorCommandJs;

public class TutorCommandTransformation implements Function<TutorCommandJs, TutorCommandConfig> {

	@Override
	@Nullable
	public TutorCommandConfig apply(@Nullable TutorCommandJs commandJs) {
		return new TutorCommandConfig(commandJs);
	}

}
