package eu.ydp.empiria.player.client.module.tutor;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

import java.util.Collection;

import com.google.common.base.Function;

public enum ActionType {

	DEFAULT, ON_PAGE_ALL_OK, ON_OK, ON_WRONG;

	private static final Function<ActionType, String> function = new Function<ActionType, String>() {

		@Override
		public String apply(ActionType type) {
			return type.toString();
		}
	};

	private static Collection<String> cachedValuesString = createValuesString();

	public static Collection<String> valuesString() {
		return cachedValuesString;
	}

	private static Collection<String> createValuesString() {
		Collection<ActionType> types = asList(values());
		return newArrayList(transform(types, function));
	}

}
