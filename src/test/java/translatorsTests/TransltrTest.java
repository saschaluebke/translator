package translatorsTests;

import org.junit.BeforeClass;
import org.junit.Test;
import translators.Transltr;

import static org.junit.Assert.assertEquals;

/**
 * Created by Saschbot on 12.03.2017.
 */
public class TransltrTest {
    static Transltr transltr;

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        transltr = new Transltr();
    }

    @Test
    public void singleTranslationTest() {
        String translation = transltr.translate("dog");
        assertEquals("Hund",translation);
    }
/*TODO: Mag er manchmal! nicht am besten auf Translation Errors besser gefasst sein (nochmal probieren)
    @Test
    public void multiTranslationTest() {
        String translation = transltr.translate("dog cat");
        assertEquals("Hund Katze",translation);
    }
*/
}
