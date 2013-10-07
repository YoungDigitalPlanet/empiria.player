package eu.ydp.empiria.player.client.module.progressbonus;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressAward;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.gwtutil.client.util.RandomWrapper;

@RunWith(MockitoJUnitRunner.class)
public class ProgressAssetProviderTest {

	@InjectMocks
	private ProgressAssetProvider assetProvider;

	@Mock
	private ProgressBonusConfig progressBonusConfig;
	@Mock
	private RandomWrapper random;
	@Mock
	private ProgressAwardResolver awardResolver;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldResolveAwardsForGivenId() {
		// given
		ProgressAward award1 = mock(ProgressAward.class);
		ProgressAward award2 = mock(ProgressAward.class);
		ProgressAward award3 = mock(ProgressAward.class);
		List<ProgressAward> awards = Lists.newArrayList(award1, award2, award3);
		when(progressBonusConfig.getAwards()).thenReturn(awards);
		// when
		assetProvider.createFrom(1);

		// then
		verify(awardResolver).createProgressAsset(award2);
	}

	@Test
	public void shouldResolveAwardsForRandomId() {
		// given
		ProgressAward award1 = mock(ProgressAward.class);
		ProgressAward award2 = mock(ProgressAward.class);
		ProgressAward award3 = mock(ProgressAward.class);
		List<ProgressAward> awards = Lists.newArrayList(award1, award2, award3);
		when(progressBonusConfig.getAwards()).thenReturn(awards);
		when(random.nextInt(anyInt())).thenReturn(2);
		// when
		assetProvider.createRandom();

		// then
		verify(awardResolver).createProgressAsset(award3);
	}
}
