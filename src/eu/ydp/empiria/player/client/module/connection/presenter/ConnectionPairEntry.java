package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.base.Objects;


public class ConnectionPairEntry<S,T> {
	S source;
	T target;

	public ConnectionPairEntry(S source,T target) {
		super();
		this.source = source;
		this.target = target;
	}

	public ConnectionPairEntry() {
	}

	public S getSource() {
		return source;
	}

	public T getTarget() {
		return target;
	}

	public void setSource(S source) {
		this.source = source;
	}

	public void setTarget(T target) {
		this.target = target;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof ConnectionPairEntry) {
			equals = Objects.equal(target, ((ConnectionPairEntry<?,?>) obj).getTarget())
					|| Objects.equal(source, ((ConnectionPairEntry<?,?>) obj).getTarget());
			if (equals) {
				equals = Objects.equal(target, ((ConnectionPairEntry<?,?>) obj).getSource())
						 || Objects.equal(source, ((ConnectionPairEntry<?,?>) obj).getSource());
			}
		}
		return equals;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(source) + Objects.hashCode(target);
	}
}
