package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.common.collect.Lists;

import java.util.List;

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
