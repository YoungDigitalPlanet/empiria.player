package eu.ydp.empiria.player.client.module.ordering.presenter;

public class CannotMatchOrderingItemsToUserAnswersException extends RuntimeException {

    private static final long serialVersionUID = 7251798479949681010L;

    public CannotMatchOrderingItemsToUserAnswersException(String errorMessage) {
        super(errorMessage);
    }
}
