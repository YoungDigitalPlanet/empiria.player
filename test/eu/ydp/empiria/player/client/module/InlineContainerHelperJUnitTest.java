package eu.ydp.empiria.player.client.module;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import eu.ydp.empiria.player.client.module.containers.DivModule;

public class InlineContainerHelperJUnitTest {

	@Test
	public void testGetInlineStyles() {
		InlineContainerHelper helper = new InlineContainerHelper();
		IModule testModule = mock(IModule.class);
		DivModule divModule = mock(DivModule.class);
		InlineContainerModule inlineContainerModuleBold = mock(InlineContainerModule.class);
		when(inlineContainerModuleBold.getType()).thenReturn(InlineFormattingContainerType.BOLD);
		InlineContainerModule inlineContainerModuleItalic = mock(InlineContainerModule.class);
		when(inlineContainerModuleItalic.getType()).thenReturn(InlineFormattingContainerType.ITALIC);
		when(inlineContainerModuleItalic.getParentModule()).thenReturn(inlineContainerModuleBold);
		when(divModule.getParentModule()).thenReturn(inlineContainerModuleItalic);		
		when(testModule.getParentModule()).thenReturn(divModule);			
		
		Object[] result = helper.getInlineStyles(testModule).toArray();
		
		assertThat(result.length, is(equalTo(2)));
		assertThat(InlineFormattingContainerType.BOLD.equals(result[0]), is(equalTo(true)));
		assertThat(InlineFormattingContainerType.ITALIC.equals(result[1]), is(equalTo(true)));
	}

}
