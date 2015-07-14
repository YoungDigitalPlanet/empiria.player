package eu.ydp.empiria.player.client.module;

/**
 * Interface for widgets implementing interactive module functions
 */
public interface IActivity extends ILockable, IResetable {

    /**
     * Mark wrong and mark correct answers
     */
    public void markAnswers(boolean mark);

    /**
     * Show correct answers
     */
    public void showCorrectAnswers(boolean show);
}
