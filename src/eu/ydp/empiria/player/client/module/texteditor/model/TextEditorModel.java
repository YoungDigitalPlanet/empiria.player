package eu.ydp.empiria.player.client.module.texteditor.model;

public class TextEditorModel {

	private final String content;

	public TextEditorModel(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public static TextEditorModel createEmpty() {
		return new TextEditorModel("");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TextEditorModel textEditorModel = (TextEditorModel) o;

		if (!content.equals(textEditorModel.content)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return content.hashCode();
	}
}
