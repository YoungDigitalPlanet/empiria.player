package eu.ydp.empiria.player.client.animation.js;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;
import eu.ydp.empiria.player.client.animation.preload.ImagePreloadHandler;
import eu.ydp.empiria.player.client.animation.preload.ImagePreloader;
import eu.ydp.empiria.player.client.util.geom.Size;
import eu.ydp.gwtutil.client.timer.TimerAccessibleMock;

public class JsAnimationTest {

	private JsAnimation animation;
	
	private final TimerAccessibleMock timer = spy(new TimerAccessibleMock());
	private final ImagePreloader preloader = mock(ImagePreloader.class);
	private final AnimationHolder holder = mock(AnimationHolder.class);
	
	@Before
	public void setUp(){
		animation = new JsAnimation(new AnimationAnalyzer(), preloader, timer);
		TimerAccessibleMock.reset();
	}

	@Test
	public void preloadingAndInitialization(){
		// given
		final Size IMAGE_SIZE = new Size(100, 40);
		final String SOURCE = "image.png";
		final int FPS = 25;
		final int INTERVAL = 1000 / FPS;
		final int FRAME_WIDHT = 20;
		final AnimationConfig config = new AnimationConfig(FPS, new Size(FRAME_WIDHT, 40), SOURCE);
		AnimationEndHandler handler = mock(AnimationEndHandler.class);
		initPreloaderMock(IMAGE_SIZE);
		
		animation.init(config, holder);
		
		// when
		animation.start(handler);
		
		// then
		verify(preloader).preload(eq(SOURCE), any(ImagePreloadHandler.class));
		verify(holder).setAnimationImage(SOURCE);
		verify(holder).setAnimationLeft(0);
		verify(timer).scheduleRepeating(INTERVAL);
		
	}
	
	@Test
	public void framesUpdate(){
		// given
		final Size IMAGE_SIZE = new Size(100, 40);
		final String SOURCE = "image.png";
		final int FPS = 25;
		final int FRAME_WIDHT = 20;
		final AnimationConfig config = new AnimationConfig(FPS, new Size(FRAME_WIDHT, 40), SOURCE);
		AnimationEndHandler handler = mock(AnimationEndHandler.class);
		initPreloaderMock(IMAGE_SIZE);
		
		animation.init(config, holder);
		animation.start(handler);

		// when
		for (int frame = 0 ; frame < 5 ; frame ++){
			timer.dispatch();
		}
		
		// then
		verify(handler).onEnd();
		verify(timer).cancel();
		ArgumentCaptor<Integer> ac = ArgumentCaptor.forClass(Integer.class);
		verify(holder, times(5)).setAnimationLeft(ac.capture());
		assertThat(ac.getAllValues()).containsSequence(0, -20, -40, -60, -80);
		
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
		initPreloaderMock(IMAGE_SIZE);
		
		animation.init(config, holder);
		
		// when
		animation.start(handler);
		
		// then
		verify(timer).scheduleRepeating(1000);
	}
	
	@Test
	public void zeroSizeAnimation(){
		// given
		final Size IMAGE_SIZE = new Size(0, 40);
		final String SOURCE = "image.png";
		final int FPS = 25;
		final int FRAME_WIDHT = 20;
		final AnimationConfig config = new AnimationConfig(FPS, new Size(FRAME_WIDHT, 40), SOURCE);
		AnimationEndHandler handler = mock(AnimationEndHandler.class);
		initPreloaderMock(IMAGE_SIZE);
		
		animation.init(config, holder);
		
		// when
		animation.start(handler);
		
		// then
		verify(handler).onEnd();
		verifyNoMoreInteractions(holder);
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
		initPreloaderMock(IMAGE_SIZE);
		
		animation.init(config, holder);
		animation.start(handler);
		
		// when
		timer.dispatch();
		animation.terminate();
		
		// then
		verify(timer).cancel();
		ArgumentCaptor<Integer> ac = ArgumentCaptor.forClass(Integer.class);
		verify(holder, times(2)).setAnimationLeft(ac.capture());
		assertThat(ac.getAllValues()).containsSequence(0, -20);
		verifyNoMoreInteractions(handler);
	}

	private void initPreloaderMock(final Size imageSize) {
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				ImagePreloadHandler handler = (ImagePreloadHandler)invocation.getArguments()[1];
				handler.onLoad(imageSize);
				return null;
			}
		}).when(preloader).preload(anyString(), any(ImagePreloadHandler.class));
	}
}
