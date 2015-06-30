package eu.ydp.empiria.player.client.module.bonus;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusAction;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResource;
import eu.ydp.gwtutil.client.util.RandomWrapper;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResourceType.IMAGE;
import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResourceType.SWIFFY;
import static eu.ydp.empiria.player.client.module.bonus.BonusConfigMockCreator.createBonus;
import static eu.ydp.empiria.player.client.module.bonus.BonusConfigMockCreator.createMockAction;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BonusProviderTest {

    @InjectMocks
    private BonusProvider bonusProvider;
    @Mock
    private BonusConfig bonusConfig;
    @Mock
    private BonusFactory bonusFactory;
    @Mock
    private RandomWrapper randomWrapper;

    @Test
    public void next_imageBonus() {
        // given
        final String asset = "bonus1.png";
        final Size size = new Size(1, 2);
        BonusResource bonusResource = createBonus(asset, size, IMAGE);
        BonusAction action = createMockAction(Lists.newArrayList(bonusResource));
        when(bonusConfig.getActions()).thenReturn(Lists.newArrayList(action));

        BonusWithAsset expected = mock(BonusWithAsset.class);
        when(bonusFactory.createBonus(eq(bonusResource))).thenReturn(expected);

        // when
        Bonus result = bonusProvider.next();

        // then
        verify(bonusFactory).createBonus(bonusResource);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void next_imageBonus_random() {
        // given
        final String asset1 = "bonus1";
        final String asset2 = "bonus2.png";
        final Size size = new Size(1, 2);
        BonusResource bonusResource = createBonus(asset2, size, IMAGE);
        ArrayList<BonusResource> bonuses = Lists.newArrayList(createBonus(asset1, size, SWIFFY), bonusResource);
        BonusAction action = createMockAction(bonuses);
        when(bonusConfig.getActions()).thenReturn(Lists.newArrayList(action));
        when(randomWrapper.nextInt(2)).thenReturn(1);

        BonusWithAsset expected = mock(BonusWithAsset.class);
        when(bonusFactory.createBonus(eq(bonusResource))).thenReturn(expected);

        // when
        Bonus result = bonusProvider.next();

        // then
        verify(bonusFactory).createBonus(bonusResource);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void next_swiffyBonus() {
        // given
        final String asset = "swiffy0";
        final Size size = new Size(1, 2);
        BonusResource bonusResource = createBonus(asset, size, SWIFFY);
        BonusAction action = createMockAction(Lists.newArrayList(bonusResource));
        when(bonusConfig.getActions()).thenReturn(Lists.newArrayList(action));

        BonusWithAsset expected = mock(BonusWithAsset.class);
        when(bonusFactory.createBonus(eq(bonusResource))).thenReturn(expected);

        // when
        Bonus result = bonusProvider.next();

        // then
        verify(bonusFactory).createBonus(bonusResource);
        assertThat(result).isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void next_noBonuses() {
        // given
        BonusAction action = createMockAction(Lists.<BonusResource>newArrayList());
        when(bonusConfig.getActions()).thenReturn(Lists.newArrayList(action));

        // when
        bonusProvider.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void next_noActions() {
        // given
        when(bonusConfig.getActions()).thenReturn(Lists.<BonusAction>newArrayList());

        // when
        bonusProvider.next();
    }
}
