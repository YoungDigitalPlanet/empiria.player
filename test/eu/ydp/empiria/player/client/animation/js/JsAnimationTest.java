package eu.ydp.empiria.player.client.animation.js;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.animation.AnimationRuntimeConfig;
import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;
import eu.ydp.empiria.player.client.util.geom.Size;

public class JsAnimationTest {

	private JsAnimation animation;

	private final FrameworkAnimation fwAnim = mock(FrameworkAnimation.class);
	private final AnimationHolder holder = mock(AnimationHolder.class);

	private FrameworkAnimationListener listener;

	@Before
	public void setUp(){
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				listener = (FrameworkAnimationListener)invocation.getArguments()[0];
				return null;
			}
		}).when(fwAnim).setListener(Mockito.any(FrameworkAnimationListener.class));
		animation = new JsAnimation(fwAnim);
	}

	@Test
	public void preloadingAndInitialization(){
		// given
		final Size IMAGE_SIZE = new Size(100, 40);
		final String SOURCE = "image.png";
		final int FPS = 25;
		final int FRAME_WIDHT = 20;
		final AnimationConfig config = new AnimationConfig(FPS, new Size(FRAME_WIDHT, 40), SOURCE);
		AnimationEndHandler handler = mock(AnimationEndHandler.class);
		AnimationRuntimeConfig runtimeConfig = new AnimationRuntimeConfig(IMAGE_SIZE, 5, config, holder);
		animation.setRuntimeConfiguration(runtimeConfig);

		// when
		animation.start(handler);

		// then

		verify(holder).setBackgroundImage(SOURCE, IMAGE_SIZE);
		verify(holder).setAnimationLeft(0);
		verify(fwAnim).run(200);

	}

	@Test
	public void framesUpdate(){
		// given
		final Size IMAGE_SIZE = new Size(100, 40);
		final String SOURCE = "image.png";
		final int FPS = 25;
		final int FRAME_WIDHT = 20;
		final int FRAMES_COUNT = 5;
		final AnimationConfig config = new AnimationConfig(FPS, new Size(FRAME_WIDHT, 40), SOURCE);
		AnimationEndHandler handler = mock(AnimationEndHandler.class);
		AnimationRuntimeConfig runtimeConfig = new AnimationRuntimeConfig(IMAGE_SIZE, 5, config, holder);
		animation.setRuntimeConfiguration(runtimeConfig);
		animation.start(handler);

		// when
		for (double frame = 1 ; frame <= FRAMES_COUNT ; frame ++){
			double progress = frame / FRAMES_COUNT;
			listener.onUpdate(progress);
		}

		// then
		verify(handler).onEnd();
		ArgumentCaptor<Integer> ac = ArgumentCaptor.forClass(Integer.class);
		verify(holder, times(6)).setAnimationLeft(ac.capture());
		assertThat(ac.getAllValues()).containsSequence(0, -20, -40, -60, -80, -80);

	}

	@Test
	public void framesUpdate_differentProgressValues(){
		// given
		final Size IMAGE_SIZE = new Size(100, 40);
		final String SOURCE = "image.png";
		final int FPS = 25;
		final int FRAME_WIDHT = 20;
		final AnimationConfig config = new AnimationConfig(FPS, new Size(FRAME_WIDHT, 40), SOURCE);
		AnimationEndHandler handler = mock(AnimationEndHandler.class);

		AnimationRuntimeConfig runtimeConfig = new AnimationRuntimeConfig(IMAGE_SIZE, 5, config, holder);
		animation.setRuntimeConfiguration(runtimeConfig);
		animation.start(handler);

		// when
		listener.onUpdate(0);
		listener.onUpdate(0.01);
		listener.onUpdate(0.199);
		listener.onUpdate(0.2);
		listener.onUpdate(0.201);
		listener.onUpdate(0.99);
		listener.onUpdate(1);

		// then
		verify(handler).onEnd();
		ArgumentCaptor<Integer> ac = ArgumentCaptor.forClass(Integer.class);
		verify(holder, times(8)).setAnimationLeft(ac.capture());
		assertThat(ac.getAllValues()).containsSequence(0, 0, 0, 0, -20, -20, -80, -80);

	}

	@Test
	public void zeroFps(){
		// given
		final Size IMAGE_SIZE = new Size(100, 40);
		final String SOURCE = "image.png";
		final int FPS = 0;
		final int FRAME_WIDHT = 20;
		final AnimationConfig config = new AnimationConfig(FPS, new Size(FRAME_WIDHT, 40), SOURCE);
		AnimationEndHandler handler = mock(AnimationEndHandler.class);


		AnimationRuntimeConfig runtimeConfig = new AnimationRuntimeConfig(IMAGE_SIZE, 5, config, holder);
		animation.setRuntimeConfiguration(runtimeConfig);

		// when
		animation.start(handler);

		// then
		verify(fwAnim).run(5000);
	}

	@Test
	public void terminate(){
		// given
		final Size IMAGE_SIZE = new Size(100, 40);
		final String SOURCE = "image.png";
		final int FPS = 25;
		final int FRAME_WIDHT = 20;
		final AnimationConfig config = new AnimationConfig(FPS, new Size(FRAME_WIDHT, 40), SOURCE);
		AnimationEndHandler handler = mock(AnimationEndHandler.class);

		AnimationRuntimeConfig runtimeConfig = new AnimationRuntimeConfig(IMAGE_SIZE, 5, config, holder);
		animation.setRuntimeConfiguration(runtimeConfig);
		animation.start(handler);

		// when
		listener.onUpdate(0.2);
		animation.terminate();

		// thent
		verify(fwAnim).cancel();
		ArgumentCaptor<Integer> ac = ArgumentCaptor.forClass(Integer.class);
		verify(holder, times(2)).setAnimationLeft(ac.capture());
		assertThat(ac.getAllValues()).containsSequence(0, -20);
		verifyNoMoreInteractions(handler);
	}

}
