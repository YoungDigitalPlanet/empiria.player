package eu.ydp.empiria.player.client.module.math;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.xml.client.Element;
import com.mathplayer.player.MathPlayerManager;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class MathModuleHelperMock extends MathModuleHelper {
	
	public MathModuleHelperMock(Element moduleElement,
			ModuleSocket moduleSocket, Response response, IModule module) {
		super(moduleElement, moduleSocket, response, module);
	}
	
	@Override
	public MathPlayerManager createMathPlayerManager() {			
		return null;
	}
	
	@Override
	public ExListBox createExListBox() {
		return mock(ExListBox.class);
	}
	
	@Override
	public InlineHTML createInlineHTML(String html) {			
		return mock(InlineHTML.class);
	}
	
	@Override
	public InlineChoiceGap createInlineChoiceGap(ExListBox listBox,
			ArrayList<String> listBoxIdentifiers,
			boolean inlineCholiceGapShowEmptyOption) {		
		return new InlineChoiceGapMock(listBox, listBoxIdentifiers, inlineCholiceGapShowEmptyOption);			
	}
	
	@Override
	protected PlayerGinjector getPlayerGinjectorInstance() {		
		PlayerGinjector injector = mock(PlayerGinjector.class);
		when(injector.getStyleNameConstants()).thenReturn(mock(StyleNameConstants.class));
		return injector;
	}
	
	public class InlineChoiceGapMock extends InlineChoiceGap {

		public InlineChoiceGapMock(ExListBox listBox, List<String> options,
				boolean hasEmptyOption) {
			super(listBox, options, hasEmptyOption);
		}
		
		@Override
		public FlowPanel createFlowPanel() {		
			return mock(FlowPanel.class);
		}
		
	}	
}