package eu.ydp.empiria.player.client.module.bonus;

import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResourceType.IMAGE;
import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResourceType.SWIFFY;
import static eu.ydp.empiria.player.client.module.bonus.BonusConfigMockCreator.createBonus;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResource;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class BonusFactoryTest {

	@InjectMocks
	private BonusFactory bonusFactory;
	@Mock
	private Provider<ImageBonus> imageProvider;
	@Mock
	private Provider<SwiffyBonus> swiffyProvider;

	@Before
	public void setUp() {
		when(imageProvider.get()).thenReturn(mock(ImageBonus.class));
		when(swiffyProvider.get()).thenReturn(mock(SwiffyBonus.class));
	}

	@Test
	public void createImage() {
		// given
		String asset = "bonus.png";
		Size size = new Size(1, 2);
		BonusResource bonusResource = createBonus(asset, size, IMAGE);

		// when
		BonusWithAsset bonus = bonusFactory.createBonus(bonusResource);

		// then
		assertThat(bonus).isInstanceOf(ImageBonus.class);
		verify(bonus).setAsset(asset, size);
	}

	@Test
	public void createSwiffy() {
		// given
		String asset = "bonus.png";
		Size size = new Size(1, 2);
		BonusResource bonusResource = createBonus(asset, size, SWIFFY);

		// when
		BonusWithAsset bonus = bonusFactory.createBonus(bonusResource);

		// then
		assertThat(bonus).isInstanceOf(SwiffyBonus.class);
		verify(bonus).setAsset(asset, size);
	}
}
