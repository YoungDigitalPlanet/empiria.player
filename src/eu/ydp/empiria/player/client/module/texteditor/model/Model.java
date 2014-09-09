package eu.ydp.empiria.player.client.module.texteditor.model;

public class Model {

	private final String content;

	public Model(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public static Model createEmpty() {
		return new Model("");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Model model = (Model) o;

		if (!content.equals(model.content)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return content.hashCode();
	}
}
