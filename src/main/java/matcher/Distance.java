package matcher;

import components.MatchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#
 *
 * http://secondstring.sourceforge.net/
 *
 * Soundex: http://introcs.cs.princeton.edu/java/31datatype/Soundex.java.html
 *
 *
 */
public class Distance {

    private Distance(){

    }
//Standard Levenshtein Distanz Funktion nur das Maximum
    public static int distance(String aString, String bString){
        int distance = 0;
        ArrayList<Integer> results = new ArrayList();


        for(int i=0;i<=bString.length()-aString.length();i++){
//            System.out.println(i+":"+bString.substring(i,aString.length()+i));
            results.add(levenshteinDistance(aString,bString.substring(i,aString.length()+i)));
        }

        return Collections.min(results);
    }

    public static ArrayList<MatchResult> distanceMatchResults(String targetString, String sourceString){
        int distance = 0;
        ArrayList<MatchResult> results = new ArrayList();

        for(int v=targetString.length();v>2;v--){
            for (int h=0;h<v-2;h++){
                String a = targetString.substring(h,v);
                for(int i=0;i<=sourceString.length()-a.length();i++){

                    int result = levenshteinDistance(a,sourceString.substring(i,a.length()+i));
           // System.out.println(v+"/"+h+"/"+i +"-"+a+"/"+sourceString.substring(i,a.length()+i)+"="+result);
                    results.add(new MatchResult(sourceString,targetString,result,i,i+a.length(),h,v));
                }
            }
        }

        return results;
    }

    public static ArrayList<MatchResult> sortResult(ArrayList<MatchResult> results, int score){
        ArrayList<MatchResult> bestResults = new ArrayList<>();
//erst nach Distanz sortieren, danach nach größe der Übereinstimmung
        for (MatchResult mr : results){
            if (mr.getScore() == score){
                bestResults.add(mr);
            }
        }
        Collections.sort(bestResults);
        //System.out.println(bestResults.toString());
        return bestResults;
    }

    public static ArrayList<MatchResult> sortResult(ArrayList<MatchResult> results, int minScore, int maxScore){
        ArrayList<MatchResult> sortResults = new ArrayList<>();
        for(int i=minScore;i<=maxScore;i++){
            sortResults.addAll(sortResult(results,i));
        }
        return sortResults;
    }

    public static int levenshteinDistance (CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for(int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert  = cost[i] + 1;
                int cost_delete  = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost; cost = newcost; newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    public static String soundex(String s) {
        char[] x = s.toUpperCase().toCharArray();
        char firstLetter = x[0];

        // convert letters to numeric code
        for (int i = 0; i < x.length; i++) {
            switch (x[i]) {

                case 'B':
                case 'F':
                case 'P':
                case 'V':
                    x[i] = '1';
                    break;

                case 'C':
                case 'G':
                case 'J':
                case 'K':
                case 'Q':
                case 'S':
                case 'X':
                case 'Z':
                    x[i] = '2';
                    break;

                case 'D':
                case 'T':
                    x[i] = '3';
                    break;

                case 'L':
                    x[i] = '4';
                    break;

                case 'M':
                case 'N':
                    x[i] = '5';
                    break;

                case 'R':
                    x[i] = '6';
                    break;

                default:
                    x[i] = '0';
                    break;
            }
        }

        // remove duplicates
        String output = "" + firstLetter;
        for (int i = 1; i < x.length; i++)
            if (x[i] != x[i-1] && x[i] != '0')
                output += x[i];

        // pad with 0's or truncate
        output = output + "0000";
        return output.substring(0, 4);
    }



}
