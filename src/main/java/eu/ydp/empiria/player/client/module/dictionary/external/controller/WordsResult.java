package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.List;

import com.google.common.collect.Lists;

public class WordsResult {

	private final List<String> list;
	private final Integer index;

	public WordsResult() {
		this.list = Lists.newArrayList();
		this.index = -1;
	}

	public WordsResult(List<String> list, Integer index) {
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