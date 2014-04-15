package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.ArrayList;
import java.util.List;

public class PasswordsResult {

	private final List<String> list;
	private final Integer index;

	public PasswordsResult() {
		this.list = new ArrayList<String>();
		this.index = -1;
	}

	public PasswordsResult(List<String> list, Integer index) {
		this.list = list;
		this.index = index;
	}

	public List<String> getList() {
		return list;
	}

	public Integer getIndex() {
		return index;
	}

}