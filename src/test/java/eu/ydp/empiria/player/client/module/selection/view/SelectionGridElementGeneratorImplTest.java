package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.MarkAnswersStyleNameConstants;
import eu.ydp.empiria.player.client.module.selection.SelectionStyleNameConstants;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

@RunWith(GwtMockitoTestRunner.class)
public class SelectionGridElementGeneratorImplTest {

    @Mock
    private InlineBodyGeneratorSocket inlineBodyGenerator;

    @Mock
    private MarkAnswersStyleNameConstants styleNameConstants;

    @Mock
    private SelectionStyleNameConstants selectionStyleNameConstants;

    @Mock
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    @InjectMocks
    private SelectionGridElementGeneratorImpl selectionGridElementGeneratorImpl;

    private SelectionGridElementGeneratorImpl gridElementGenerator;

    @Before
    public void setup() {
        userInteractionHandlerFactory = mock(UserInteractionHandlerFactory.class);
        styleNameConstants = mock(MarkAnswersStyleNameConstants.class);
        inlineBodyGenerator = mock(InlineBodyGeneratorSocket.class);

        gridElementGenerator = new SelectionGridElementGeneratorImpl(styleNameConstants, userInteractionHandlerFactory, selectionStyleNameConstants);
        gridElementGenerator.setInlineBodyGenerator(inlineBodyGenerator);
    }

    @Test
    public void testGetButtonElementPositionFor() throws Exception {
        SelectionGridElementPosition buttonPosition = gridElementGenerator.getButtonElementPositionFor(0, 0);

        // then
        Assert.assertEquals(buttonPosition.getColumnNumber(), 1);
        Assert.assertEquals(buttonPosition.getRowNumber(), 1);
    }

    @Test
    public void testGetChoiceLabelElementPosition() throws Exception {
        SelectionGridElementPosition choicePosition = gridElementGenerator.getChoiceLabelElementPosition(0);

        // then
        Assert.assertEquals(choicePosition.getColumnNumber(), 1);
        Assert.assertEquals(choicePosition.getRowNumber(), 0);
    }

    @Test
    public void testGetItemLabelElementPosition() throws Exception {
        SelectionGridElementPosition itemPosition = gridElementGenerator.getItemLabelElementPosition(0);

        // then
        Assert.assertEquals(itemPosition.getColumnNumber(), 0);
        Assert.assertEquals(itemPosition.getRowNumber(), 1);
    }
}
