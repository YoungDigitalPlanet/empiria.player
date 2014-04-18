package eu.ydp.empiria.player.client.module.dictionary.external.model;


public class Entry {

	private final String ang;
	private final String pol;
	private final String post;
	private final String desc;
	private final String angSound;
	private final String descrSound;

	Entry(String ang, String pol, String post, String desc, String angSound,
			String descrSound) {
		this.ang = ang;
		this.pol = pol;
		this.post = post;
		this.desc = desc;
		this.angSound = angSound;
		this.descrSound = descrSound;
	}

	public String getAng() {
		return ang;
	}

	public String getPol() {
		return pol;
	}

	public String getPost() {
		return post;
	}

	public String getDesc() {
		return desc;
	}

	public String getAngSound() {
		return angSound;
	}

	public String getDescrSound() {
		return descrSound;
	}

}
