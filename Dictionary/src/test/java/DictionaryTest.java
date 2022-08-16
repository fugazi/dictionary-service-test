
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import lombok.extern.slf4j.Slf4j;
import org.example.Dictionary;
import org.example.DictionaryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
// Annotation to load the mockito JUnit 5 extension
@ExtendWith(MockitoExtension.class)
public class DictionaryTest {

    public static final String VALID_ENGLISH_WORD = "WORKING";
    public static final String INVALID_ENGLISH_WORD = "AMIGO";
    public static final String POSSIBLE_ENGLISH_WORD = "NO";

    private Dictionary dictionary;
    private DictionaryService dictService;
    List<String> dictionaryList;

    /**
     * Mocking the DictionaryService class to get the dictionary list
     */
    @BeforeEach
    public void init() {
        dictionary = new Dictionary();
        dictService = mock(DictionaryService.class);
        dictionary.setDictionaryService(dictService);
        // I'm adding a mocked dictionary here
        when(dictService.getDictionary()).thenReturn(createDictionaryArray());
        dictionaryList = dictService.getDictionary();
    }

    /**
     * Create String list based on the Dictionary file (EnglishWords in this case) to mock the dictionary service
     * @return String list with the dictionary content
     */
    static List<String> createDictionaryArray() {
        List<String> listDictionary = new ArrayList<>();
        ClassLoader loader = DictionaryTest.class.getClassLoader();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                loader.getResourceAsStream(    "EnglishWords"))))
        {
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
     * Validate if given word exists in the dictionary (EnglishWords in this case) to mock the isEnglishWord function
     * @param word exists in the dictionary
     * @return boolean based if the word was found in the dictionary
     */
    public boolean isThisEnglish(String word) {
        for (String w : dictionaryList) {
            if (w.equals(word.toLowerCase())) {
                log.info(word + " is a valid English word");
                return true;
            }
        }
        log.info(word + " is not a valid English word");
        return false;
    }

    /**
     * Test the dictionary service with the mocked dictionary list
     */
    @Test
    void testDictionaryService() {
        assertSoftly(softly -> {
            softly.assertThat(dictionaryList.size()).isNotNull();
        });
    }

    /**
     * Test the dictionary service with the non-English word: AMIGO
     */
    @Test
    void testValidateNonEnglishWord() {
        when(dictService.isEnglishWord(INVALID_ENGLISH_WORD)).thenReturn(isThisEnglish(INVALID_ENGLISH_WORD));
         assertSoftly(softly -> {
             softly.assertThat(dictionary.isEnglishWord(INVALID_ENGLISH_WORD))
                     .withFailMessage("%s is not a valid English word", INVALID_ENGLISH_WORD)
                     .isFalse();
         });
    }

    /**
     * Test the dictionary service finding possible words of a given string
     */
    @Test
    void testValidatePossibleWord() {
        when(dictService.isEnglishWord(POSSIBLE_ENGLISH_WORD)).thenReturn(isThisEnglish(POSSIBLE_ENGLISH_WORD));
        assertSoftly(softly -> {
            softly.assertThat(dictionary.isEnglishWord(POSSIBLE_ENGLISH_WORD))
                    .describedAs("%s is a valid English word", POSSIBLE_ENGLISH_WORD)
                    .isTrue();
        });
        // Print all possible words
        for (String word : dictionary.findPossibleWords(POSSIBLE_ENGLISH_WORD)) {
            log.info(word);
        }
    }

    /**
     * Test the dictionary service with the real English word: WORKING
     */
    @Test
    void testValidateEnglishWord() {
        when(dictService.isEnglishWord(VALID_ENGLISH_WORD)).thenReturn(isThisEnglish(VALID_ENGLISH_WORD));
        assertSoftly(softly -> {
            softly.assertThat(dictionary.isEnglishWord(VALID_ENGLISH_WORD))
                    .describedAs("%s is a valid English word", VALID_ENGLISH_WORD)
                    .isTrue();
        });
        // Print all possible words
        log.info(dictionary.findPossibleWords(VALID_ENGLISH_WORD).toString());
    }
}
