package eu.ydp.empiria.player.client.module;

/**
 * Interface for widgets implementing interactive module functions
 */
public interface IActivity {

	/** Reset module (as if it was created once again) */
	public void reset();

	/** Mark wrong and mark correct answers */
	public void markAnswers(boolean mark);
	
	/** Show correct answers */
	public void showCorrectAnswers(boolean show);
	
	/** lock activity */
	public void lock(boolean l);
}
