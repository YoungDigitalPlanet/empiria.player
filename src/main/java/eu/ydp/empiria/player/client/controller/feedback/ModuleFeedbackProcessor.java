package eu.ydp.empiria.player.client.controller.feedback;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.google.common.base.Optional.fromNullable;

public class ModuleFeedbackProcessor {

    private FeedbackRegistry feedbackRegistry;
    private FeedbackConditionMatcher matcher;
    protected SoundActionProcessor soundProcessor;
    private FeedbackPropertiesCollector propertiesCollector;
    private Provider<FeedbackActionCollector> feedbackActionCollectorProvider;
    protected FeedbackActionCollector feedbackActionCollector;
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    @Inject
    public ModuleFeedbackProcessor(@Assisted InlineBodyGeneratorSocket inlineBodyGeneratorSocket, FeedbackRegistry feedbackRegistry,
                                   FeedbackConditionMatcher matcher, SoundActionProcessor soundProcessor, FeedbackPropertiesCollector propertiesCollector,
                                   Provider<FeedbackActionCollector> feedbackActionCollectorProvider) {
        this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
        this.feedbackRegistry = feedbackRegistry;
        this.matcher = matcher;
        this.soundProcessor = soundProcessor;
        this.propertiesCollector = propertiesCollector;
        this.feedbackActionCollectorProvider = feedbackActionCollectorProvider;
        initializeFeedbackActionCollector();
    }

    private void initializeFeedbackActionCollector() {
        feedbackActionCollector = feedbackActionCollectorProvider.get();
    }

    public void processFeedbacks(ItemOutcomeStorageImpl outcomeManager, IUniqueModule sender) {
        if (feedbackRegistry.hasFeedbacks()) {
            process(sender, outcomeManager);
        }
    }

    private void process(IModule sender, ItemOutcomeStorageImpl variables) {
        initializeFeedbackActionCollector();
        feedbackActionCollector.setSource(sender);
        propertiesCollector.setVariables(createVariables(variables));
        processFeedbackActionCollector(sender);
    }

    private Map<String, Outcome> createVariables(ItemOutcomeStorageImpl outcomeStorage) {
        HashMap<String, Outcome> result = new HashMap<>();

        for (String id : outcomeStorage.getVariableIdentifiers()) {
            result.put(id, outcomeStorage.getVariable(id));
        }

        return result;
    }

    private void processFeedbackActionCollector(IModule source) {
        if (fromNullable(source).isPresent()) {
            appendPropertiesToSource(source);
            appendActionsToSource(source);
            processFeedbackActionCollector(source.getParentModule());
            processActions(source);
        }
    }

    private void appendPropertiesToSource(IModule source) {
        FeedbackProperties properties = getPropertiesForModule(source);
        feedbackActionCollector.appendPropertiesToSource(properties, source);
    }

    private void appendActionsToSource(IModule source) {
        List<Feedback> feedbackList = feedbackRegistry.getModuleFeedbacks(source);

        if (fromNullable(feedbackList).isPresent()) {
            for (Feedback feedback : feedbackList) {
                appendMatchedFeedbackActions(feedback, source);
            }
        }
    }

    private void appendMatchedFeedbackActions(Feedback feedback, IModule source) {
        FeedbackProperties properties = feedbackActionCollector.getSourceProperties(source);

        if (!isUnselectFeedback(properties)) {
            boolean match = matcher.match(feedback.getCondition(), properties);
            if (match) {
                feedbackActionCollector.appendActionsToSource(feedback.getActions(), source);
            }
        }
    }

    private Boolean isUnselectFeedback(FeedbackProperties properties) {
        return (Boolean) properties.getProperty(FeedbackPropertyName.UNSELECT);
    }

    protected void processActions(IModule module) {
        List<FeedbackActionProcessor> processors = getFeedbackProcessors(module);
        FeedbackProperties sourceProperties = feedbackActionCollector.getSourceProperties(module);
        FeedbackMark mark = FeedbackMark.getMark(sourceProperties);

        for (FeedbackActionProcessor processor : processors) {
            List<FeedbackAction> actions = feedbackActionCollector.getActions();
            List<FeedbackAction> processedActions = processor.processActions(actions, inlineBodyGeneratorSocket, mark);
            feedbackActionCollector.removeActions(processedActions);
        }
    }

    protected List<FeedbackActionProcessor> getFeedbackProcessors(IModule module) {
        List<FeedbackActionProcessor> processors = Lists.newArrayList();

        processors.addAll(getDefaultProcessors());
        processors.addAll(getProcessorModules(module));

        return processors;
    }

    private List<FeedbackActionProcessor> getDefaultProcessors() {
        List<FeedbackActionProcessor> processors = Lists.newArrayList();
        processors.add(soundProcessor);

        return processors;
    }

    private void findAndAddFeedbackActionProcessors(Collection<IModule> children, Collection<FeedbackActionProcessor> processorsListAddTo) {
        if (children != null) {
            for (IModule child : children) {
                if (child instanceof FeedbackActionProcessor) {
                    processorsListAddTo.add((FeedbackActionProcessor) child);
                }
            }
        }
    }

    protected List<FeedbackActionProcessor> getProcessorModules(IModule module) {
        List<FeedbackActionProcessor> processors = Lists.newArrayList();
        if (module != null) {
            try {
                IModule parentModule = module.getParentModule();
                if (parentModule != null) {
                    findAndAddFeedbackActionProcessors(parentModule.getChildren(), processors);
                }
            } catch (Exception exception) {
                Logger.getLogger(getClass().getName()).info(exception.getMessage());
            }
        }
        return processors;
    }

    private FeedbackProperties getPropertiesForModule(IModule module) {
        return propertiesCollector.collect(module, feedbackActionCollector.getSource());
    }
}
