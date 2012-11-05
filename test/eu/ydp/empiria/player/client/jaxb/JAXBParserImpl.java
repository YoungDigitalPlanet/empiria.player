package eu.ydp.empiria.player.client.jaxb;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Assert;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

public class JAXBParserImpl<T> implements JAXBParser<T> {
	private final Class<?> clazz;

	public JAXBParserImpl(Class<? extends JAXBParserFactory<?>> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T parse(String xml) {
		try {
			JAXBBindings bindings = clazz.getAnnotation(JAXBBindings.class);
			ArrayList<Class<?>> binds = new ArrayList<Class<?>>();
			binds.add(bindings.value());

			for (Class<?> c : bindings.objects()) {
				binds.add(c);
			}

			final JAXBContext context = JAXBContext.newInstance(binds.toArray(new Class<?>[0])); // NOPMD
			return (T) context.createUnmarshaller().unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			 Assert.fail(e.getMessage());
			return null;
		}
	}
}
