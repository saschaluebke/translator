package components;

import matching.distance.DistanceStrategy;
import matching.sorting.SortStrategy;
import org.springframework.beans.factory.support.ManagedList;

import java.util.ArrayList;

/**
 * Created by sascha on 09.06.17.
 */
public class MatchResultSet {
    private int maxScore, minScore;
    private DistanceStrategy distanceStrategy;
    private SortStrategy sortStrategy;
    private ArrayList<ArrayList<MatchResult>> matchResults;
    private ArrayList<Word> words;

    public MatchResultSet(int minScore, int maxScore, DistanceStrategy distanceStrategy, SortStrategy sortStrategy){
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.sortStrategy = sortStrategy;
        this.distanceStrategy = distanceStrategy;
        matchResults = new ArrayList<>();
        words = new ArrayList<>();
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public ArrayList<ArrayList<MatchResult>> getMatchResults() {
        return matchResults;
    }

    public void setMatchResults(ArrayList<ArrayList<MatchResult>> matchResults) {
        this.matchResults = matchResults;
    }

    public ArrayList<Word> getWords(){
        return words;
    }

    public void addMatchResults(ArrayList<MatchResult> matchResult, Word word){
        this.matchResults.add(matchResult);
        this.words.add(word);
    }

    @Override
    public String toString() {
        String output = "";
        for(ArrayList<MatchResult> mr : matchResults){
            output = output+mr.toString()+System.lineSeparator();
        }
        return output;
    }
}
