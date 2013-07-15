package eu.ydp.empiria.player.client.module.tutor.commands;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.animation.Animation;
import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.module.tutor.EndHandler;

@RunWith(MockitoJUnitRunner.class)
public class AnimationCommandTest {
	@InjectMocks
	AnimationCommand command;
	@Mock
	private EndHandler handler;
	@Mock
	private Animation animation;

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
		verify(handler).onEnd();
		assertThat(command.isFinished(), is(true));
	}

	@Test
	public void shouldTerminateMethod() {
		// given

		// when
		command.terminate();

		// then
		verify(handler, never()).onEnd();
		assertThat(command.isFinished(), is(true));
	}
}
