package eu.ydp.empiria.player.client.module.tutor;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackMediator;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackTutorClient;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenter;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TutorModule extends SimpleModuleBase implements PowerFeedbackTutorClient {

    @Inject
    private EventsBus eventsBus;
    @Inject
    private PageScopeFactory eventScopeFactory;
    @Inject
    @ModuleScoped
    private TutorPresenter presenter;
    @Inject
    @ModuleScoped
    private TutorView view;
    @Inject
    @ModuleScoped
    private ActionEventGenerator eventGenerator;
    @Inject
    @ModuleScoped
    private TutorConfig config;
    @Inject
    @PageScoped
    private PowerFeedbackMediator mediator;

    private final TutorEventHandler tutorEventHandler = new TutorEventHandler() {

        @Override
        public void onTutorChanged(TutorEvent event) {
            eventGenerator.tutorChanged(event.getPersonaIndex());
        }
    };

    @Override
    protected void initModule(Element element) {
        addHandlers();
        init();
    }

    private void init() {
        mediator.registerTutor(this);
        presenter.init();
    }

    private void addHandlers() {
        final CurrentPageScope modulePageScope = eventScopeFactory.getCurrentPageScope();
        eventsBus.addHandler(TutorEvent.getType(TutorEventTypes.TUTOR_CHANGED), tutorEventHandler, modulePageScope);
    }

    @Override
    public Widget getView() {
        return view.asWidget();
    }

    @Override
    public void resetPowerFeedback() {
        eventGenerator.executeDefaultAction();
    }

    @Override
    public void terminatePowerFeedback() {
        eventGenerator.stop();
    }

    @Override
    public void processUserInteraction(EndHandler endHandler) {
        eventGenerator.stateChanged(endHandler);
    }

    @Override
    public void initPowerFeedbackClient() {
        eventGenerator.start();
    }
}
