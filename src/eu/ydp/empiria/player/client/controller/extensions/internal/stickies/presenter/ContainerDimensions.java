package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter;

public class ContainerDimensions {

	private int width;
	private int height;
	private int absoluteTop;
	private int absoluteLeft;

	public ContainerDimensions() {
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getAbsoluteTop() {
		return absoluteTop;
	}

	public int getAbsoluteLeft() {
		return absoluteLeft;
	}

	public static class Builder {
		private int width = 0;
		private int height = 0;
		private int absoluteTop = 0;
		private int absoluteLeft = 0;

		public Builder width(int width) {
			this.width = width;
			return this;
		}

		public Builder height(int height) {
			this.height = height;
			return this;
		}

		public Builder absoluteTop(int absoluteTop) {
			this.absoluteTop = absoluteTop;
			return this;
		}

		public Builder absoluteLeft(int absoluteLeft) {
			this.absoluteLeft = absoluteLeft;
			return this;
		}

		public ContainerDimensions build() {
			ContainerDimensions containerDimensions = new ContainerDimensions();
			containerDimensions.width = width;
			containerDimensions.height = height;
			containerDimensions.absoluteTop = absoluteTop;
			containerDimensions.absoluteLeft = absoluteLeft;
			return containerDimensions;
		}

		public Builder() {
		}

		public static Builder fromContainerDimensions(ContainerDimensions dimensions) {
			return new Builder().width(dimensions.getWidth()).height(dimensions.height).absoluteLeft(dimensions.getAbsoluteLeft())
					.absoluteTop(dimensions.getAbsoluteTop());
		}
	}
}
