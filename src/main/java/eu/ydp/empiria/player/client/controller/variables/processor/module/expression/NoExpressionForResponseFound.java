package eu.ydp.empiria.player.client.controller.variables.processor.module.expression;

public class NoExpressionForResponseFound extends RuntimeException {

    private static final long serialVersionUID = -8436088428201923482L;

    public NoExpressionForResponseFound(String errorMessage) {
        super(errorMessage);
    }

}
