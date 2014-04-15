package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Panel;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.MainView;

public class MainController implements PasswordsLoadingListener, ExplanationListener {

	@Inject
	private PasswordsController passwordsController;
	@Inject
	private EntriesController entriesController;
	@Inject
	private MainView mainView;

	private Panel root;

	public void init(Panel root) {
		this.root = root;
		passwordsController.load();
	}

	@Override
	public void onPasswordsLoaded() {
		mainView.init(getSelectedPassword());
		root.add(mainView);
	}

	@Override
	public void onEntryLoaded(Entry entry, boolean playSound) {
		mainView.showExplanation(entry, playSound);
	}

	@Override
	public void onBackClick() {
		mainView.hideExplanation();
	}

	private String getSelectedPassword() {
		String pwd = getSelectedPasswordNative();
		if (pwd != null) {
			pwd = URL.decode(pwd);
		}
		return pwd;
	}

	private native String getSelectedPasswordNative()/*-{
														if (typeof $wnd.empiriaDictionaryInternalGetPassword == 'function'){
														return $wnd.empiriaDictionaryInternalGetPassword();
														}
														return "";
														}-*/;

}