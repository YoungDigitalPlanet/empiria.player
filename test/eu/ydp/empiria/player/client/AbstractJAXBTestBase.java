package eu.ydp.empiria.player.client;

public abstract class AbstractJAXBTestBase<T> extends TestJAXBParser<T> {

	@SuppressWarnings("unchecked")
	public final T createBeanFromXMLString(String xmlString) {
		return parse(xmlString);
	}

}
