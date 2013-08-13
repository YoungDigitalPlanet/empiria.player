package eu.ydp.empiria.player.client.controller.multiview.animation;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.multiview.swipe.SwipeType;
import eu.ydp.empiria.player.client.inject.Instance;

@RunWith(MockitoJUnitRunner.class)
public class SwipeAnimationProviderTest {
	@Mock private Instance<SwipeType> swipeType;
	@Mock private Instance<AnimationBase> animationBase;
	@Mock private Instance<NoAnimation> noAnimation;

	@InjectMocks SwipeAnimationProvider instance;

	@Test
	public void getWhenSwipeIsDisabled() throws Exception {
		doReturn(SwipeType.DISABLED).when(swipeType).get();
		NoAnimation animation = mock(NoAnimation.class);
		doReturn(animation).when(noAnimation).get();
		assertThat(instance.get()).isEqualTo(animation);
	}

	@Test
	public void getWhenSwipeIsEnabled() throws Exception {
		doReturn(SwipeType.DEFAULT).when(swipeType).get();
		AnimationBase animation = mock(AnimationBase.class);
		doReturn(animation).when(animationBase).get();
		assertThat(instance.get()).isEqualTo(animation);
	}


}
