package eu.ydp.empiria.player.client;

import java.lang.reflect.ParameterizedType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.apache.tools.ant.filters.StringInputStream;

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

public abstract class TestJAXBParser<T> implements JAXBParser<T> {

	private Unmarshaller unmarshaller;

	@Override
	public T parse(String xml) {
		ensureUnmarshaller();
		try {
			return (T) unmarshaller.unmarshal(new StringInputStream(xml));
		} catch (JAXBException e) {
			Assert.fail(e.getMessage());
			return null;
		}
	}

	private void ensureUnmarshaller() {
		if (unmarshaller == null) {
			unmarshaller = getUnmarshaller();
		}
	}

	@SuppressWarnings("unchecked")
	private Class<T> getBeanClass() throws Exception {
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) superclass.getActualTypeArguments()[0];
	}

	private Unmarshaller getUnmarshaller() {
		Unmarshaller unmarshaller = null;

		try {
			JAXBContext context = JAXBContext.newInstance(getBeanClass());
			unmarshaller = context.createUnmarshaller();
			return unmarshaller;
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
			return unmarshaller;
		}
	}
}
