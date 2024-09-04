package Dictionary;

import java.security.InvalidKeyException;
import java.util.TreeMap;

public class Dictionary{
    private TreeMap<String, String> entries;

    public Dictionary() {
        entries = new TreeMap<String, String>();
    }

    public void addEntry(String word, String meaning) {
        entries.put(word, meaning);
    }

    public void removeEntry(String word)  throws InvalidKeyException {
        if (entries.containsKey(word))
            entries.remove(word);
        else throw new InvalidKeyException();
    }

    public void updateEntry(String word, String newMeaning) throws InvalidKeyException {
        if (entries.containsKey(word)) {
            entries.put(word, newMeaning);
        }
        else throw new InvalidKeyException();
    }


    public String search(String word) {
        return entries.get(word);
    }

    public TreeMap<String, String> getAllEntries() {
        return entries;
    }
}