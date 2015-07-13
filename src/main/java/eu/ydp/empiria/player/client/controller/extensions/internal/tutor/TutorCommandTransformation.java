package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import com.google.common.base.Function;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorCommandJs;

import javax.annotation.Nullable;

public class TutorCommandTransformation implements Function<TutorCommandJs, TutorCommandConfig> {

    @Override
    @Nullable
    public TutorCommandConfig apply(@Nullable TutorCommandJs commandJs) {
        return TutorCommandConfig.fromJs(commandJs);
    }

}
