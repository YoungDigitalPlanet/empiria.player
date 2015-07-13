package eu.ydp.empiria.player.client.module.bonus;

import eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyObject;
import eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyService;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.Wrapper;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SwiffyBonusTest {

    @InjectMocks
    private SwiffyBonus bonus;
    @Mock
    private BonusPopupPresenter presenter;
    @Mock
    private EmpiriaPaths empiriaPaths;
    @Mock
    private SwiffyService swiffyService;

    @Test
    public void execute() {
        // given
        String asset = "bonus1";
        String fullUrl = "http://x.y.z/bonus1/bonus1.js";
        Size size = new Size(100, 100);
        when(empiriaPaths.getCommonsFilePath(asset + ".js")).thenReturn(fullUrl);
        SwiffyObject swiffyObject = mock(SwiffyObject.class);
        when(swiffyService.getSwiffyObject(asset, fullUrl)).thenReturn(swiffyObject);
        bonus.setAsset(asset, size);

        // when
        bonus.execute();

        // then
        verify(presenter).showAnimation(eq(swiffyObject), eq(size), any(EndHandler.class));
    }

    @Test
    public void handleEnd() {
        // given
        String asset = "bonus1";
        Size size = new Size(100, 100);
        final Wrapper<EndHandler> endHandlerWrapper = new Wrapper<EndHandler>();
        startInterceptionOfEndHandler(endHandlerWrapper);
        bonus.setAsset(asset, size);
        bonus.execute();

        // when
        endHandlerWrapper.getInstance().onEnd();

        // then
        verify(swiffyService).clear(asset);
    }

    private void startInterceptionOfEndHandler(final Wrapper<EndHandler> endHandlerWrapper) {
        Answer<Void> endHandlerRetrievalAnswer = new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                EndHandler endHandler = (EndHandler) invocation.getArguments()[2];
                endHandlerWrapper.setInstance(endHandler);
                return null;
            }
        };
        doAnswer(endHandlerRetrievalAnswer).when(presenter).showAnimation(any(SwiffyObject.class), any(Size.class), any(EndHandler.class));
    }
}
