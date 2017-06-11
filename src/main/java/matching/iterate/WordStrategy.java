package matching.iterate;

import components.MatchResult;

import java.util.ArrayList;

public class WordStrategy implements IterateStrategy {
    private final String REGEX = "( )|(/)"; //Split by space or /
    private String dbString, searchString;
    private ArrayList<MatchResult> matchResults;


    @Override
    public void setDBString(String dbString) {
        this.dbString = dbString;
    }

    @Override
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    //TODO: testen wie viel ersparniss (wie viele worttrenner im mittel pro wort)
    @Override
    public ArrayList<MatchResult> getMatchList() {
        matchResults = new ArrayList<>();
        String[] splitted = dbString.split(REGEX);
        for(String s: splitted){
            CharacterStrategy cs = new CharacterStrategy();
            cs.setSearchString(searchString);
            cs.setDBString(s);
            matchResults.addAll(cs.getMatchList());
        }
        return matchResults;
    }
}
