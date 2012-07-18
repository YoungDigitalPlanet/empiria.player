package eu.ydp.empiria.player.client.module.binding;

import java.util.HashMap;
import java.util.Map;

public abstract class BindingManagerBase implements BindingManager {

	private Map<BindingGroupIdentifier, BindingContext> contexts;
	private boolean acceptOnlyEmptyGroupIdentifier;
	
	public BindingManagerBase(boolean acceptOnlyEmptyGroupIdentifier){
		contexts = new HashMap<BindingGroupIdentifier, BindingContext>();
		this.acceptOnlyEmptyGroupIdentifier = acceptOnlyEmptyGroupIdentifier;
	}
	
	@Override
	public BindingContext getBindingContext(BindingGroupIdentifier groupIdentifier) {
		if (groupIdentifier == null)
			return null;
		for (BindingGroupIdentifier gi : contexts.keySet()){
			if (gi.equals(groupIdentifier)){
				return contexts.get(gi);
			}
		}
		if (!groupIdentifier.isEmptyGroupIdentifier()  &&  acceptOnlyEmptyGroupIdentifier){
			return null;
		}
		BindingContext newContext = createNewBindingContext();
		contexts.put(groupIdentifier, newContext);
		return newContext;
	}
	
	protected abstract BindingContext createNewBindingContext();

}
