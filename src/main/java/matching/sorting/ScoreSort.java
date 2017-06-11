package matching.sorting;

import components.MatchResult;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sascha on 09.06.17.
 */
public class ScoreSort implements SortStrategy {
    @Override
    public ArrayList<MatchResult> sort(ArrayList<MatchResult> results, int minScore, int maxScore) {
        ArrayList<MatchResult> sortedResults = new ArrayList<>();
        for(int score=minScore;score<=maxScore;score++){
            for (MatchResult mr : results){
                if (mr.getScore() == score){
                    sortedResults.add(mr);
                }
            }
            Collections.sort(sortedResults);
        }
        return sortedResults;
    }
}
