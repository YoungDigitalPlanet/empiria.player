package eu.ydp.empiria.player.client.module.expression;

public enum ExpressionMode {
    DEFAULT, COMMUTATION;

    @Override
    public String toString() {
        String value = super.toString();
        return value.toLowerCase();
    }

}
