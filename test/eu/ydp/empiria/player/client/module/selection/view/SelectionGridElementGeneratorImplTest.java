package eu.ydp.empiria.player.client.module.selection.view;

import static org.mockito.Mockito.mock;
import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest({Widget.class})
public class SelectionGridElementGeneratorImplTest {
	
	@Mock
	private InlineBodyGeneratorSocket inlineBodyGenerator;

	@Mock
	private StyleNameConstants styleNameConstants;

	@Mock
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
	@InjectMocks
	private SelectionGridElementGeneratorImpl selectionGridElementGeneratorImpl;

	private SelectionGridElementGeneratorImpl gridElementGenerator;
	
	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}
	
	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}
	
	@Before 
	public void setup() {
		userInteractionHandlerFactory = mock(UserInteractionHandlerFactory.class);
		styleNameConstants = mock(StyleNameConstants.class);
		inlineBodyGenerator = mock(InlineBodyGeneratorSocket.class);
		
		gridElementGenerator = new SelectionGridElementGeneratorImpl(
				styleNameConstants, 
				userInteractionHandlerFactory);
		gridElementGenerator.setInlineBodyGenerator(inlineBodyGenerator);
	}
	
	@Test
	public void testGetButtonElementPositionFor() throws Exception {
		SelectionGridElementPosition buttonPosition = gridElementGenerator.getButtonElementPositionFor(0, 0);

		
		//then
		Assert.assertEquals(buttonPosition.getColumnNumber(), 1);
		Assert.assertEquals(buttonPosition.getRowNumber(), 1);
	}

	@Test
	public void testGetChoiceLabelElementPosition() throws Exception {
		SelectionGridElementPosition choicePosition = gridElementGenerator.getChoiceLabelElementPosition(0);
		
		
		//then
		Assert.assertEquals(choicePosition.getColumnNumber(), 1);
		Assert.assertEquals(choicePosition.getRowNumber(), 0);
	}

	@Test
	public void testGetItemLabelElementPosition() throws Exception {
		SelectionGridElementPosition itemPosition = gridElementGenerator.getItemLabelElementPosition(0);
		

		//then
		Assert.assertEquals(itemPosition.getColumnNumber(), 0);
		Assert.assertEquals(itemPosition.getRowNumber(), 1);
	}
}