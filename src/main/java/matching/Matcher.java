package matching;

import components.MatchResult;
import components.MatchResultSet;
import components.Word;
import database.DBHelper;
import matching.distance.DistanceStrategy;
import matching.iterate.IterateStrategy;
import matching.sorting.SortStrategy;
import java.util.ArrayList;

public class Matcher {
    private SortStrategy sortStrategy;
    private DistanceStrategy distanceStrategy;
    private IterateStrategy iterateStrategy;
    private int minScore, maxScore;

    public Matcher(IterateStrategy iterateStrategy, DistanceStrategy distanceStrategy,SortStrategy sortStrategy){
        this.iterateStrategy = iterateStrategy;
        this.sortStrategy = sortStrategy;
        this.distanceStrategy = distanceStrategy;
    }

    private ArrayList<MatchResult> getMatchResult(String searchString, String dbString){
        iterateStrategy.setDBString(dbString);
        iterateStrategy.setSearchString(searchString);
        ArrayList<MatchResult> matchResults = iterateStrategy.getMatchList();

        for(MatchResult mr : matchResults){
            mr.setScore(distanceStrategy.getDistance(mr.getSearchString(),mr.getDbString()));
        }

        return matchResults;
    }

    public ArrayList<MatchResult> getSortedMatchResult(String searchString, String dbString){
        ArrayList<MatchResult> results = getMatchResult(searchString,dbString);
        ArrayList<MatchResult> sortedResults = sortStrategy.sort(results,minScore,maxScore);
        return sortedResults;
    }

    public MatchResultSet getMatchingWordList(String name, String language){
        DBHelper dbh = new DBHelper();
        MatchResultSet mrs = new MatchResultSet(minScore,maxScore,distanceStrategy,sortStrategy);
        ArrayList<Word> words = dbh.getAllWords(language);
        for(Word w: words){
            ArrayList<MatchResult> result = getSortedMatchResult(name,w.getName());
            if (result.size()>0){
                mrs.addMatchResults(result,w);
            }
        }
        return mrs;
    }

    public SortStrategy getSortStrategy() {
        return sortStrategy;
    }

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public DistanceStrategy getDistanceStrategy() {
        return distanceStrategy;
    }

    public void setDistanceStrategy(DistanceStrategy distanceStrategy) {
        this.distanceStrategy = distanceStrategy;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public void setIterateStrategy(IterateStrategy iterateStrategy){
        this.iterateStrategy = iterateStrategy;
    }

    public IterateStrategy getIterateStrategy(){
        return this.iterateStrategy;
    }
}
