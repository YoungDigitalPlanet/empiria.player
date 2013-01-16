package eu.ydp.empiria.player.client.module;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import eu.ydp.empiria.player.client.module.containers.DivModule;

public class InlineContainerStylesExtractorJUnitTest {

	@Test
	public void testGetInlineStyles() {
		InlineContainerStylesExtractor helper = new InlineContainerStylesExtractor();		
		IModule testModule = mock(IModule.class);
		DivModule divModule = mock(DivModule.class);		
		InlineContainerModule inlineContainerModuleBold = mock(InlineContainerModule.class);
		
		when(inlineContainerModuleBold.getType()).
			thenReturn(InlineFormattingContainerType.BOLD);
		
		InlineContainerModule inlineContainerModuleItalic = mock(InlineContainerModule.class);
		when(inlineContainerModuleItalic.getType()).
			thenReturn(InlineFormattingContainerType.ITALIC);		
		when(inlineContainerModuleItalic.getParentModule()).
			thenReturn(inlineContainerModuleBold);
		
		when(divModule.getParentModule()).
			thenReturn(inlineContainerModuleItalic);
		
		when(testModule.getParentModule()).
			thenReturn(divModule);

		Set<InlineFormattingContainerType> result = helper.getInlineStyles(testModule);

		assertThat(result.size(), is(equalTo(2)));
		Iterator<InlineFormattingContainerType> resultIterator = result.iterator();
		assertThat(InlineFormattingContainerType.BOLD.equals(resultIterator.next()), is(equalTo(true)));
		assertThat(InlineFormattingContainerType.ITALIC.equals(resultIterator.next()), is(equalTo(true)));
	}

}
