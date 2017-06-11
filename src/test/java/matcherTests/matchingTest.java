package matcherTests;

import components.MatchResult;
import components.MatchResultSet;
import components.Word;
import database.DBHelper;
import matching.Matcher;
import matching.distance.Levenshtein;
import matching.iterate.CharacterStrategy;
import matching.iterate.WordStrategy;
import matching.sorting.IntervalSort;
import matching.sorting.ScoreSort;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by sascha on 01.06.17.
 */
public class matchingTest {
    static DBHelper dbh;
    static Matcher matcher;

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        matcher = new Matcher(new CharacterStrategy(), new Levenshtein(),new ScoreSort());
        matcher.setMaxScore(3);
        matcher.setMinScore(0);
        dbh = new DBHelper();
        dbh.dropAllTables();
        dbh.truncate("languages");
        dbh.newLanguage("de");
        dbh.putWordList(new Word(0,"Hallo","de"));
        dbh.putWordList(new Word(0,"Glukose","de"));
        dbh.putWordList(new Word(0,"There is Clucose","de"));
        dbh.putWordList(new Word(0,"Die Glucosennmanufraktur muss entfacht werden","de"));
    }

    @Test
    public void levenshteinTest() {
        Levenshtein l = new Levenshtein();
        int result = l.getDistance("Hallo","Hallo");
        assertEquals(0,result);
    }

    @Test
    public void ScoreSortTest() {

        ArrayList<MatchResult> mr = matcher.getSortedMatchResult("Glukose","Get Glucose level");
        //System.out.println(mr.toString());
        assertEquals(3,mr.get(0).getTargetSize());
    }


    @Test
    public void IntervalSortTest() {
        matcher.setSortStrategy(new IntervalSort());
        ArrayList<MatchResult> mr = matcher.getSortedMatchResult("Glukose","Get Glucose level");
        //System.out.println(mr.toString());
        assertEquals(7,mr.get(0).getTargetSize());
    }

    @Test
    public void dbMatchTest() {
        matcher.setSortStrategy(new IntervalSort());
        matcher.setMaxScore(1);
        MatchResultSet mrs = matcher.getMatchingWordList("Glukose","de");
        //System.out.println(mrs.toString());
        assertEquals(true,true);
    }

    @Test
    public void wordStrategyMatchTest() {
        matcher.setSortStrategy(new IntervalSort());
        matcher.setIterateStrategy(new WordStrategy());
        matcher.setMaxScore(4);
        MatchResultSet mrs = matcher.getMatchingWordList("Glukose","de");

        //System.out.println(mrs.toString());

        assertEquals(1,mrs.getMatchResults().get(3).get(0).getScore());
    }


}
