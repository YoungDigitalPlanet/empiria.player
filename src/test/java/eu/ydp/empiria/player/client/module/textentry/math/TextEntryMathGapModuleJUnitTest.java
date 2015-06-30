package eu.ydp.empiria.player.client.module.textentry.math;

import com.google.common.collect.Lists;
import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.gap.GapModulePresenter;
import eu.ydp.empiria.player.client.module.math.MathGapModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TextEntryMathGapModuleJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private static class CustomGuiceModule implements Module {
        private MathGapModel mathGapModel;

        public CustomGuiceModule(MathGapModel mathGapModel) {
            this.mathGapModel = mathGapModel;
        }

        @Override
        public void configure(Binder binder) {
            binder.bind(TextEntryMathGapModulePresenter.class).toInstance(mock(TextEntryMathGapModulePresenter.class));
            binder.bind(MathGapModel.class).annotatedWith(ModuleScoped.class).toInstance(mathGapModel);
            binder.bind(ResponseSocket.class).toInstance(mock(ResponseSocket.class));
        }
    }

    @Before
    public void before() {
        MathGapModel mathGapModel = new MathGapModel();
        setUp(new Class<?>[]{}, new CustomGuiceModule(mathGapModel));
    }

    @Test
    public void testIfCorrectAnswerIsFound() {
        TextEntryMathGapModule textGap = mockTextGap();
        assertTrue(textGap.getCorrectAnswer().equals("13"));
    }

    @Test
    public void testIfMarkAnswersWorksCorrectly() {

        TextEntryGapModuleMock gap1 = mockTextGap();
        TextEntryGapModuleMock gap2 = mockTextGap();
        TextEntryGapModuleMock gap3 = mockTextGap();

        gap1.setMockedResponse("13");
        gap2.setMockedResponse("4");
        gap3.setMockedResponse("");

        gap1.setEvaluatedResponse(true);
        gap2.setEvaluatedResponse(false);
        gap3.setEvaluatedResponse(false);

        gap1.markAnswers(true);
        gap2.markAnswers(true);
        gap3.markAnswers(true);

        verify(gap1.getPresenter()).setMarkMode(GapModulePresenter.CORRECT);
        verify(gap2.getPresenter()).setMarkMode(GapModulePresenter.WRONG);
        verify(gap3.getPresenter()).setMarkMode(GapModulePresenter.NONE);
    }

    @BeforeClass
    public static void prepareTestEnviroment() {
        /**
         * disable GWT.create() behavior for pure JUnit testing
         */
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void restoreEnviroment() {
        /**
         * restore GWT.create() behavior
         */
        GWTMockUtilities.restore();
    }

    public TextEntryGapModuleMock mockTextGap(Map<String, String> styles) {
        return new TextEntryGapModuleMock(styles);
    }

    public TextEntryGapModuleMock mockTextGap() {
        return new TextEntryGapModuleMock(new HashMap<String, String>());
    }

    private class TextEntryGapModuleMock extends TextEntryMathGapModule {

        private boolean evaluatedResponse;

        public TextEntryGapModuleMock(Map<String, String> styles) {
            mathGapModel = injector.getInstance(Key.get(MathGapModel.class, ModuleScoped.class));
            textEntryPresenter = injector.getInstance(TextEntryMathGapModulePresenter.class);
            postConstruct();
        }

        protected String mockedResponse;

        @Override
        protected Response getResponse() {
            Response response = mock(Response.class);
            response.groups = Lists.newArrayList();
            response.correctAnswers = new CorrectAnswers();
            response.correctAnswers.add(new ResponseValue("13"));
            response.correctAnswers.add(new ResponseValue("1322"));
            response.correctAnswers.add(new ResponseValue("1"));

            return response;
        }

        @Override
        public int getFontSize() {
            return 20;
        }

        public void setMockedResponse(String response) {
            mockedResponse = response;
        }

        @Override
        protected String getCurrentResponseValue() {
            return mockedResponse;
        }

        public GapModulePresenter getPresenter() {
            return presenter;
        }

        public void setEvaluatedResponse(boolean evaluatedResponse) {
            this.evaluatedResponse = evaluatedResponse;
        }

        @Override
        protected List<Boolean> getEvaluatedResponse() {
            List<Boolean> evaluations = Lists.newArrayList();
            evaluations.add(evaluatedResponse);
            return evaluations;
        }

    }
}
