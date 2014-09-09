package eu.ydp.empiria.player.client.module.texteditor.wrapper;

public class TextEditorJSWrapper {

	public void convert(String moduleId) {
		convertNative(moduleId);
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

	private native final void convertNative(String moduleId) /*-{
        var options = {
            link: false,
            indent: false,
            outdent: false,
            unlink: false,
            remove: false,
            source: false,
            rule: false
        };
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