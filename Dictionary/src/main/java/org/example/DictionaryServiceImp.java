package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DictionaryServiceImp implements DictionaryService {

    private List<String> listDictionary = new ArrayList<>();

    /**
     * A class loader for load the Dictionary file (EnglishWords in this case)
     * @return String list with the dictionary content
     */
    @Override
    public List<String> getDictionary() {

        if (!listDictionary.isEmpty()) {
            return listDictionary;
        }

        ClassLoader loader = this.getClass().getClassLoader();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                loader.getResourceAsStream("EnglishWords")))) {

            String line = reader.readLine();
            while (line != null) {
                listDictionary.add(line);
                line = reader.readLine(); // read next line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listDictionary;
    }

    /**
     * Validate if given word exists in the dictionary (EnglishWords in this case) into the isEnglishWord function
     *
     * @param word any given string
     * @return stream based if the word was found in the dictionary
     */
    @Override
    public boolean isEnglishWord(String word) {
        return listDictionary.stream()
                .parallel()
                .anyMatch(x -> x.equals(word.toLowerCase()));
    }
}
