package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.multiview.swipe.SwipeType;
import eu.ydp.empiria.player.client.inject.Instance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SwipeAnimationProviderTest {

    @InjectMocks
    private SwipeAnimationProvider testObj;

    @Mock
    private Provider<SwipeType> swipeTypeProvider;
    @Mock
    private Instance<AnimationBase> animationBase;
    @Mock
    private Instance<NoAnimation> noAnimation;

    @Test
    public void getWhenSwipeIsDisabled() throws Exception {
        when(swipeTypeProvider.get()).thenReturn(SwipeType.DISABLED);
        NoAnimation animation = mock(NoAnimation.class);
        doReturn(animation).when(noAnimation).get();
        assertThat(testObj.get()).isEqualTo(animation);
    }

    @Test
    public void getWhenSwipeIsEnabled() throws Exception {
        when(swipeTypeProvider.get()).thenReturn(SwipeType.DEFAULT);
        AnimationBase animation = mock(AnimationBase.class);
        doReturn(animation).when(animationBase).get();
        assertThat(testObj.get()).isEqualTo(animation);
    }
}
