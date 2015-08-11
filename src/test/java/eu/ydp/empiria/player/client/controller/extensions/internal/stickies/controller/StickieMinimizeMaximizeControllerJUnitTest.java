package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller;

import com.google.common.base.Optional;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.*;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.ContainerDimensions;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size.MaximizedStickieSizeStorage;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size.StickieSize;
import eu.ydp.gwtutil.client.geom.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StickieMinimizeMaximizeControllerJUnitTest {

    @InjectMocks
    private StickieMinimizeMaximizeController testObj;

    @Mock
    private MaximizedStickieSizeStorage maximizedStickieSizeStorage;
    @Mock
    private StickieViewPositionFinder positionFinder;
    @Mock
    private Point<Integer> previousMinimizedPosition;
    @Mock
    private IStickieProperties stickieProperties;

    private final int absoluteLeft = 1;
    private final int absoluteTop = 2;
    private final int width = 3;
    private final int height = 4;
    private ContainerDimensions stickieDimensions;

    @Before
    public void setUp() throws Exception {
        stickieDimensions = new ContainerDimensions.Builder().absoluteLeft(absoluteLeft).absoluteTop(absoluteTop).width(width).height(height).build();
    }

    @Test
    public void shouldUpdateMaximizeStickieSizeWhenMinimizingStickie() throws Exception {
        // given
        when(stickieProperties.getColorIndex()).thenReturn(0);

        // when
        testObj.positionMinimizedStickie(stickieDimensions);

        // then
        ArgumentCaptor<Integer> colorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<StickieSize> stickieSizeCaptor = ArgumentCaptor.forClass(StickieSize.class);
        verify(maximizedStickieSizeStorage).updateIfBiggerThanExisting(colorCaptor.capture(), stickieSizeCaptor.capture());

        assertThat(colorCaptor.getValue(), equalTo(0));

        StickieSize stickieSize = stickieSizeCaptor.getValue();
        assertThat(stickieSize.getWidth(), equalTo(width));
        assertThat(stickieSize.getHeight(), equalTo(height));
    }

    @Test
    public void shouldUseCurrentPositionIfNoPreviousMinimizedPositionIsStored() throws Exception {
        // given
        Point<Integer> expectedPosition = new Point<>(123123, 2234);
        when(stickieProperties.getPosition()).thenReturn(expectedPosition);

        // when
        Point<Integer> result = testObj.positionMinimizedStickie(stickieDimensions);

        // then
        assertThat(result.getX(), equalTo(123123));
        assertThat(result.getY(), equalTo(2234));
    }

    @Test
    public void shouldUsePreviousMinimizedPositionIfStored() throws Exception {
        // given
        Point<Integer> previousMinimizedPosition = new Point<>(123, 456);
        when(stickieProperties.getPosition()).thenReturn(previousMinimizedPosition);

        // when
        Point<Integer> stickiePosition = testObj.positionMinimizedStickie(stickieDimensions);

        // then
        assertThat(stickiePosition.getX(), equalTo(previousMinimizedPosition.getX()));
        assertThat(stickiePosition.getY(), equalTo(previousMinimizedPosition.getY()));
        verify(stickieProperties, times(1)).getPosition();
    }

    @Test
    public void shouldResetPreviousMinimizedPosition() throws Exception {
        // given
        Point<Integer> previousMinimizedPosition = new Point<>(123, 456);
        when(stickieProperties.getPosition()).thenReturn(previousMinimizedPosition);
        testObj.positionMinimizedStickie(stickieDimensions);

        // when
        testObj.resetCachedMinimizedPosition();
        testObj.positionMinimizedStickie(stickieDimensions);

        // then
        verify(stickieProperties, times(2)).getPosition();
    }

    @Test
    public void shouldSaveCurrentPositionAsPreviousMinimizedPositionWhenMaximizing() throws Exception {
        // given
        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);
        when(stickieProperties.getPosition()).thenReturn(new Point<>(0, 0));

        // when
        testObj.positionMaximizedStickie(stickieDimensions, null);
        Point<Integer> previousMinimizedPosition = testObj.positionMinimizedStickie(stickieDimensions);

        // then
        assertEquals(Integer.valueOf(0), previousMinimizedPosition.getX());
        assertEquals(Integer.valueOf(0), previousMinimizedPosition.getY());
    }

    @Test
    public void shouldNotOverridePreviousMinimizedPositionWhenMaximizing() throws Exception {
        // given
        Point<Integer> expectedPreviousMinimizedPosition = new Point<Integer>(323123, 78675);
        when(stickieProperties.getPosition()).thenReturn(expectedPreviousMinimizedPosition);

        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);
        testObj.positionMinimizedStickie(stickieDimensions);

        // when
        testObj.positionMaximizedStickie(stickieDimensions, null);

        //then
        Point<Integer> position = testObj.positionMinimizedStickie(stickieDimensions);
        assertEquals(expectedPreviousMinimizedPosition.getX(), position.getX());
        assertEquals(expectedPreviousMinimizedPosition.getY(), position.getY());
    }

    @Test
    public void shouldUpdateMaximizeStickieSizeWhenMaximizingStickie() throws Exception {
        // given
        ContainerDimensions parentDimensions = new ContainerDimensions.Builder().build();

        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);

        // when
        testObj.positionMaximizedStickie(stickieDimensions, parentDimensions);

        // then
        ArgumentCaptor<Integer> colorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<StickieSize> stickieSizeCaptor = ArgumentCaptor.forClass(StickieSize.class);
        verify(maximizedStickieSizeStorage).updateIfBiggerThanExisting(colorCaptor.capture(), stickieSizeCaptor.capture());

        assertThat(colorCaptor.getValue(), equalTo(stickieProperties.getColorIndex()));

        StickieSize stickieSize = stickieSizeCaptor.getValue();
        assertThat(stickieSize.getWidth(), equalTo(width));
        assertThat(stickieSize.getHeight(), equalTo(height));
    }

    @Test
    public void shouldUseGivenStickieDimensionsWhenMaximizingAndNoOtherAreStored() throws Exception {
        // given
        ContainerDimensions parentDimensions = new ContainerDimensions.Builder().build();

        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);

        Point<Integer> newPosition = new Point<Integer>(123, 456);
        when(positionFinder.refinePosition(stickieProperties.getPosition(), stickieDimensions, parentDimensions)).thenReturn(newPosition);

        // when
        Point<Integer> positionMaximizedStickie = testObj.positionMaximizedStickie(stickieDimensions, parentDimensions);

        // then
        verify(positionFinder).refinePosition(stickieProperties.getPosition(), stickieDimensions, parentDimensions);
        assertEquals(newPosition, positionMaximizedStickie);
    }

    @Test
    public void shouldUseStoredStickieDimensionsWhenMaximizing() throws Exception {
        // given
        ContainerDimensions parentDimensions = new ContainerDimensions.Builder().build();

        StickieSize maximizedStickieSize = new StickieSize(666, 777);
        Optional<StickieSize> optionalStickieSize = Optional.fromNullable(maximizedStickieSize);
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);

        Point<Integer> newPosition = new Point<Integer>(123, 456);
        when(positionFinder.refinePosition(eq(stickieProperties.getPosition()), any(ContainerDimensions.class), eq(parentDimensions))).thenReturn(
                newPosition);

        // when
        Point<Integer> positionMaximizedStickie = testObj.positionMaximizedStickie(stickieDimensions, parentDimensions);

        // then
        assertEquals(newPosition, positionMaximizedStickie);

        ArgumentCaptor<ContainerDimensions> stickieContainerDimensionsCaptor = ArgumentCaptor.forClass(ContainerDimensions.class);
        verify(positionFinder).refinePosition(eq(stickieProperties.getPosition()), stickieContainerDimensionsCaptor.capture(), eq(parentDimensions));

        ContainerDimensions capturedDimensions = stickieContainerDimensionsCaptor.getValue();
        assertEquals(stickieDimensions.getAbsoluteLeft(), capturedDimensions.getAbsoluteLeft());
        assertEquals(stickieDimensions.getAbsoluteTop(), capturedDimensions.getAbsoluteTop());
        assertEquals(maximizedStickieSize.getWidth(), capturedDimensions.getWidth());
        assertEquals(maximizedStickieSize.getHeight(), capturedDimensions.getHeight());
    }
}
