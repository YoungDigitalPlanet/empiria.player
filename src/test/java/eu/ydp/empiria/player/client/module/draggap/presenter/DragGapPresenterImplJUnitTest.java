package eu.ydp.empiria.player.client.module.draggap.presenter;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.draggap.DragGapModuleModel;
import eu.ydp.empiria.player.client.module.draggap.SourceListManagerAdapter;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DragGapPresenterImplJUnitTest extends AbstractTestBase {

    private DragGapBasePresenter presenter;
    @Mock
    private DragGapView view;
    @Mock
    private DragGapModuleModel model;
    @Mock
    private AnswerEvaluationSupplier answerEvaluationSupplier;
    @Mock
    private SourceListManagerAdapter sourceListManagerAdapter;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    List<Boolean> evaluatedAnswers;

    @Override
    @Before
    public void setUp() {
        presenter = new DragGapBasePresenter(view, model, answerEvaluationSupplier, sourceListManagerAdapter);

        ModuleSocket moduleSocket = mock(ModuleSocket.class);
        when(moduleSocket.getInlineBodyGeneratorSocket()).thenReturn(inlineBodyGeneratorSocket);
        presenter.setModuleSocket(moduleSocket);
    }

    @Test
    public void shouldBindView() {
        // when
        presenter.bindView();

        // then
        verify(view).updateStyle(UserAnswerType.DEFAULT);
    }

    @Test
    public void shouldReset() {
        // when
        presenter.reset();

        // then
        InOrder inOrder = Mockito.inOrder(view, model);
        inOrder.verify(view).removeContent();
        inOrder.verify(model).reset();
        inOrder.verify(view).updateStyle(UserAnswerType.DEFAULT);
    }

    @Test
    public void shouldSetContent() {
        // given
        String itemId = "content";
        SourcelistItemValue item = new SourcelistItemValue(SourcelistItemType.IMAGE, "value", itemId);

        when(sourceListManagerAdapter.getItemById(itemId)).thenReturn(item);

        // when
        presenter.setContent(itemId);

        // then
        InOrder inOrder = Mockito.inOrder(view, model);
        inOrder.verify(view).setItemContent(item, inlineBodyGeneratorSocket);
        inOrder.verify(model).addAnswer(itemId);
    }

    @Test
    public void shouldRemoveContent() {
        // when
        presenter.removeContent();

        // then
        InOrder inOrder = Mockito.inOrder(view, model);
        inOrder.verify(view).removeContent();
        inOrder.verify(model).clearAnswers();
    }

    @Test
    public void shouldMarkAnswers_UnmarkCorrect() {
        // when
        presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.UNMARK);

        // then
        verify(view).updateStyle(UserAnswerType.DEFAULT);
    }

    @Test
    public void shouldMarkAnswers_UnmarkWrong() {
        // when
        presenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.UNMARK);

        // then
        verify(view).updateStyle(UserAnswerType.DEFAULT);
    }

    @Test
    public void shouldMarkAnswers_MarkCorrectAndEmptyAnswers() {
        // given
        mockAnswers(true);

        // when
        presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);

        // then
        verify(view).updateStyle(UserAnswerType.NONE);
    }

    @Test
    public void shouldMarkAnswers_MarkCorrectAndCorrectAnswer() {
        // given
        mockAnswers(false);
        when(evaluatedAnswers.get(0)).thenReturn(true);

        // when
        presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);

        // then
        verify(view).updateStyle(UserAnswerType.CORRECT);
    }

    @Test
    public void shouldMarkAnswers_MarkCorrectAndIncorrectAnswer() {
        // given
        mockAnswers(false);
        when(evaluatedAnswers.get(0)).thenReturn(false);

        // when
        presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);

        // then
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldMarkAnswers_MarkWrongAndCorrectAnswer() {
        // given
        mockAnswers(false);
        when(evaluatedAnswers.get(0)).thenReturn(true);

        // when
        presenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.MARK);

        // then
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldMarkAnswers_MarkWrongAndIncorrectAnswer() {
        // given
        mockAnswers(false);
        when(evaluatedAnswers.get(0)).thenReturn(false);

        // when
        presenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.MARK);

        // then
        verify(view).updateStyle(UserAnswerType.WRONG);
    }

    private void mockAnswers(boolean answersAreEmpty) {
        Response response = mock(Response.class);
        when(model.getResponse()).thenReturn(response);
        evaluatedAnswers = mock(List.class);
        when(evaluatedAnswers.isEmpty()).thenReturn(answersAreEmpty);
        when(answerEvaluationSupplier.evaluateAnswer(response)).thenReturn(evaluatedAnswers);
    }

    @Test
    public void shouldShowAnswers_CorrectAndEmptyAnswers() {
        // given
        List<String> answers = Lists.newArrayList();
        when(model.getCorrectAnswers()).thenReturn(answers);

        // when
        presenter.showAnswers(ShowAnswersType.CORRECT);

        // then
        verify(view).removeContent();
    }

    @Test
    public void shouldShowAnswers_CorrectAndAnswerExists() {
        // given
        String answerToSet = "answer";
        List<String> answers = Lists.newArrayList(answerToSet);
        when(model.getCorrectAnswers()).thenReturn(answers);

        SourcelistItemValue item = new SourcelistItemValue(SourcelistItemType.IMAGE, "value", answerToSet);
        when(sourceListManagerAdapter.getItemById(answerToSet)).thenReturn(item);

        // when
        presenter.showAnswers(ShowAnswersType.CORRECT);

        // then
        verify(view).setItemContent(item, inlineBodyGeneratorSocket);
    }

    @Test
    public void shouldShowAnswers_UserAndEmptyAnswers() {
        // given
        List<String> answers = Lists.newArrayList();
        when(model.getCurrentAnswers()).thenReturn(answers);

        // when
        presenter.showAnswers(ShowAnswersType.USER);

        // then
        verify(view).removeContent();
    }

    @Test
    public void shouldShowAnswers_UserAndAnswerExists() {
        // given
        String answerToSet = "answer";
        List<String> answers = Lists.newArrayList(answerToSet);
        when(model.getCurrentAnswers()).thenReturn(answers);

        SourcelistItemValue item = new SourcelistItemValue(SourcelistItemType.IMAGE, "value", answerToSet);
        when(sourceListManagerAdapter.getItemById(answerToSet)).thenReturn(item);

        // when
        presenter.showAnswers(ShowAnswersType.USER);

        // then
        verify(view).setItemContent(item, inlineBodyGeneratorSocket);
    }

    @Test
    public void shouldSetLocked_true() {
        setLockedAndVerify(true);
    }

    @Test
    public void shouldSetLocked_false() {
        setLockedAndVerify(false);
    }

    private void setLockedAndVerify(boolean locked) {
        // when
        presenter.setLocked(locked);

        // then
        verify(view).lock(locked);
        verify(view).setDragDisabled(locked);
    }

    @Test
    public void shouldSetWidthAndHeightOnView() throws Exception {
        int width = 123;
        int height = 456;
        HasDimensions size = new Size(width, height);

        presenter.setGapDimensions(size);

        verify(view).setWidth(width);
        verify(view).setHeight(height);
    }

}
