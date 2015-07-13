package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller;

import com.google.common.base.Optional;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiePropertiesTestable;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.ContainerDimensions;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size.MaximizedStickieSizeStorage;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size.StickieSize;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;
import eu.ydp.gwtutil.client.geom.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StickieMinimizeMaximizeControllerJUnitTest {

    private final ReflectionsUtils reflectionsUtils = new ReflectionsUtils();

    private StickieMinimizeMaximizeController stickieMinimizeMaximizeController;
    private IStickieProperties stickieProperties;

    @Mock
    private MaximizedStickieSizeStorage maximizedStickieSizeStorage;
    @Mock
    private StickieViewPositionFinder positionFinder;

    private final int absoluteLeft = 1;
    private final int absoluteTop = 2;
    private final int width = 3;
    private final int height = 4;
    private ContainerDimensions stickieDimensions;

    @Before
    public void setUp() throws Exception {
        stickieProperties = new StickiePropertiesTestable();
        stickieMinimizeMaximizeController = new StickieMinimizeMaximizeController(stickieProperties, maximizedStickieSizeStorage, positionFinder);

        stickieDimensions = new ContainerDimensions.Builder().absoluteLeft(absoluteLeft).absoluteTop(absoluteTop).width(width).height(height).build();
    }

    @Test
    public void shouldUpdateMaximizeStickieSizeWhenMinimizingStickie() throws Exception {
        // when
        stickieMinimizeMaximizeController.positionMinimizedStickie(stickieDimensions);

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
    public void shouldUseCurrentPositionIfNoPreviousMinimizedPositionIsStored() throws Exception {
        stickieProperties.setX(123123);
        stickieProperties.setY(2234);

        Point<Integer> stickiePosition = stickieMinimizeMaximizeController.positionMinimizedStickie(stickieDimensions);

        assertThat(stickiePosition.getX(), equalTo(stickieProperties.getX()));
        assertThat(stickiePosition.getY(), equalTo(stickieProperties.getY()));
    }

    @Test
    public void shouldUsePreviousMinimizedPositionIfStored() throws Exception {
        Point<Integer> previousMinimizedPosition = new Point<Integer>(123, 456);
        setPreviousMinimizedPosition(previousMinimizedPosition);

        Point<Integer> stickiePosition = stickieMinimizeMaximizeController.positionMinimizedStickie(stickieDimensions);

        assertThat(stickiePosition.getX(), equalTo(previousMinimizedPosition.getX()));
        assertThat(stickiePosition.getY(), equalTo(previousMinimizedPosition.getY()));
    }

    @Test
    public void shouldResetPreviousMinimizedPosition() throws Exception {
        Point<Integer> previousMinimizedPosition = new Point<Integer>(123, 456);
        setPreviousMinimizedPosition(previousMinimizedPosition);

        stickieMinimizeMaximizeController.resetCachedMinimizedPosition();

        Object currentPreviousMinimizedPosition = reflectionsUtils.getValueFromFiledInObject("previousMinimizedPosition", stickieMinimizeMaximizeController);
        assertNull(currentPreviousMinimizedPosition);
    }

    @Test
    public void shouldSaveCurrentPositionAsPreviousMinimizedPositionWhenMaximizing() throws Exception {
        // given
        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);

        // when
        stickieMinimizeMaximizeController.positionMaximizedStickie(stickieDimensions, null);

        @SuppressWarnings("unchecked")
        Point<Integer> previousMinimizedPosition = (Point<Integer>) reflectionsUtils.getValueFromFiledInObject("previousMinimizedPosition",
                stickieMinimizeMaximizeController);

        assertNotNull(previousMinimizedPosition);
        assertEquals(Integer.valueOf(stickieProperties.getX()), previousMinimizedPosition.getX());
        assertEquals(Integer.valueOf(stickieProperties.getY()), previousMinimizedPosition.getY());
    }

    @Test
    public void shouldNotOverridePreviousMinimizedPositionWhenMaximizing() throws Exception {
        // given
        Point<Integer> expectedPreviousMinimizedPosition = new Point<Integer>(323123, 78675);
        setPreviousMinimizedPosition(expectedPreviousMinimizedPosition);

        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);

        // when
        stickieMinimizeMaximizeController.positionMaximizedStickie(stickieDimensions, null);

        @SuppressWarnings("unchecked")
        Point<Integer> currentPreviousMinimizedPosition = (Point<Integer>) reflectionsUtils.getValueFromFiledInObject("previousMinimizedPosition",
                stickieMinimizeMaximizeController);

        assertNotNull(currentPreviousMinimizedPosition);
        assertEquals(expectedPreviousMinimizedPosition.getX(), currentPreviousMinimizedPosition.getX());
        assertEquals(expectedPreviousMinimizedPosition.getY(), currentPreviousMinimizedPosition.getY());
    }

    @Test
    public void shouldUpdateMaximizeStickieSizeWhenMaximizingStickie() throws Exception {
        // given
        ContainerDimensions parentDimensions = new ContainerDimensions.Builder().build();

        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);

        // when
        stickieMinimizeMaximizeController.positionMaximizedStickie(stickieDimensions, parentDimensions);

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
        Point<Integer> positionMaximizedStickie = stickieMinimizeMaximizeController.positionMaximizedStickie(stickieDimensions, parentDimensions);

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
        when(positionFinder.refinePosition(eq(stickieProperties.getPosition()), any(ContainerDimensions.class), eq(parentDimensions))).thenReturn(newPosition);

        // when
        Point<Integer> positionMaximizedStickie = stickieMinimizeMaximizeController.positionMaximizedStickie(stickieDimensions, parentDimensions);

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

    private void setPreviousMinimizedPosition(Point<Integer> previousMinimizedPosition) {
        try {
            reflectionsUtils.setValueInObjectOnField("previousMinimizedPosition", stickieMinimizeMaximizeController, previousMinimizedPosition);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find field previousMinimizedPosition in class: " + stickieMinimizeMaximizeController.getClass().getName());
        }
    }
}
