package eu.ydp.empiria.player.client.util.geom;

import eu.ydp.empiria.player.client.module.view.HasDimensions;

public class Size implements HasDimensions {

	private int width;
	private int height;

	public Size(){
		width = 0;
		height = 0;
	}

	public Size(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Size)) {
			return false;
		}
		Size other = (Size) obj;
		if (height != other.height) {
			return false;
		}
		if (width != other.width) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Size [width=" + width + ", height=" + height + "]";
	}
	
	
}
