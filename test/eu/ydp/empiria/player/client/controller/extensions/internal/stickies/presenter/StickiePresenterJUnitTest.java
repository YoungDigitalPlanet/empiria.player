package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieView;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiePropertiesTestable;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieRegistration;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieMinimizeMaximizeController;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder;
import eu.ydp.gwtutil.client.geom.Point;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StickiePresenterJUnitTest {

	private StickiePresenter stickiePresenter;
	private @Mock IStickieProperties stickieProperties;
	private @Mock StickieMinimizeMaximizeController minimizeMaximizeController;
	private @Mock StickieRegistration stickieRegistration;
	private @Mock StickieViewPositionFinder positionFinder;
	private @Mock IStickieView stickieView;
	private ContainerDimensions parentDimensions = new ContainerDimensions.Builder().build();
	private ContainerDimensions stickieDimensions = new ContainerDimensions.Builder().build();
	
	@Before
	public void setUp() throws Exception {
		stickieProperties = new StickiePropertiesTestable();
		stickiePresenter = new StickiePresenter(stickieProperties, minimizeMaximizeController, stickieRegistration, positionFinder);
		stickiePresenter.setView(stickieView);
		
		when(stickieView.getStickieDimensions())
		.thenReturn(stickieDimensions);
		
		when(stickieView.getParentDimensions())
			.thenReturn(parentDimensions);
	}

	@Test
	public void shouldDeleteStickie() throws Exception {
		stickiePresenter.deleteStickie();
		verify(stickieRegistration).removeStickie();
	}
	
	@Test
	public void shouldMinimizeStickieWhenIsMaximized() throws Exception {
		//given
		stickieProperties.setMinimized(false);
		Point<Integer> newPosition = new Point<Integer>(987, 654);
		when(minimizeMaximizeController.positionMinimizedStickie(stickieDimensions))
			.thenReturn(newPosition );
		
		//when
		stickiePresenter.negateStickieMinimize();
		
		//then
		assertEquals(true, stickieProperties.isMinimized());
		assertEquals(newPosition, stickieProperties.getPosition());
		verifyViewWithDoubleMinimizedCheck();
	}
	
	@Test
	public void shouldMaximizeStickieWhenIsMinimized() throws Exception {
		//given
		stickieProperties.setMinimized(true);
		Point<Integer> newPosition = new Point<Integer>(987, 654);
		when(minimizeMaximizeController.positionMaximizedStickie(stickieDimensions, parentDimensions))
		.thenReturn(newPosition );
		
		//when
		stickiePresenter.negateStickieMinimize();
		
		//then
		assertEquals(false, stickieProperties.isMinimized());
		assertEquals(newPosition, stickieProperties.getPosition());
		verifyViewWithDoubleMinimizedCheck();
	}

	@Test
	public void shouldCenterPositionToViewAndCorrectIfNeeded() throws Exception {
		//given
		Point<Integer> centerPosition = new Point<Integer>(123, 25);
		when(positionFinder.calculateCenterPosition(stickieDimensions, parentDimensions))
			.thenReturn(centerPosition);
		
		Point<Integer> correctedPosition = new Point<Integer>(666, 777);
		when(positionFinder.refinePosition(centerPosition, stickieDimensions, parentDimensions))
			.thenReturn(correctedPosition);
		
		//when
		stickiePresenter.centerPositionToView();
		
		//then
		verify(positionFinder).calculateCenterPosition(stickieDimensions, parentDimensions);
		verify(positionFinder).refinePosition(centerPosition, stickieDimensions, parentDimensions);
		assertThat(stickieProperties.getX(), equalTo(correctedPosition.getX()));
		assertThat(stickieProperties.getY(), equalTo(correctedPosition.getY()));
		verifyViewUpdate();
	}
	
	@Test
	public void shouldCorrectCurrentStickiePosition() throws Exception {
		//given
		Point<Integer> currentPosition = new Point<Integer>(444, 555);
		stickieProperties.setPosition(currentPosition);
		
		Point<Integer> correctedPosition = new Point<Integer>(1, 2);
		when(positionFinder.refinePosition(currentPosition, stickieDimensions, parentDimensions))
			.thenReturn(correctedPosition );
		
		//when
		stickiePresenter.correctStickiePosition();
		
		//then
		verify(positionFinder).refinePosition(currentPosition, stickieDimensions, parentDimensions);
		assertThat(stickieProperties.getX(), equalTo(correctedPosition.getX()));
		assertThat(stickieProperties.getY(), equalTo(correctedPosition.getY()));
		verifyViewUpdate();
	}
	
	@Test
	public void shouldResetMinimizedStickiePositionAndUpdateStickiePosition() throws Exception {
		//given
		Point<Integer> newPosition = new Point<Integer>(10, 20);
		
		Point<Integer> correctedPosition = new Point<Integer>(30, 40);
		when(positionFinder.refinePosition(newPosition, stickieDimensions, parentDimensions))
			.thenReturn(correctedPosition );
		
		//when
		stickiePresenter.moveStickieToPosition(newPosition);
		
		//then
		verify(minimizeMaximizeController).resetCachedMinimizedPosition();
		verify(positionFinder).refinePosition(newPosition, stickieDimensions, parentDimensions);
		assertThat(stickieProperties.getX(), equalTo(correctedPosition.getX()));
		assertThat(stickieProperties.getY(), equalTo(correctedPosition.getY()));		
	}
	
	@Test
	public void shouldChangeStickieContentAndUpdateView() throws Exception {
		String contentText = "new content";
		stickiePresenter.changeContentText(contentText);
		
		verify(stickieView).setText(contentText);
		verify(stickieView).setPosition(stickieProperties.getX(), stickieProperties.getY());
		verify(stickieView).setMinimized(stickieProperties.isMinimized());
		verify(stickieView).setColorIndex(stickieProperties.getColorIndex());
	}
	
	private void verifyViewUpdate() {
		verify(stickieView).setText(stickieProperties.getStickieContent());
		verify(stickieView).setPosition(stickieProperties.getX(), stickieProperties.getY());
		verify(stickieView).setMinimized(stickieProperties.isMinimized());
		verify(stickieView).setColorIndex(stickieProperties.getColorIndex());
	}

	private void verifyViewWithDoubleMinimizedCheck() {
		verify(stickieView).setText(stickieProperties.getStickieContent());
		verify(stickieView).setPosition(stickieProperties.getX(), stickieProperties.getY());
		verify(stickieView, times(2)).setMinimized(stickieProperties.isMinimized());
		verify(stickieView).setColorIndex(stickieProperties.getColorIndex());
	}
	
	
}
