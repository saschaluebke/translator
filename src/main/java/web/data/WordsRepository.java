package web.data;

import web.model.Word;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Saschbot on 11.03.2017.
 */
@Component
public class WordsRepository {
    private static final List<Word> ALL_WORDS = Arrays.asList(
            new Word(1,""),
            new Word(0,"Hallo")
    );

    public Word findByName(String name) {
        for(Word w : ALL_WORDS) {
            if(w.getName().equals(name)) {
                return w;
            }
        }
        return null;
    }

    public List<Word> getAllWords() {
        return ALL_WORDS;
    }


}
