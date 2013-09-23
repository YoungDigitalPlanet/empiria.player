package eu.ydp.empiria.player.client.module.tutor.commands;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.gwtutil.client.animation.Animation;
import eu.ydp.gwtutil.client.animation.AnimationEndHandler;

@RunWith(MockitoJUnitRunner.class)
public class AnimationCommandTest {
	@InjectMocks AnimationCommand command;
	@Mock private TutorEndHandler handler;
	@Mock private Animation animation;

	@Test
	public void shouldStartAnimation() {
		// when
		command.execute();

		// then
		verify(animation).start(any(AnimationEndHandler.class));
		assertThat(command.isFinished(), is(false));
	}

	@Test
	public void shouldtestName() {
		// when
		command.execute();
		command.animationHandler.onEnd();

		// then
		verify(handler).onEnd(true);
		assertThat(command.isFinished(), is(true));
	}

	@Test
	public void shouldTerminateMethod() {
		// given

		// when
		command.terminate();

		// then
		verify(handler, never()).onEnd(anyBoolean());
		assertThat(command.isFinished(), is(true));
	}
}
