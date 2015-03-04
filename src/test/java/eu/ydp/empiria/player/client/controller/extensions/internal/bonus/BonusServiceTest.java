package eu.ydp.empiria.player.client.controller.extensions.internal.bonus;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class BonusServiceTest {

	private BonusService bonusService;

	@Before
	public void setUp() throws Exception {
		bonusService = new BonusService();
	}

	@Test
	public void shouldReturnPropperBonusForId() {
		// given
		String bonusId = "BONUS_ID";
		BonusConfig bonusConfig = mock(BonusConfig.class);
		bonusService.registerBonus(bonusId, bonusConfig);

		String bonusId2 = "BONUS_ID2";
		BonusConfig bonusConfig2 = mock(BonusConfig.class);
		bonusService.registerBonus(bonusId2, bonusConfig2);

		// when
		BonusConfig returned = bonusService.getBonusConfig(bonusId);

		// then
		assertThat(returned).isEqualTo(bonusConfig);
	}
}
