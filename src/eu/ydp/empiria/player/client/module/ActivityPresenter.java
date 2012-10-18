package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

public interface ActivityPresenter<H> extends IsWidget {

	/**
	 * Wiąże widok z prezenterem, w tym momencie 
	 * prezenter powinien mieć pełną informację 
	 * o strukturze według, której będzie tworzył widoki
	 */
	void bindView();
	/**
	 * Czyści wszystkie powiazania w module
	 */
	void reset();
	
	/**
	 * Blokowanie widoku
	 * @param locked
	 */
	void setLocked(boolean locked);
	
	/**
	 * Ustawianie przekazanych odpowiedzi
	 * @param answers lista odpowiedzi które mają byc 
	 * ustawione
	 */
	void showAnswers(List<H> answers);
	
	/**
	 * Zaznaczanie poprawnych odpowiedzi
	 */
	void markCorrectAnswers();
	
	/**
	 * Zaznaczanie błędnych odpowiedzi
	 */
	void markWrongAnswers();
	
	/**
	 * Odznaczanie poprawnych odpowiedzi
	 */
	void unmarkCorrectAnswers();
	
	/**
	 * Odznaczanie błędnych odpowiedzi
	 */
	void unmarkWrongAnswers();

}
