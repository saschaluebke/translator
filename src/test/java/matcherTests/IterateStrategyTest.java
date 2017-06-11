package matcherTests;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import components.MatchResult;
import components.Word;
import matching.distance.Levenshtein;
import matching.iterate.CharacterStrategy;
import matching.iterate.WordStrategy;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by sascha on 11.06.17.
 */
public class IterateStrategyTest  {

    @Test
    public void characterStrategyTest() {
        CharacterStrategy cs = new CharacterStrategy();
        cs.setDBString("Hallöchen");
        cs.setSearchString("Hallo");
        ArrayList<MatchResult> results = cs.getMatchList();
        System.out.println(results.toString());
       // assertEquals(0,result);
    }

    @Test
    public void wordStrategyTest() {
        WordStrategy ws = new WordStrategy();
        ws.setDBString("Hallöchen Ich bin Sascha");
        ws.setSearchString("Hallo");
        ArrayList<MatchResult> results = ws.getMatchList();
        //System.out.println("Result:"+results.toString());

        ws.setDBString("Hallöchen/ich/bin SaschaLuebke");
        results = ws.getMatchList();
        //System.out.println("Result:"+results.toString());
        assertEquals("Hal",results.get(0).getDbString());
    }
}
