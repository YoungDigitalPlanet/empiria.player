package eu.ydp.empiria.player.client;

import java.lang.annotation.Annotation;

@SuppressWarnings("PMD")
public class BindDescriptor<T> {

	public enum BindType {
		SIMPLE, MOCK, SPY
	}

	private Class<? extends Annotation> in;
	private Class<? extends T> to;
	private Class<T> bind;

	public BindDescriptor(Class<T> bind, Class<? extends T> to, Class<? extends Annotation> in) {
		this.bind = bind;
		this.to = to;
		this.in = in;
	}

	public BindDescriptor() {

	}

	public Class<T> getBind() {
		return bind;
	}

	public Class<? extends Annotation> getIn() {
		return in;
	}

	public Class<? extends T> getTo() {
		return to;
	}

	public BindDescriptor<T> bind(Class<T> bind) {
		this.bind = bind;
		return this;
	}

	public BindDescriptor<T> to(Class<? extends T> to) {
		this.to = to;
		return this;
	}

	public BindDescriptor<T> in(Class<? extends Annotation> in) {
		this.in = in;
		return this;
	}

	public boolean isAllSet() {
		return in != null && to != null && bind != null;
	}
}
