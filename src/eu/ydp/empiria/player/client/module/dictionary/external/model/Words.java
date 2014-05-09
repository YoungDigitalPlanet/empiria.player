package eu.ydp.empiria.player.client.module.dictionary.external.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Words {
    private final Map<String, List<String>> wordsByLetter;
    private final Map<String, Integer> baseIndexes;

    public Words(Map<String, List<String>> wordsByLetter, Map<String, Integer> baseIndexes) {
        this.wordsByLetter = wordsByLetter;
        this.baseIndexes = baseIndexes;
    }

    public Map<String, Integer> getBaseIndexes() {
        return baseIndexes;
    }

    public Set<String> getFirstLetters(){
        return wordsByLetter.keySet();
    }

    public List<String> getWordsByLetter(String firstLetter){
        return wordsByLetter.get(firstLetter);
    }
}
