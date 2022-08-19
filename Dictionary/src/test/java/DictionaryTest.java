
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import lombok.extern.slf4j.Slf4j;
import org.example.Dictionary;
import org.example.DictionaryService;
import org.example.DictionaryServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class DictionaryTest {

    public static final String VALID_ENGLISH_WORD = "WORKING";
    public static final String INVALID_ENGLISH_WORD = "AMIGO";
    public static final String POSSIBLE_ENGLISH_WORD = "NO";

    private Dictionary dictionary;
    private DictionaryService dictService;

    /**
     * Inject the DictionaryServiceImp class to get the dictionary file
     */
    @BeforeEach
    public void init() {
        dictService = new DictionaryServiceImp();
        dictionary = new Dictionary(dictService);
        dictionary.getDictionary();
    }

    /**
     * Test the dictionary service with the dictionary list
     */
    @Test
    void testDictionaryService() {
        assertSoftly(softly -> {
            softly.assertThat(dictionary.getDictionary().size()).isNotNull();
        });
    }

    /**
     * Test the dictionary service with the non-English word: AMIGO
     */
    @Test
    void testValidateNonEnglishWord() {
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
        assertSoftly(softly -> {
            softly.assertThat(dictionary.isEnglishWord(VALID_ENGLISH_WORD))
                    .describedAs("%s is a valid English word", VALID_ENGLISH_WORD)
                    .isTrue();
        });
        // Print all possible words
        log.info(dictionary.findPossibleWords(VALID_ENGLISH_WORD).toString());
    }
}
