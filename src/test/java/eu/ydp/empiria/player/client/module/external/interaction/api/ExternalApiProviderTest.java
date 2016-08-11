package eu.ydp.empiria.player.client.module.external.interaction.api;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ExternalApiProviderTest {

    private ExternalApiProvider testObj = new ExternalApiProvider();

    @Test
    public void shouldReturnNullApiObject_whenNotOtherApiWasSet() throws Exception {
        // WHEN
        ExternalInteractionApi result = testObj.getExternalApi();

        // THEN
        assertThat(result).isInstanceOf(ExternalInteractionApiNullObject.class);
    }

    @Test
    public void shouldReturnSetApiObject() throws Exception {
        // GIVEN
        ExternalInteractionApi givenApi = mock(ExternalInteractionApi.class);
        testObj.setExternalApi(givenApi);

        // WHEN
        ExternalInteractionApi result = testObj.getExternalApi();

        // THEN
        assertThat(result).isEqualTo(givenApi);
    }
}