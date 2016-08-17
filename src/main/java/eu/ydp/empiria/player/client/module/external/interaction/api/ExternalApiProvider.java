package eu.ydp.empiria.player.client.module.external.interaction.api;

public class ExternalApiProvider {

    private ExternalInteractionApi externalInteractionApi;

    public ExternalApiProvider() {
        this.externalInteractionApi = new ExternalInteractionApiNullObject();
    }

    public ExternalInteractionApi getExternalApi() {
        return externalInteractionApi;
    }

    public void setExternalApi(ExternalInteractionApi externalInteractionApi) {
        this.externalInteractionApi = externalInteractionApi;
    }
}
