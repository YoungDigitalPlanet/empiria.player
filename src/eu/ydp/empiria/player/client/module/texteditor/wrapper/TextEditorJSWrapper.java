package eu.ydp.empiria.player.client.module.texteditor.wrapper;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;

public class TextEditorJSWrapper {

	@Inject
	private TextEditorOptions options;

	public void convert(String moduleId) {
		convertNative(moduleId, options.getOptions());
	}

	public void setContent(String moduleId, String text) {
		setContentNative(moduleId, text);
	}

	public String getContent(String moduleId) {
		return getContentNative(moduleId);
	}

	public void lock(String moduleId) {
		lockNative(moduleId);
	}

	public void unlock(String moduleId) {
		unlockNative(moduleId);
	}

	private native final void convertNative(String moduleId, JavaScriptObject options) /*-{
        $wnd.$("#" + moduleId + " textarea").jqte(options);
    }-*/;

	private native final void setContentNative(String moduleId, String text) /*-{
        $wnd.$("#" + moduleId + " textarea").jqteVal(text);
    }-*/;

	private native final String getContentNative(String moduleId) /*-{
        return $wnd.$("#" + moduleId + " textarea").val();
    }-*/;

	private native final void lockNative(String moduleId) /*-{
        $wnd.$('#' + moduleId + ' .jqte_editor').attr('contenteditable', 'false');
    }-*/;

	private native final void unlockNative(String moduleId) /*-{
        $wnd.$('#' + moduleId + ' .jqte_editor').attr('contenteditable', 'true');
    }-*/;
}