package matcherTests;

import components.MatchResult;
import matcher.Distance;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by sascha on 01.06.17.
 */
public class distanceTest {

    @Test
    public void levenshteinSmallTest() {
        String a = "Hallo";
        String b = "Hello";

        int leven = Distance.levenshteinDistance(a,b);

        assertEquals(1,leven);

        a = "Hallo";
        b = "Hallo";

        leven = Distance.levenshteinDistance(a,b);

        assertEquals(0,leven);

    }

    @Test
    public void levenshteinBigTest() {
        String a = "Hallo";
        String b = "Hello World!";

        int leven = Distance.levenshteinDistance(a,b);

        assertEquals(8,leven);

    }

    @Test
    public void distanceTest() {
        String a = "Hallo";
        String b = "Hallo Welt";

        int distance = Distance.distance(a,b);

        assertEquals(0,distance);

    }

    @Test
    public void distanceMatchResultsTest() {
        String a = "Halloöchen";
        String b = "Ich sage: Hallo Welt";

        ArrayList<MatchResult> results = Distance.distanceMatchResults(a,b);

        assertEquals(10,results.get(0).getScore());

    }

    @Test
    public void distanceSortMatchResultsTest() {
        String a = "Halloöchen";
        String b = "Ich sage: Hallo Welt";

        ArrayList<MatchResult> results = Distance.distanceMatchResults(a,b);
        ArrayList<MatchResult> sortResult = Distance.sortResult(results,0);
        ArrayList<MatchResult> sortAllResults = Distance.sortResult(results,0,9);
        assertEquals(0,sortResult.get(0).getScore());
        assertEquals(1,sortAllResults.get(6).getScore());

    }


}
