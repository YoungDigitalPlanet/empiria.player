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

package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

public class DtoModuleProcessingResult {

    private GeneralVariables generalVariables;
    private ConstantVariables constantVariables;
    private UserInteractionVariables userInteractionVariables;

    public DtoModuleProcessingResult(GeneralVariables generalVariables, ConstantVariables constantVariables, UserInteractionVariables userInteractionVariables) {
        this.generalVariables = generalVariables;
        this.constantVariables = constantVariables;
        this.userInteractionVariables = userInteractionVariables;
    }

    public static DtoModuleProcessingResult fromDefaultVariables() {
        GeneralVariables generalVariables = new GeneralVariables();
        ConstantVariables constantVariables = new ConstantVariables();
        UserInteractionVariables userInteractionVariables = new UserInteractionVariables();
        return new DtoModuleProcessingResult(generalVariables, constantVariables, userInteractionVariables);
    }

    public GeneralVariables getGeneralVariables() {
        return generalVariables;
    }

    public void setGeneralVariables(GeneralVariables generalVariables) {
        this.generalVariables = generalVariables;
    }

    public ConstantVariables getConstantVariables() {
        return constantVariables;
    }

    public void setConstantVariables(ConstantVariables constantVariables) {
        this.constantVariables = constantVariables;
    }

    public UserInteractionVariables getUserInteractionVariables() {
        return userInteractionVariables;
    }

    public void setUserInteractionVariables(UserInteractionVariables userInteractionVariables) {
        this.userInteractionVariables = userInteractionVariables;
    }
}
