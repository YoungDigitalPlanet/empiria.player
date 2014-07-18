package eu.ydp.empiria.player.client.module.texteditor.wrapper;

public class TextEditorJSWrapper {

	public native final void convert(String moduleId) /*-{
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

	public native final void setContent(String moduleId, String text) /*-{
        $wnd.$("#" + moduleId + " textarea").jqteVal(text);
    }-*/;

	public native final String getContent(String moduleId) /*-{
        return $wnd.$("#" + moduleId + " textarea").val();
    }-*/;
}
