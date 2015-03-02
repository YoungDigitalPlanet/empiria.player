package eu.ydp.empiria.player.client;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Sets;

public class GuiceModuleConfiguration {
	private final List<Class<?>> classToOmmit = Lists.newArrayList();
	private final List<Class<?>> classToMock = Lists.newArrayList();
	private final List<Class<?>> classToSpy = Lists.newArrayList();
	private final Set<Class<?>> classWithDisabledPostConstruct = Sets.newHashSet();

	public void addClassToOmmit(Class<?> clazz) {
		classToOmmit.add(clazz);
	}

	public void addClassToMock(Class<?> clazz) {
		classToMock.add(clazz);
	}

	public void addClassToSpy(Class<?> clazz) {
		classToSpy.add(clazz);
	}

	public void addAllClassToOmit(Class<?>... clazz) {
		classToOmmit.addAll(Arrays.asList(clazz));
	}

	public void addAllClassToMock(Class<?>... clazz) {
		classToMock.addAll(Arrays.asList(clazz));
	}

	public void addAllClassToSpy(Class<?>... clazz) {
		classToSpy.addAll(Arrays.asList(clazz));
	}

	public void addClassWithDisabledPostConstruct(Class<?> clazz) {
		classWithDisabledPostConstruct.add(clazz);
	}

	public List<Class<?>> getClassToMock() {
		return classToMock;
	}

	public List<Class<?>> getClassToOmmit() {
		return classToOmmit;
	}

	public List<Class<?>> getClassToSpy() {
		return classToSpy;
	}

	public Set<Class<?>> getClassWithDisabledPostConstruct() {
		return classWithDisabledPostConstruct;
	}

}
