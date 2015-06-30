package eu.ydp.empiria.player.client.module.external.interaction.api;

import com.google.gwt.core.client.js.JsType;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;

@JsType
public interface ExternalInteractionApi extends ExternalApi {

    void showCorrectAnswers();

    void hideCorrectAnswers();

    void markCorrectAnswers();

    void unmarkCorrectAnswers();

    void markWrongAnswers();

    void unmarkWrongAnswers();
}
