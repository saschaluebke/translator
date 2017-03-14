package translatorsTests;

import org.junit.BeforeClass;
import org.junit.Test;
import translators.TranslatorHelper;
import translators.Transltr;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sasch on 14.03.2017.
 */
public class TranslatorHelperTest {
    static TranslatorHelper th;

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        th = new TranslatorHelper();
    }

    @Test
    public void tokenizerTest() {
        String[] token = th.tokenizer("Ich Bild-band.");
        assertEquals("Ich",token[0]);
        assertEquals("Bild",token[1]);
        assertEquals("band",token[2]);
    }

    @Test
    public void retokenizerTest() {
        String[] token = th.tokenizer("Ich Bild-band.");
        String result = th.retokenizer(token);
        assertEquals("Ich Bild band",result);

    }
}
