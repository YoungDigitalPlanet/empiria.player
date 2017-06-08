/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller.feedback;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.base.ISingleViewWithBodyModule;
import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;

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
    private String lastChange;

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
        properties.addStringProperty(FeedbackPropertyName.LAST_CHANGE, lastChange);
    }

    private void setProperties(FeedbackProperties properties, IModule module) {
        if (module == sender) {
            isWrong = properties.getBooleanProperty(FeedbackPropertyName.WRONG);
            isOk = properties.getBooleanProperty(FeedbackPropertyName.OK);
        }

        todo += (double) properties.getIntegerProperty(FeedbackPropertyName.TODO);
        done += (double) properties.getIntegerProperty(FeedbackPropertyName.DONE);
        lastChange = properties.getStringProperty(FeedbackPropertyName.LAST_CHANGE);
    }

    public void setVariables(Map<String, ? extends Variable> variables) {
        this.variables = variables;
    }

}
