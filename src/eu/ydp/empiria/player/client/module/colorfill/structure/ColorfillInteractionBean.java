package eu.ydp.empiria.player.client.module.colorfill.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.structure.ModuleBean;

@XmlRootElement(name = "colorfillInteraction")
@XmlAccessorType(XmlAccessType.NONE)
public class ColorfillInteractionBean extends ModuleBean {
	
	@XmlElement(name = "buttons")
	private ButtonsContainer buttons;

	@XmlElement(name = "areas")
	private AreaContainer areas;

	@XmlElement(name = "image")
	private Image image;

	@XmlElement(name = "correctImage")
	private Image correctImage;
	
	@XmlElement(name = "fakeAreas")
	private FakeAreas fakeAreas;
	
	public AreaContainer getAreas() {
		return areas;
	}

	public void setAreas(AreaContainer areas) {
		this.areas = areas;
	}

	public ButtonsContainer getButtons() {
		return buttons;
	}

	public void setButtons(ButtonsContainer buttons) {
		this.buttons = buttons;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getCorrectImage() {
		return correctImage;
	}

	public void setCorrectImage(Image correctImage) {
		this.correctImage = correctImage;
	}

	public FakeAreas getFakeAreas() {
		if(fakeAreas == null) {
			fakeAreas = new FakeAreas();
		}
		return fakeAreas;
	}

	public void setFakeAreas(FakeAreas fakeAreas) {
		this.fakeAreas = fakeAreas;
	}	
}
