package eu.ydp.empiria.player.client.controller.feedback;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IContainerModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IUniqueModule;

class FeedbackPropertiesCollectorTestHelper {
	
	private IModule sender;
	
	private IContainerModule container;
	
	private Map<String, Variable> variables;
	
	public FeedbackPropertiesCollectorTestHelper(){
		variables = Maps.newHashMap();
	}
	
	public void createHierarchy(ModuleInfo[] infos){
		container = mock(IContainerModule.class);
		
		variables = Maps.newHashMap();
		List<IModule> children = Lists.newArrayList();
		
		for (ModuleInfo info: infos) {
			children.add(createUniqueModuleMock(container, info.getId(), createOutcomeVariables(info)));
		}
		
		sender = children.get(0);
		when(container.getChildren()).thenReturn(children);
		when(container.getParentModule()).thenReturn(null);
	}
	
	public Map<String, Outcome> createOutcomeVariables(ModuleInfo info){
		OutcomeCreator creator = new OutcomeCreator(info.getId());
		
		return OutcomeListBuilder.init().
						put(creator.createDoneOutcome(info.getDone())).
						put(creator.createTodoOutcome(info.getTodo())).
						put(creator.createErrorsOutcome(info.getErrors())).
						put(creator.createLastMistakenOutcome(info.isLastOk()? 0: 1)).
						getMap();
	}
	
	public IUniqueModule createUniqueModuleMock(HasChildren parent, String id, Map<String, ? extends Variable> variables){
		IUniqueModule module = mock(IUniqueModule.class);
		when(module.getIdentifier()).thenReturn(id);
		when(module.getParentModule()).thenReturn(parent);
		
		this.variables.putAll(variables);
		
		return module;
	}
	
	public Map<String, Variable> getVariables() {
		return variables;
	}

	public IModule getSender() {
		return sender;
	}
	
	public IModule getContainer() {
		return container;
	}
	
	protected static class ModuleInfo{
		
		private final String id;
		
		private boolean lastOk;
		
		private int todo;
		
		private int done;
		
		private int errors;
		
		public ModuleInfo(String id){
			this.id = id;
		}
		
		public static ModuleInfo create(String id){
			return new ModuleInfo(id);
		}
		
		public String getId() {
			return id;
		}
		
		public ModuleInfo setLastOk(boolean isLastOk) {
			this.lastOk = isLastOk;
			return this;
		}
		
		public boolean isLastOk() {
			return lastOk;
		}
		
		public ModuleInfo setTodo(int todo) {
			this.todo = todo;
			return this;
		}
		
		public int getTodo() {
			return todo;
		}
		
		public ModuleInfo setDone(int done) {
			this.done = done;
			return this;
		}
		
		public int getDone() {
			return done;
		}
		
		public ModuleInfo setErrors(int errors) {
			this.errors = errors;
			return this;
		}
		
		public int getErrors() {
			return errors;
		}
		
	}
}
