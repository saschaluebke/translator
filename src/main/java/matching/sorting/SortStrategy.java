package matching.sorting;

import components.MatchResult;

import java.util.ArrayList;

public interface SortStrategy {
    public ArrayList<MatchResult> sort(ArrayList<MatchResult> results, int minScore, int maxScore);
}
