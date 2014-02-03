package eu.ydp.empiria.player.client.module.colorfill.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "buttons")
public class ButtonsContainer {

	@XmlElement(name = "button")
	private List<ColorButton> buttons;

	@XmlElement(name = "eraserButton")
	private EraserButton eraserButton;

	public List<ColorButton> getButtons() {
		return buttons;
	}

	public void setButtons(List<ColorButton> buttons) {
		this.buttons = buttons;
	}

	public EraserButton getEraserButton() {
		return eraserButton;
	}

	public void setEraserButton(EraserButton eraserButton) {
		this.eraserButton = eraserButton;
	}

}
