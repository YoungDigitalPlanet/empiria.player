package eu.ydp.empiria.player.client.module.tutor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.animation.Animation;
import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.animation.AnimationFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorCommandConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.gin.factory.TutorCommandsModuleFactory;
import eu.ydp.empiria.player.client.module.tutor.commands.AnimationCommand;
import eu.ydp.empiria.player.client.module.tutor.commands.ShowImageCommand;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.empiria.player.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class CommandFactoryTest {
	@InjectMocks
	CommandFactory factory;

	@Mock
	TutorCommandsModuleFactory commandsModuleFactory;
	@Mock
	private TutorConfig tutorConfig;
	@Mock
	private TutorView moduleView;
	@Mock
	private AnimationFactory animationFactory;
	@Mock
	private EndHandler handler;
	TutorPersonaProperties properties;

	@Before
	public void setUp() {
		properties = mock(TutorPersonaProperties.class);
		when(properties.getAnimationSize()).thenReturn(new Size(30, 40));
		when(properties.getAnimationFps()).thenReturn(30);
		when(properties.getName()).thenReturn("ALEX");
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionForEmptyConfig() {
		// given
		when(tutorConfig.getCommandsForAction(ActionType.DEFAULT)).thenReturn(Lists.<TutorCommandConfig> newArrayList());

		// when
		factory.createCommand(ActionType.DEFAULT, handler);
	}

	@Test
	public void shouldCreateAnimationCommand() {
		// given
		prepareConfigForType(CommandType.ANIMATION);
		Animation animation = mock(Animation.class);

		when(animationFactory.getAnimation(argThat(isAnimationConfigOfJumpingAlex()), eq(moduleView))).thenReturn(animation);

		TutorCommand animationCommand = mock(AnimationCommand.class);

		when(commandsModuleFactory.createAnimationCommand(animation, handler)).thenReturn(animationCommand);

		// when
		TutorCommand command = factory.createCommand(ActionType.DEFAULT, handler);

		// then
		verify(animationFactory).getAnimation(argThat(isAnimationConfigOfJumpingAlex()), eq(moduleView));
		assertThat(command, is(animationCommand));
	}

	@Test
	public void shouldCreateShowImageCommand() {
		// given
		prepareConfigForType(CommandType.IMAGE);

		TutorCommand showImageCommand = mock(ShowImageCommand.class);

		when(commandsModuleFactory.createShowImageCommand(eq(moduleView), anyString())).thenReturn(showImageCommand);

		// when
		TutorCommand command = factory.createCommand(ActionType.DEFAULT, handler);

		// then
		verify(commandsModuleFactory).createShowImageCommand(moduleView, "ALEX_JUMPS.png");
		assertThat(command, is(showImageCommand));
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionForUnsupportedComandType() {
		// given
		prepareConfigForType(CommandType.SOUND);

		// when
		factory.createCommand(ActionType.DEFAULT, handler);
	}

	private void prepareConfigForType(CommandType type) {
		mockTutorConfig(type);
	}

	private void mockTutorConfig(CommandType type) {
		TutorCommandConfig tutorCommandConfig = mock(TutorCommandConfig.class);
		when(tutorCommandConfig.getType()).thenReturn(type);
		when(tutorCommandConfig.getAsset()).thenReturn("_JUMPS.png");

		when(tutorConfig.getCommandsForAction(ActionType.DEFAULT)).thenReturn(Lists.newArrayList(tutorCommandConfig));
		when(tutorConfig.supportsAction(ActionType.DEFAULT)).thenReturn(true);
		when(tutorConfig.getTutorPersonaProperties(0)).thenReturn(properties);
	}

	private ArgumentMatcher<AnimationConfig> isAnimationConfigOfJumpingAlex() {

		return new ArgumentMatcher<AnimationConfig>() {

			@Override
			public boolean matches(Object argument) {
				return ((AnimationConfig) argument).getSource().equals("ALEX_JUMPS.png");
			}
		};
	}

}
