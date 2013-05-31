package eu.ydp.empiria.player.client.module.colorfill.model;

public class ColorModel {
	
	public static ColorModel createFromRgbString(String rgb) {
		ColorModel cm = new ColorModel();
		cm.red = Integer.parseInt(rgb.substring(0,2), 16);
		cm.green = Integer.parseInt(rgb.substring(2, 4), 16);
		cm.blue = Integer.parseInt(rgb.substring(4,6), 16);
		cm.alpha = 255;
		return cm;
	}
	
	public static ColorModel createFromRgba(int red, int green, int blue, int alpha) {
		ColorModel cm = new ColorModel();
		cm.red = red;
		cm.green = green;
		cm.blue = blue;
		cm.alpha = alpha;
		return cm;
	}
	
	public static ColorModel createEraser() {
		return new ColorModel();
	}

	private int red;
	private int green;
	private int blue;
	private int alpha;
	
	private ColorModel(){}

	public boolean isTransparent(){
		return alpha < 255;
	}
	
	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}

	public int getAlpha() {
		return alpha;
	}
	
	public String toStringRgb() {
		return toHex(red) + toHex(green) + toHex(blue);
	}
	
	public String toStringRgba() {
		return toHex(red) + toHex(green) + toHex(blue) + toHex(alpha);
	}
	
	private String toHex(int n) {
		String hex = Integer.toHexString(n);
		return (hex.length() > 1) ? hex : "0" + hex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + alpha;
		result = prime * result + blue;
		result = prime * result + green;
		result = prime * result + red;
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		ColorModel other = (ColorModel) obj;
		if (alpha != other.alpha) {
			return false;
		}
		if (blue != other.blue) {
			return false;
		}
		if (green != other.green) {
			return false;
		}
		if (red != other.red) {
			return false;
		}
		return true;
	}
	
}
