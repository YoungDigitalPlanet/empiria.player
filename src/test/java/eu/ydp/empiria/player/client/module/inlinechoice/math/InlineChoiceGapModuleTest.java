package eu.ydp.empiria.player.client.module.inlinechoice.math;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.module.math.MathGapModel;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.EventScope;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class InlineChoiceGapModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {
    InlineChoiceMathGapModule instance;
    ExListBox listBox;
    EventsBus eventsBus;

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    private static class CustomGuiceModule implements Module {
        private final InlineChoiceMathGapModulePresenter presenter;
        private final MathGapModel mathGapModel;

        public CustomGuiceModule(InlineChoiceMathGapModulePresenter presenter, MathGapModel mathGapModel) {
            this.presenter = presenter;
            this.mathGapModel = mathGapModel;
        }

        @Override
        public void configure(Binder binder) {
            binder.bind(InlineChoiceMathGapModulePresenter.class).toInstance(presenter);
            binder.bind(MathGapModel.class).annotatedWith(ModuleScoped.class).toInstance(mathGapModel);
        }
    }

    @Before
    public void before() {
        InlineChoiceMathGapModulePresenter presenter = mock(InlineChoiceMathGapModulePresenter.class);
        listBox = mock(ExListBox.class);
        doReturn(listBox).when(presenter).getListBox();
        MathGapModel mathGapModel = new MathGapModel();
        setUp(new Class<?>[]{}, new Class<?>[]{}, new Class<?>[]{EventsBus.class}, new CustomGuiceModule(presenter, mathGapModel));
        eventsBus = injector.getInstance(EventsBus.class);
        instance = injector.getInstance(InlineChoiceMathGapModule.class);
    }

    @Test
    public void testPostConstruct() {
        verify(eventsBus).addHandler(Matchers.eq(PlayerEvent.getType(PlayerEventTypes.PAGE_SWIPE_STARTED)), Matchers.eq(instance), Matchers.any(EventScope.class));
    }

    @Test
    public void testOnPlayerEvent() {
        PlayerEvent event = mock(PlayerEvent.class);
        when(event.getType()).thenReturn(PlayerEventTypes.PAGE_SWIPE_STARTED);
        instance.onPlayerEvent(event);

        verify(listBox).hidePopup();
        Set<PlayerEventTypes> types = new HashSet<>(Arrays.asList(PlayerEventTypes.values()));
        types.remove(PlayerEventTypes.PAGE_SWIPE_STARTED);
        for (PlayerEventTypes type : types) {
            when(event.getType()).thenReturn(type);
            instance.onPlayerEvent(event);
        }
        verify(listBox).hidePopup();
    }

    public Map<String, String> mockMathStyles(boolean hasEmptyOption) {
        Map<String, String> mathStyles = new HashMap<>();

        String styleValue = (hasEmptyOption) ? EmpiriaStyleNameConstants.VALUE_SHOW : EmpiriaStyleNameConstants.VALUE_HIDE;

        mathStyles.put(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION, styleValue);

        return mathStyles;
    }
}
