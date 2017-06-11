package matching.iterate;

import components.MatchResult;

import java.util.ArrayList;

public interface IterateStrategy {
    void setDBString(String dbString);
    void setSearchString(String searchString);
    ArrayList<MatchResult> getMatchList();
}
