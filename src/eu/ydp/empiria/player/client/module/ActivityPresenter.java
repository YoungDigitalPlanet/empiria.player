package eu.ydp.empiria.player.client.module;

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
	 * Pokazywanie poprawnych odpowiedzi
	 */	
	void showCorrectAnswers();
	
	/**
	 * Pokazywanie odpowiedzi zaznaczonych 
	 * przez użytkownika
	 */
	void showCurrentAnswers();
	
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
