package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.gwtutil.test.mock.ReturnsJavaBeanAnswers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class StickiesProcessorExtensionJUnitTest extends AbstractTestWithMocksBase {

    StickiesProcessorExtension processorExtension;
    final int COLOR_INDEX = 11;
    final int ITEMS_COUNT = 10;
    final int ITEM_INDEX = 1;

    @Override
    @Before
    public void setUp() {
        super.setUp(StickiesProcessorExtension.class);
        processorExtension = spy(injector.getInstance(StickiesProcessorExtension.class));
    }

    @Test
    public void createStickie() {
        // when
        IStickieProperties sp = processorExtension.createStickie(COLOR_INDEX);

        // then
        assertThat(sp.getColorIndex(), is(COLOR_INDEX));
        verify(sp).updateTimestamp();
    }

    @Test
    public void addStickie() {
        // given
        processorExtension.initStickiesList(ITEMS_COUNT);
        processorExtension.currItemIndex = ITEM_INDEX;
        IStickieProperties stickiePropertiesMock = mock(IStickieProperties.class, new ReturnsJavaBeanAnswers());
        doReturn(stickiePropertiesMock).when(processorExtension).createStickie(anyInt());
        doNothing().when(processorExtension).addStickieView(any(IStickieProperties.class), anyBoolean());

        // when
        processorExtension.addStickie(COLOR_INDEX);

        // then
        assertThat(processorExtension.stickies.get(ITEM_INDEX), contains(stickiePropertiesMock));
        verify(processorExtension).createStickie(eq(COLOR_INDEX));
        verify(processorExtension).addStickieView(eq(stickiePropertiesMock), eq(true));
    }

    @Test
    public void checkStickieOverlay() {
        // given
        processorExtension.initStickiesList(ITEMS_COUNT);
        processorExtension.currItemIndex = ITEM_INDEX;
        final int coord1 = 500;
        final int coord2 = 520;

        IStickieProperties sp1 = mock(IStickieProperties.class, new ReturnsJavaBeanAnswers());
        IStickieProperties sp2 = mock(IStickieProperties.class, new ReturnsJavaBeanAnswers());
        IStickieProperties spTested = mock(IStickieProperties.class, new ReturnsJavaBeanAnswers());
        sp1.setX(coord1);
        sp1.setY(coord1);
        sp2.setX(coord2);
        sp2.setY(coord2);
        spTested.setX(coord1);
        spTested.setY(coord1);
        processorExtension.stickies.get(ITEM_INDEX).add(sp1);
        processorExtension.stickies.get(ITEM_INDEX).add(sp2);

        // when
        processorExtension.checkStickieOverlay(spTested);

        // then
        assertThat(spTested.getX() >= coord2 + StickiesProcessorExtension.DISTANCE_MIN_COMPONENT, is(true));
        assertThat(spTested.getY() >= coord2 + StickiesProcessorExtension.DISTANCE_MIN_COMPONENT, is(true));
    }

    @Test
    public void deleteStickie() {
        // given
        processorExtension.initStickiesList(ITEMS_COUNT);
        processorExtension.currItemIndex = ITEM_INDEX;

        doNothing().when(processorExtension).deleteStickieView(any(IStickieProperties.class));

        IStickieProperties sp = mock(IStickieProperties.class, new ReturnsJavaBeanAnswers());

        processorExtension.stickies.get(ITEM_INDEX).add(sp);

        // when
        processorExtension.deleteStickie(sp);

        // then
        assertThat(processorExtension.stickies.get(ITEM_INDEX).isEmpty(), is(true));
        verify(processorExtension).deleteStickieView(eq(sp));
    }

    @Test
    public void deleteStickieView() {
        // given
        IStickieProperties sp = mock(IStickieProperties.class);
        IStickieView sv = mock(IStickieView.class);
        processorExtension.views.put(sp, sv);

        // when
        processorExtension.deleteStickieView(sp);

        // then
        assertThat(processorExtension.views.isEmpty(), is(true));
    }

    @Test
    public void clearAll() {
        // given
        final int OTHER_ITEM_INDEX = 0;
        processorExtension.initStickiesList(ITEMS_COUNT);
        processorExtension.currItemIndex = ITEM_INDEX;

        IStickieProperties spOnCurrentItem1 = mock(IStickieProperties.class);
        IStickieProperties spOnCurrentItem2 = mock(IStickieProperties.class);
        IStickieProperties spOnOtherItem1 = mock(IStickieProperties.class);
        doNothing().when(processorExtension).deleteStickieView(any(IStickieProperties.class));

        processorExtension.stickies.get(ITEM_INDEX).add(spOnCurrentItem1);
        processorExtension.stickies.get(ITEM_INDEX).add(spOnCurrentItem2);
        processorExtension.stickies.get(OTHER_ITEM_INDEX).add(spOnOtherItem1);

        // when
        processorExtension.clearAll();

        // then
        ArgumentCaptor<IStickieProperties> ac = ArgumentCaptor.forClass(IStickieProperties.class);
        verify(processorExtension, times(2)).deleteStickie(ac.capture());
        assertThat(ac.getAllValues(), contains(spOnCurrentItem1, spOnCurrentItem2));

    }

    @Test
    public void testParseExternalStickiesNull() {
        // given
        processorExtension.initStickiesList(ITEMS_COUNT);
        List<List<IStickieProperties>> stickies = processorExtension.stickies;
        int initSize = processorExtension.stickies.size();
        doReturn(null).when(processorExtension).getExternalStickies();

        // when
        processorExtension.parseExternalStickies();

        // then
        assertThat(stickies, notNullValue());
        assertThat(stickies.size(), equalTo(initSize));
        for (int i = 0; i < stickies.size(); i++) {
            assertThat(stickies.get(i), empty());
        }

    }
}
