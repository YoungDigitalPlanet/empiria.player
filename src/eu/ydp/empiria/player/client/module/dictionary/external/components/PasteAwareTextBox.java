package eu.ydp.empiria.player.client.module.dictionary.external.components;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.TextBox;

public class PasteAwareTextBox extends TextBox {

	private PasteListener pasteListener;

	public PasteAwareTextBox() {
		super();
		registerNativePasteEvent(this.getElement());
	}

	private void onPaste() {
		pasteListener.onPaste();
	}

	public void addPasteListener(PasteListener pasteListener) {
		this.pasteListener = pasteListener;
	}

	public interface PasteListener {
		void onPaste();
	}

	private native void registerNativePasteEvent(Element element)/*-{
																	var instance = this;
																	element.addEventListener ("input", function(){
																	instance.@eu.ydp.empiria.player.client.module.dictionary.external.components.PasteAwareTextBox::onPaste()();
																	}, false);
																	}-*/;
}
