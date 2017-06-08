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

package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.google.common.base.Objects;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "responseDeclaration")
public class ResponseBean {

    @XmlAttribute
    private String identifier;

    @XmlAttribute
    private Cardinality cardinality;

    @XmlAttribute
    private Evaluate evaluate;

    @XmlAttribute
    private CheckMode checkMode;

    @XmlAttribute
    private CountMode countMode;

    @XmlElement(name = "correctResponse")
    private CorrectResponseBean correctResponse;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Cardinality getCardinality() {
        return cardinality;
    }

    public void setCardinality(Cardinality cardinality) {
        this.cardinality = cardinality;
    }

    public Evaluate getEvaluate() {
        return Objects.firstNonNull(evaluate, Evaluate.DEFAULT);
    }

    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }

    public CheckMode getCheckMode() {
        return Objects.firstNonNull(checkMode, CheckMode.DEFAULT);
    }

    public void setCheckMode(CheckMode checkMode) {
        this.checkMode = checkMode;
    }

    public CorrectResponseBean getCorrectResponse() {
        return correctResponse;
    }

    public void setCorrectResponse(CorrectResponseBean correctResponse) {
        this.correctResponse = correctResponse;
    }

    public CountMode getCountMode() {
        return countMode;
    }

    public void setCountMode(CountMode countMode) {
        this.countMode = countMode;
    }

}
