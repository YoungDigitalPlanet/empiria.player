package eu.ydp.empiria.player.client.module.math;


public class MathModuleHelperMock extends MathModuleHelper {
	
	public MathModuleHelperMock(){
		super(null, null, null, null);
	}
	
	/*public MathModuleHelperMock(Element moduleElement,
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
		
	}*/	
}