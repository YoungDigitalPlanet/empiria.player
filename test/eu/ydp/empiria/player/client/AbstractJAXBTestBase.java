package eu.ydp.empiria.player.client;

import java.lang.reflect.ParameterizedType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.apache.tools.ant.filters.StringInputStream;
import org.junit.Before;

public abstract class AbstractJAXBTestBase<T>{

	private Unmarshaller unmarshaller;

	@Before
	public void createUnmarshaller() {
		if (unmarshaller == null) {
			unmarshaller = getUnmarshaller();
		}
	}

	@SuppressWarnings("unchecked")
	public final T createBeanFromXMLString(String xmlString){
		try {
			return (T)unmarshaller.unmarshal(new StringInputStream(xmlString));
		} catch (JAXBException e) {
			Assert.fail(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private Class<T> getBeanClass() throws Exception {
        ParameterizedType superclass = (ParameterizedType)getClass().getGenericSuperclass();
        return (Class<T>) superclass.getActualTypeArguments()[0];
	}

	private Unmarshaller getUnmarshaller(){
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
