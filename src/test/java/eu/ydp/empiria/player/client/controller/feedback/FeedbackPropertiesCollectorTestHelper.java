package eu.ydp.empiria.player.client.controller.feedback;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.base.ISingleViewWithBodyModule;
import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FeedbackPropertiesCollectorTestHelper {

    private IModule sender;

    private ISingleViewWithBodyModule container;

    private Map<String, Variable> variables;

    public FeedbackPropertiesCollectorTestHelper() {
        variables = Maps.newHashMap();
    }

    public void createHierarchy(ModuleInfo[] infos) {
        createHierarchy(infos, ISingleViewWithBodyModule.class);
    }

    public void createHierarchy(ModuleInfo[] infos, Class<? extends ISingleViewWithBodyModule> ModuleClass) {
        container = mock(ModuleClass);

        variables = Maps.newHashMap();
        List<IModule> children = Lists.newArrayList();

        for (ModuleInfo info : infos) {
            children.add(createUniqueModuleMock(container, info.getId(), createOutcomeVariables(info)));
        }

        sender = children.get(0);
        when(container.getChildren()).thenReturn(children);
    }

    public Map<String, Outcome> createOutcomeVariables(ModuleInfo info) {
        OutcomeCreator creator = new OutcomeCreator(info.getId());

        return OutcomeListBuilder.init().put(creator.createDoneOutcome(info.getDone())).put(creator.createTodoOutcome(info.getTodo()))
                .put(creator.createErrorsOutcome(info.getErrors())).put(creator.createLastChangeOutcome(info.getLastChange()))
                .put(creator.createLastMistakenOutcome(info.getLastMistaken())).getMap();
    }

    public IUniqueModule createUniqueModuleMock(HasChildren parent, String id, Map<String, ? extends Variable> variables) {
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

    protected static class ModuleInfo {

        private final String id;

        private LastMistaken lastMistaken;

        private int todo;

        private int done;

        private int errors;

        private String lastChange = "+Selected";

        public ModuleInfo(String id) {
            this.id = id;
        }

        public static ModuleInfo create(String id) {
            return new ModuleInfo(id);
        }

        public String getId() {
            return id;
        }

        public ModuleInfo setLastOk(LastMistaken isLastOk) {
            this.lastMistaken = isLastOk;
            return this;
        }

        public LastMistaken getLastMistaken() {
            return lastMistaken;
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

        public String getLastChange() {
            return lastChange;
        }

        public ModuleInfo setLastChange(String lastChange) {
            this.lastChange = lastChange;
            return this;
        }
    }
}
