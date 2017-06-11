package matching.iterate;

import components.MatchResult;

import java.util.ArrayList;

public class CharacterStrategy implements IterateStrategy {

    private String dbString, searchString;
    private ArrayList<MatchResult> matchList;

    @Override
    public ArrayList<MatchResult> getMatchList() {
        this.matchList = new ArrayList<>();
        for(int v=3;v<=searchString.length();v++) {
            for (int h = 0; h < v - 2; h++) {
                String currentSearchString = searchString.substring(h,v);
                for (int i = 0; i < dbString.length() - currentSearchString.length()+1; i++) {
                    matchList.add(new MatchResult(dbString,searchString,-1,i,i+currentSearchString.length(),h,v));
                }
            }
        }
        return matchList;
    }

    @Override
    public void setDBString(String dbString) {
        this.dbString = dbString;
    }

    @Override
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }




}
