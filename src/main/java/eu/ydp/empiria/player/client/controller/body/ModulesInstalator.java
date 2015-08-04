package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.components.ModulePlaceholder;
import eu.ydp.empiria.player.client.controller.body.parenthood.ParenthoodGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.gwtutil.client.collections.StackMap;

import java.util.ArrayList;
import java.util.List;

public class ModulesInstalator implements ModulesInstalatorSocket {

    private ModulesRegistrySocket registry;
    private ModuleSocket moduleSocket;
    private InteractionEventsListener interactionListener;
    private ParenthoodGeneratorSocket parenthood;
    private List<IModule> singleViewModules;

    private StackMap<String, StackMap<Element, HasWidgets>> uniqueModulesMap = new StackMap<>();
    private StackMap<Element, StackMap<IModule, HasWidgets>> nonuniqueModulesMap = new StackMap<>();
    private StackMap<String, IModule> multiViewModulesMap = new StackMap<>();

    private FeedbackRegistry feedbackRegistry;

    @Inject
    public ModulesInstalator(@Assisted ParenthoodGeneratorSocket pts, @Assisted ModulesRegistrySocket reg,
                             @Assisted ModuleSocket ms, @Assisted InteractionEventsListener mil, FeedbackRegistry feedbackRegistry) {
        this.registry = reg;
        this.moduleSocket = ms;
        this.interactionListener = mil;
        this.parenthood = pts;
        this.feedbackRegistry = feedbackRegistry;
        singleViewModules = new ArrayList<>();
    }

    @Override
    public boolean isModuleSupported(String nodeName) {
        return registry.isModuleSupported(nodeName);
    }

    @Override
    public boolean isMultiViewModule(String nodeName) {
        return registry.isMultiViewModule(nodeName);
    }

    @Override
    public void registerModuleView(Element element, HasWidgets parent) {
        String responseIdentifier = element.getAttribute("responseIdentifier");

        ModulePlaceholder placeholder = new ModulePlaceholder();
        parent.add(placeholder);

        if (responseIdentifier != null) {
            if (!uniqueModulesMap.containsKey(responseIdentifier)) {
                uniqueModulesMap.put(responseIdentifier, new StackMap<Element, HasWidgets>());
            }
            if (!multiViewModulesMap.containsKey(responseIdentifier)) {
                IModule currModule = registry.createModule(element);
                multiViewModulesMap.put(responseIdentifier, currModule);
                parenthood.addChild(currModule);
            }
            uniqueModulesMap.get(responseIdentifier).put(element, placeholder);
        } else {
            if (!nonuniqueModulesMap.containsKey(element)) {
                nonuniqueModulesMap.put(element, new StackMap<IModule, HasWidgets>());
            }

            IModule currModule = registry.createModule(element);
            nonuniqueModulesMap.get(element).put(currModule, placeholder);
            parenthood.addChild(currModule);
        }
    }

    @Override
    public void createSingleViewModule(Element element, HasWidgets parent, BodyGeneratorSocket bodyGeneratorSocket) {
        IModule module = registry.createModule(element);

        parenthood.addChild(module);
        registerModuleFeedbacks(module, element);

        if (module instanceof ISingleViewWithBodyModule) {
            parenthood.pushParent((ISingleViewWithBodyModule) module);
            ((ISingleViewWithBodyModule) module).initModule(element, moduleSocket, bodyGeneratorSocket);
            parenthood.popParent();
        } else if (module instanceof ISingleViewSimpleModule) {
            ((ISingleViewSimpleModule) module).initModule(element, moduleSocket, interactionListener);
        } else if (module instanceof IInlineModule) {
            ((IInlineModule) module).initModule(element, moduleSocket, interactionListener);
        }
        if (((ISingleViewModule) module).getView() instanceof Widget) {
            parent.add(((ISingleViewModule) module).getView());
        }

        singleViewModules.add(module);
    }

    private void registerModuleFeedbacks(IModule module, Element moduleElement) {
        feedbackRegistry.registerFeedbacks(module, moduleElement);
    }

    public void installMultiViewNonuniuqeModules() {
        for (Element currElement : nonuniqueModulesMap.getKeys()) {
            StackMap<IModule, HasWidgets> moduleMap = nonuniqueModulesMap.get(currElement);
            IModule module = moduleMap.getKeys().get(0);

            if (module instanceof IMultiViewModule) {
                IMultiViewModule multiViewModule = (IMultiViewModule) module;
                List<HasWidgets> placeholders = moduleMap.getValues();

                multiViewModule.initModule(moduleSocket, interactionListener);
                multiViewModule.addElement(currElement);
                multiViewModule.installViews(placeholders);
                registerModuleFeedbacks(module, currElement);
            }
        }
    }

    public List<IModule> installMultiViewUniqueModules() {

        List<IModule> modules = new ArrayList<IModule>();

        for (String responseIdentifier : uniqueModulesMap.getKeys()) {
            StackMap<Element, HasWidgets> currModuleMap = uniqueModulesMap.get(responseIdentifier);

            IModule currModule = null;

            for (Element currElement : currModuleMap.getKeys()) {
                if (currModule == null) {
                    currModule = multiViewModulesMap.get(responseIdentifier);
                    if (currModule instanceof IMultiViewModule) {
                        ((IMultiViewModule) currModule).initModule(moduleSocket, interactionListener);
                    }

                    registerModuleFeedbacks(currModule, currElement);
                }
                if (currModule instanceof IMultiViewModule) {
                    ((IMultiViewModule) currModule).addElement(currElement);
                }
            }

            if (currModule instanceof IMultiViewModule) {
                ((IMultiViewModule) currModule).installViews(currModuleMap.getValues());
            }

            if (currModule != null) {
                modules.add(currModule);
            }

        }
        return modules;
    }

    public List<IModule> getInstalledSingleViewModules() {
        return singleViewModules;
    }

    public void setInitialParent(HasChildren parent) {
        parenthood.pushParent(parent);
    }

}
