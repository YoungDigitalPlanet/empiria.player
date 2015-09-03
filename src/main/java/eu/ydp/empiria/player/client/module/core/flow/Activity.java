package eu.ydp.empiria.player.client.module.core.flow;

/**
 * Interface for widgets implementing interactive module functions
 */
public interface Activity extends Lockable, Resetable {

    /**
     * Mark wrong and mark correct answers
     */
    public void markAnswers(boolean mark);

    /**
     * Show correct answers
     */
    public void showCorrectAnswers(boolean show);
}
