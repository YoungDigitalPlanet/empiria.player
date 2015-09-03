package eu.ydp.empiria.player.client.module.colorfill.presenter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillModelProxy;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillViewBuilder;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.AreaContainer;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ColorfillInteractionPresenterImplTest {

    @InjectMocks
    private ColorfillInteractionPresenterImpl presenter;
    @Mock
    private ResponseUserAnswersConverter responseUserAnswersConverter;
    @Mock
    private ResponseAnswerByViewBuilder responseAnswerByViewBuilder;
    @Mock
    private ColorfillViewBuilder colorfillViewBuilder;
    @Mock
    private ColorfillInteractionView interactionView;
    @Mock
    private ColorfillModelProxy model;
    @Mock
    private ColorButtonsController colorButtonController;
    @Mock
    private UserToResponseAreaMapper areaMapper;

    private ColorfillInteractionBean bean;

    @Before
    public void setUp() throws Exception {
        prepareBean();
        presenter.setBean(bean);
    }

    private void prepareBean() {
        bean = new ColorfillInteractionBean();
        AreaContainer areasContainer = new AreaContainer();
        List<Area> areas = Lists.newArrayList(new Area(1, 1), new Area(2, 2));
        areasContainer.setAreas(areas);
        bean.setAreas(areasContainer);
    }

    @Test
    public void shouldBuildView() throws Exception {
        presenter.bindView();
        verify(colorfillViewBuilder).buildView(bean, presenter);
    }

    @Test
    public void shouldRedrawAreaWithCurrentColorAndRebuildResponses() throws Exception {
        // given
        Area clickedArea = new Area(123, 253);

        ColorModel currentColor = ColorModel.createFromRgbString("00ff00");
        when(colorButtonController.getCurrentSelectedButtonColor()).thenReturn(currentColor);

        List<String> rebuildedAnswers = Lists.newArrayList("a", "b");
        when(responseAnswerByViewBuilder.buildNewResponseAnswersByCurrentImage(bean.getAreas().getAreas())).thenReturn(rebuildedAnswers);

        // when
        presenter.imageColorChanged(clickedArea);

        // them
        verify(interactionView).setColor(clickedArea, currentColor);
        verify(model).updateUserAnswers();
    }

    @Test
    public void shouldIgnoreAreaClickWhenNoColorIsCurrentlySelected() throws Exception {
        // given
        Area clickedArea = new Area(123, 253);
        when(colorButtonController.getCurrentSelectedButtonColor()).thenReturn(null);

        // when
        presenter.imageColorChanged(clickedArea);

        // then
        verify(colorButtonController).getCurrentSelectedButtonColor();
        verifyNoMoreInteractionsOnAllMocks();
    }

    @Test
    public void shouldIgnoreAreaClickWhenLock() throws Exception {
        // given
        Area clickedArea = new Area(123, 253);
        presenter.setLocked(true);

        // when
        presenter.imageColorChanged(clickedArea);

        // then
        verifyNoMoreInteractionsOnAllMocks();
    }

    @Test
    public void shouldReactOnColorButtonClicked() throws Exception {
        ColorModel color = ColorModel.createFromRgbString("00ff00");

        presenter.buttonClicked(color);

        verify(colorButtonController).colorButtonClicked(color);
        verifyNoMoreInteractionsOnAllMocks();
    }

    @Test
    public void shouldShowUserAnswers() throws Exception {
        List<String> currentAnswers = Lists.newArrayList("a", "b");
        Map<Area, ColorModel> areaToColorMap = Maps.newHashMap();
        when(model.getUserAnswers()).thenReturn(areaToColorMap);

        when(responseUserAnswersConverter.convertResponseAnswersToAreaColorMap(currentAnswers)).thenReturn(areaToColorMap);

        presenter.showAnswers(ShowAnswersType.USER);

        InOrder inOrder = Mockito.inOrder(interactionView);
        inOrder.verify(interactionView).showUserAnswers();
        inOrder.verify(interactionView).setColors(areaToColorMap);
    }

    @Test
    public void shouldShowAnswersInCorrectMode() throws Exception {
        presenter.showAnswers(ShowAnswersType.CORRECT);

        verify(interactionView).showCorrectAnswers();

        verifyNoMoreInteractionsOnAllMocks();
    }

    @Test
    public void markCorrectAnswers() {
        List<Area> areaList = Lists.newArrayList();
        List<Area> areaMapperList = Lists.newArrayList();
        doReturn(areaMapperList).when(areaMapper).mapResponseToUser(eq(areaList));
        doReturn(areaList).when(model).getUserCorrectAnswers();
        presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);
        verify(interactionView).markCorrectAnswers(eq(areaMapperList));
        verifyNoMoreInteractions(interactionView);
    }

    @Test
    public void markWrongAnswers() {
        List<Area> areaList = Lists.newArrayList();
        List<Area> areaMapperList = Lists.newArrayList();
        doReturn(areaMapperList).when(areaMapper).mapResponseToUser(eq(areaList));
        doReturn(areaList).when(model).getUserCorrectAnswers();
        presenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.MARK);
        verify(interactionView).markWrongAnswers(eq(areaMapperList));
        verifyNoMoreInteractions(interactionView);
    }

    @Test
    public void unmarkCorrectAnswers() {
        List<Area> areaList = Lists.newArrayList();
        doReturn(areaList).when(model).getUserCorrectAnswers();
        presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.UNMARK);
        verify(interactionView).unmarkCorrectAnswers();
        verifyNoMoreInteractions(interactionView);
    }

    @Test
    public void unmarkWrongAnswers() {
        List<Area> areaList = Lists.newArrayList();
        doReturn(areaList).when(model).getUserCorrectAnswers();
        presenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.UNMARK);
        verify(interactionView).unmarkWrongAnswers();
        verifyNoMoreInteractions(interactionView);
    }

    @Test
    public void shouldResetViewOnReset() throws Exception {
        presenter.reset();
        verify(interactionView).reset();
        verify(areaMapper).reset();
        verifyNoMoreInteractionsOnAllMocks();
    }

    private void verifyNoMoreInteractionsOnAllMocks() {
        Mockito.verifyNoMoreInteractions(responseUserAnswersConverter, responseAnswerByViewBuilder, colorfillViewBuilder, colorButtonController,
                interactionView, model);
    }
}
