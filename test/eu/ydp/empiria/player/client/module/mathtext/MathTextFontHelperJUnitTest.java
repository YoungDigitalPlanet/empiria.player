package eu.ydp.empiria.player.client.module.mathtext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.xml.client.Element;
import com.mathplayer.player.geom.Font;

import eu.ydp.empiria.player.client.module.IInlineModule;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;
import eu.ydp.empiria.player.client.module.ModuleSocket;

public class MathTextFontHelperJUnitTest {

	@Test
	public void testInitializeFont() {
		IInlineModule testModule = mock(IInlineModule.class);
		ModuleSocket socket = mock(ModuleSocket.class);
		MathTextFontHelper helper = new MathTextFontHelper(testModule, socket);
		Element element = mock(Element.class);
		Set<InlineFormattingContainerType> inlineStyles = new HashSet<InlineFormattingContainerType>();
		inlineStyles.add(InlineFormattingContainerType.BOLD);		
		when(socket.getInlineFormattingTags(testModule)).thenReturn(inlineStyles);
		
		Font result = helper.initializeFont(element);
		
		assertThat(result.bold, is(equalTo(true)));
	}

}
