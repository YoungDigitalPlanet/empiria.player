package eu.ydp.empiria.player.client.module.dictionary.external.controller.search;

import java.util.Comparator;

public class StringIgnoreCaseComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return o1.compareToIgnoreCase(o2);
    }

}
