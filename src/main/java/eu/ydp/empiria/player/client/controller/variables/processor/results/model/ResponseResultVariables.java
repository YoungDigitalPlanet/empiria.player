package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

import com.google.common.collect.ComparisonChain;

public class ResponseResultVariables implements ResultVariables {

    private final DtoModuleProcessingResult processingResult;

    public ResponseResultVariables(DtoModuleProcessingResult processingResult) {
        this.processingResult = processingResult;
    }

    @Override
    public int getTodo() {
        return processingResult.getConstantVariables().getTodo();
    }

    @Override
    public int getDone() {
        return processingResult.getGeneralVariables().getDone();
    }

    @Override
    public int getMistakes() {
        return processingResult.getUserInteractionVariables().getMistakes();
    }

    @Override
    public int getErrors() {
        return processingResult.getGeneralVariables().getErrors();
    }

    @Override
    public LastMistaken getLastMistaken() {
        return processingResult.getUserInteractionVariables().getLastmistaken();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getDone();
        result = prime * result + getErrors();
        result = prime * result + ((getLastMistaken() == null) ? 0 : getLastMistaken().hashCode());
        result = prime * result + getMistakes();
        result = prime * result + getTodo();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ResultVariables)) {
            return false;
        }
        ResultVariables other = (ResultVariables) obj;
        if (processingResult == null) {
            return false;
        }
        return ComparisonChain.start().compare(getTodo(), other.getTodo()).compare(getDone(), other.getDone()).compare(getErrors(), other.getErrors())
                .compare(getMistakes(), other.getMistakes()).compare(getLastMistaken(), other.getLastMistaken()).result() == 0;
    }

}
