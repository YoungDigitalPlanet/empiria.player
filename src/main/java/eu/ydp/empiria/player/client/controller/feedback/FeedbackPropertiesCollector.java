package eu.ydp.empiria.player.client.controller.feedback;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ISingleViewWithBodyModule;
import eu.ydp.empiria.player.client.module.IUniqueModule;

import java.util.Map;

public class FeedbackPropertiesCollector {

    private Map<String, ? extends Variable> variables;

    @Inject
    private FeedbackPropertiesCreator propertiesCreator;

    private IModule sender;

    private double todo;

    private double done;

    private boolean isWrong;

    private boolean isOk;

    public FeedbackProperties collect(IModule module, IModule sender) {
        this.sender = sender;
        this.todo = 0.0;
        this.done = 0.0;

        FeedbackProperties properties = new FeedbackProperties();
        mergeProperties(module, properties);
        updateProperties(properties);

        return properties;
    }

    private void mergeProperties(IModule module, FeedbackProperties properties) {
        if (module instanceof IUniqueModule) {
            FeedbackProperties moduleProperties = getPropertiesForUniqueModule(module);
            properties.merge(moduleProperties);
            setProperties(moduleProperties, module);
        } else if (module instanceof ISingleViewWithBodyModule) {
            ISingleViewWithBodyModule container = (ISingleViewWithBodyModule) module;

            for (IModule child : container.getChildren()) {
                mergeProperties(child, properties);
            }
        }
    }

    private FeedbackProperties getPropertiesForUniqueModule(IModule module) {
        String identifier = ((IUniqueModule) module).getIdentifier();
        return propertiesCreator.getPropertiesFromVariables(identifier, variables);
    }

    private void updateProperties(FeedbackProperties properties) {
        double result = 0.0;

        if (todo != 0.0) {
            result = Math.round(100.0 * (done / todo));
        }

        properties.addBooleanProperty(FeedbackPropertyName.WRONG, isWrong);
        properties.addBooleanProperty(FeedbackPropertyName.OK, isOk);
        properties.addDoubleProperty(FeedbackPropertyName.RESULT, result);
    }

    private void setProperties(FeedbackProperties properties, IModule module) {
        if (module == sender) {
            isWrong = properties.getBooleanProperty(FeedbackPropertyName.WRONG);
            isOk = properties.getBooleanProperty(FeedbackPropertyName.OK);
        }

        todo += (double) properties.getIntegerProperty(FeedbackPropertyName.TODO);
        done += (double) properties.getIntegerProperty(FeedbackPropertyName.DONE);
    }

    public void setVariables(Map<String, ? extends Variable> variables) {
        this.variables = variables;
    }

}
