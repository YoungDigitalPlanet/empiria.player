package eu.ydp.empiria.player.client.module;

import java.util.HashSet;
import java.util.Set;

public class InlineContainerHelper {
	
	public Set<InlineFormattingContainerType> getInlineStyles(IModule module) {
		Set<InlineFormattingContainerType> inlineStyles = new HashSet<InlineFormattingContainerType>();
		
		if (module != null) {
			HasChildren currParent = module.getParentModule();				
			while (currParent != null) {					
				if (currParent instanceof IInlineContainerModule) {
					IInlineContainerModule inlineContainerModule = (IInlineContainerModule) currParent;
					inlineStyles.add(inlineContainerModule.getType());
				}
				currParent = currParent.getParentModule();
			}
		}
		
		return inlineStyles;
	}
}
