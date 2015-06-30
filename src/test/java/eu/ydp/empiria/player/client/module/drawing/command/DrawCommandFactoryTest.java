package eu.ydp.empiria.player.client.module.drawing.command;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.gin.module.ModuleScopedLazyProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DrawCommandFactoryTest {
    @Mock
    private ModuleScopedLazyProvider<ClearAllDrawCommand> clearAllComandProvider;
    @Mock
    private ClearAllDrawCommand command;
    @InjectMocks
    private DrawCommandFactory instance;

    @Before
    public void before() {
        doReturn(command).when(clearAllComandProvider).get();
    }

    @Test
    public void createClearAllCommand() throws Exception {
        DrawCommand drawCommand = instance.createCommand(DrawCommandType.CLEAR_ALL);
        assertThat(drawCommand).isNotNull();
        assertThat(drawCommand).isInstanceOf(ClearAllDrawCommand.class);
        assertThat(drawCommand).isEqualTo(command);
        verify(clearAllComandProvider).get();
    }

    @Test
    public void createOtherCommand() throws Exception {
        ArrayList<DrawCommandType> allComandsType = Lists.newArrayList(DrawCommandType.values());
        allComandsType.remove(DrawCommandType.CLEAR_ALL);

        for (DrawCommandType type : allComandsType) {
            instance.createCommand(type);
            verifyZeroInteractions(clearAllComandProvider);
        }
        DrawCommand drawCommand = instance.createCommand(null);
        assertThat(drawCommand).isNull();
        verifyZeroInteractions(clearAllComandProvider);
    }

}
