package eu.ydp.empiria.player.client.module;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.ModuleBean;

/**
 * Interfejs prezentera
 * @param <H> typ odpowiedzi
 * @param <T> typ beana przyjmowanego do utworzenia struktury widoku
 */
public interface ActivityPresenter<H extends AbstractResponseModel<?>, T extends ModuleBean> extends IsWidget {

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
	 * Ustawia response model
	 * @param model
	 */
	void setModel(H model);
	
	/**
	 * Ustawia beana reprezentującego strukturę widoku
	 * @param bean 
	 */
	void setBean(T bean);
	
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
