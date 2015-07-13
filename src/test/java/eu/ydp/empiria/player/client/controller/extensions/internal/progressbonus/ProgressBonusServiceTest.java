package eu.ydp.empiria.player.client.controller.extensions.internal.progressbonus;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusService;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ProgressBonusServiceTest {

    private ProgressBonusService service;

    @Before
    public void setup() {
        service = new ProgressBonusService();
    }

    @Test
    public void shouldReturnPropperProgressBonusForId() {
        // given
        String progressBonusId = "PROGRESSBONUS_ID";
        ProgressBonusConfig config = mock(ProgressBonusConfig.class);
        service.register(progressBonusId, config);

        String progressBonusId2 = "PROGRESSBONUS_ID2";
        ProgressBonusConfig config2 = mock(ProgressBonusConfig.class);
        service.register(progressBonusId2, config2);

        // when
        ProgressBonusConfig returned = service.getProgressBonusConfig(progressBonusId);

        // then
        assertThat(returned).isEqualTo(config);
    }
}
