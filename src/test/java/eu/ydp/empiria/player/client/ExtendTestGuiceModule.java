package eu.ydp.empiria.player.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;

import eu.ydp.empiria.player.client.BindDescriptor.BindType;
import eu.ydp.gwtutil.test.AbstractTestModule;

public abstract class ExtendTestGuiceModule extends AbstractTestModule {
	protected final Set<BindDescriptor<?>> bindDescriptors = new HashSet<BindDescriptor<?>>();

	public ExtendTestGuiceModule(Class<?>... classToOmit) {
		super(classToOmit);
	}

	public ExtendTestGuiceModule(Iterable<Class<?>> classToOmit) {
		super(classToOmit);
	}

	protected Provider<?> getProvider(final Class<?> clazz, final BindType bindType) {
		Provider<?> provider = new Provider<Object>() {
			@Override
			public Object get() {
				try {
					Object instance = null;
					if (bindType == BindType.SPY) {
						instance = spy(clazz.newInstance());
					} else if (bindType == BindType.MOCK) {
						instance = mock(clazz);
					}
					return instance;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		return provider;
	}

	/**
	 * binduje klasy do mockow
	 * 
	 * @param bindDescriptor
	 */
	protected void bindToMock(BindDescriptor bindDescriptor) {
		if (bindDescriptor.getIn() != null && bindDescriptor.getIn().isAssignableFrom(Singleton.class)) {// SINGLETON
			if (bindDescriptor.isAllSet()) {// wszystkie pola ustawione
				bind(bindDescriptor.getBind()).toInstance(mock(bindDescriptor.getTo()));
			} else {
				bind(bindDescriptor.getBind()).toInstance(mock(bindDescriptor.getBind()));
			}
		} else {
			if (bindDescriptor.getTo() == null) {
				bind(bindDescriptor.getBind()).toProvider(getProvider(bindDescriptor.getBind(), BindType.MOCK));
			} else {
				bind(bindDescriptor.getBind()).toProvider(getProvider(bindDescriptor.getTo(), BindType.MOCK));
			}
		}
	}

	/**
	 * binduje klasy do spy
	 * 
	 * @param bindDescriptor
	 */
	protected void bindToSpy(BindDescriptor bindDescriptor) {
		if (bindDescriptor.getIn() != null && bindDescriptor.getIn().isAssignableFrom(Singleton.class)) {// SINGLETON
			try {
				if (bindDescriptor.isAllSet()) {// wszystkie pola ustawione
					bind(bindDescriptor.getBind()).toInstance(spy(bindDescriptor.getTo().newInstance()));
				} else {
					bind(bindDescriptor.getBind()).toInstance(spy(bindDescriptor.getBind().newInstance()));
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		} else {
			if (bindDescriptor.getTo() == null) {
				bind(bindDescriptor.getBind()).toProvider(getProvider(bindDescriptor.getBind(), BindType.SPY));
			} else {
				bind(bindDescriptor.getBind()).toProvider(getProvider(bindDescriptor.getTo(), BindType.SPY));
			}
		}
	}

	// FIXME dodac providery jak beda potrzebne fabryki dla mockow
	protected void bind(BindDescriptor bindDescriptor, BindType bindType) {
		switch (bindType) {
		case SIMPLE:
			AnnotatedBindingBuilder<?> builder = null;
			if (bindDescriptor.getBind() != null) {
				builder = bind(bindDescriptor.getBind());
				if (bindDescriptor.getTo() != null) {
					builder.to(bindDescriptor.getTo());
				}
				if (bindDescriptor.getIn() != null) {
					builder.in(bindDescriptor.getIn());
				}
			}
			break;
		case MOCK:
			bindToMock(bindDescriptor);
			break;
		case SPY:
			bindToSpy(bindDescriptor);
			break;
		}
	}
}
