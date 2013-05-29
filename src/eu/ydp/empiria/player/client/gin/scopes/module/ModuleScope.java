package eu.ydp.empiria.player.client.gin.scopes.module;

import eu.ydp.gwtutil.client.gin.scopes.AbstractCustomScope;

public class ModuleScope extends AbstractCustomScope {

	private final ModuleCreationContext moduleCreationContext;
	
	
	public ModuleScope(ModuleCreationContext moduleCreationContext) {
		this.moduleCreationContext = moduleCreationContext;
	}

	@Override
	public boolean equals(Object object) {
		if( !(object instanceof ModuleScope) ){
			return false;
		}
		ModuleScope moduleScope = (ModuleScope) object;
		ModuleCreationContext context = moduleScope.getModuleCreationContext();
		
		return this.moduleCreationContext.equals(context);
	}

	@Override
	public int hashCode() {
		return moduleCreationContext.hashCode();
	}

	public ModuleCreationContext getModuleCreationContext() {
		return moduleCreationContext;
	}

}
