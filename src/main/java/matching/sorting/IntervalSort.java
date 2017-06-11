package matching.sorting;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import components.MatchResult;

import javax.swing.text.MutableAttributeSet;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sascha on 09.06.17.
 */
public class IntervalSort implements SortStrategy{
    private ArrayList<MatchResult> intervallSort(ArrayList<MatchResult> results){
        quicksort(results,0,results.size()-1);
        return results;
    }

    public static void quicksort(ArrayList<MatchResult> list, int from, int to) {
        if (from < to) {
            int pivot = from;
            int left = from + 1;
            int right = to;
            int pivotValue = list.get(pivot).getTargetSize();
            while (left <= right) {
                // left <= to -> limit protection
                while (left <= to && pivotValue < list.get(left).getTargetSize()) {
                    left++;
                }
                // right > from -> limit protection
                while (right > from && pivotValue >= list.get(right).getTargetSize()) {
                    right--;
                }
                if (left < right) {
                    Collections.swap(list, left, right);
                }
            }
            Collections.swap(list, pivot, left - 1);
            quicksort(list, from, right - 1); // <-- pivot was wrong!
            quicksort(list, right + 1, to);   // <-- pivot was wrong!
        }
    }

    @Override
    public ArrayList<MatchResult> sort(ArrayList<MatchResult> results, int minScore, int maxScore) {
        ArrayList<MatchResult> sortedResults = new ArrayList<>();
        for(int score=minScore;score<=maxScore;score++){
            for (MatchResult mr : results){
                if (mr.getScore() == score){
                    sortedResults.add(mr);
                }
            }
            intervallSort(sortedResults);
        }
        return sortedResults;
    }
}
