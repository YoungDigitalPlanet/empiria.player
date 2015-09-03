package eu.ydp.empiria.player.client.controller;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.BodyGenerator;
import eu.ydp.empiria.player.client.controller.body.ModulesInstalator;
import eu.ydp.empiria.player.client.controller.body.parenthood.ParenthoodManager;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.workmode.WorkModeClientType;
import eu.ydp.empiria.player.client.gin.factory.ModulesInstalatorFactory;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.containers.AssessmentBodyModule;
import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.HasParent;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.base.ParenthoodSocket;
import eu.ydp.empiria.player.client.module.core.flow.LifecycleModule;
import eu.ydp.empiria.player.client.module.pageinpage.PageInPageModule;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

import java.util.List;

public class AssessmentBody implements WidgetWorkflowListener {

    private final DisplayContentOptions options;
    private final ModuleSocket moduleSocket;
    private final ModulesRegistrySocket modulesRegistrySocket;
    private Panel pageSlot;
    private final ParenthoodManager parenthood;
    private List<IModule> modules;
    private final PlayerWorkModeService playerWorkModeService;
    private final Provider<AssessmentBodyModule> assessmentBodyModuleProvider;
    private final ModulesInstalatorFactory modulesInstalatorFactory;
    private final EventsBus eventsBus;

    @Inject
    public AssessmentBody(@Assisted DisplayContentOptions options, @Assisted ModuleSocket moduleSocket, @Assisted ModulesRegistrySocket modulesRegistrySocket,
                          PlayerWorkModeService playerWorkModeService, ParenthoodManager parenthood, Provider<AssessmentBodyModule> assessmentBodyModuleProvider,
                          ModulesInstalatorFactory modulesInstalatorFactory, EventsBus eventsBus) {
        this.options = options;
        this.moduleSocket = moduleSocket;
        this.modulesRegistrySocket = modulesRegistrySocket;
        this.playerWorkModeService = playerWorkModeService;

        this.parenthood = parenthood;
        this.assessmentBodyModuleProvider = assessmentBodyModuleProvider;
        this.modulesInstalatorFactory = modulesInstalatorFactory;
        this.eventsBus = eventsBus;
    }

    public Widget init(Element assessmentBodyElement) {

        ModulesInstalator instalator = modulesInstalatorFactory.createModulesInstalator(parenthood, modulesRegistrySocket, moduleSocket);
        BodyGenerator generator = new BodyGenerator(instalator, options);

        AssessmentBodyModule bodyModule = assessmentBodyModuleProvider.get();
        instalator.setInitialParent(bodyModule);
        bodyModule.initModule(assessmentBodyElement, moduleSocket, generator, eventsBus);

        modules = instalator.getInstalledSingleViewModules();

        pageSlot = findPageInPage();

        return bodyModule.getView();
    }

    public Panel getPageSlot() {
        return pageSlot;
    }

    private Panel findPageInPage() {
        Panel pagePanel = null;

        for (IModule module : modules) {
            if (module instanceof PageInPageModule) {
                pagePanel = (Panel) ((PageInPageModule) module).getView();
                break;
            }
        }

        return pagePanel;
    }

    public HasChildren getModuleParent(IModule module) {
        return parenthood.getParent(module);
    }

    public List<IModule> getModuleChildren(IModule parent) {
        return parenthood.getChildren(parent);
    }

    public ParenthoodSocket getParenthoodSocket() {
        return moduleSocket;
    }

    @Override
    public void onLoad() {
        for (IModule currModule : modules) {
            if (currModule instanceof LifecycleModule) {
                ((LifecycleModule) currModule).onBodyLoad();
            }
        }
    }

    @Override
    public void onUnload() {
        for (IModule currModule : modules) {
            if (currModule instanceof LifecycleModule) {
                ((LifecycleModule) currModule).onBodyUnload();
            }
        }
    }

    public void setUp() {
        for (IModule currModule : modules) {
            if (currModule instanceof LifecycleModule) {
                ((LifecycleModule) currModule).onSetUp();
            }
            workModeProceeding(currModule);
        }
    }

    private void workModeProceeding(IModule currModule) {
        if (currModule instanceof WorkModeClientType) {
            playerWorkModeService.registerModule((WorkModeClientType) currModule);
        }
    }

    public void start() {
        for (IModule currModule : modules) {
            if (currModule instanceof LifecycleModule) {
                ((LifecycleModule) currModule).onStart();
            }
        }
    }

    public void close() {
        for (IModule currModule : modules) {
            if (currModule instanceof LifecycleModule) {
                ((LifecycleModule) currModule).onClose();
            }
        }
    }

    public ParenthoodManager getParenthood() {
        return parenthood;
    }

    public List<HasParent> getNestedChildren(HasChildren parent) {
        return parenthood.getNestedChildren(parent);
    }

    public List<HasChildren> getNestedParents(HasParent child) {
        return parenthood.getNestedParents(child);
    }
}
