package components;

/**
 * Created by sascha on 07.06.17.
 */
public class MatchResult implements Comparable<MatchResult> {
    private int score, sourceStart, sourceEnd, targetStart, targetEnd;
    private final String dbString, searchString;

    public MatchResult(String sourceText, String targetText, int score,int sourceStart,int sourceEnd, int targetStart, int targetEnd){
        this.dbString = sourceText;
        this.searchString = targetText;
        this.score=score;
        this.sourceStart=sourceStart;
        this.sourceEnd=sourceEnd;
        this.targetStart = targetStart;
        this.targetEnd = targetEnd;
    }

    public String getDbString(){
        return dbString.substring(sourceStart,sourceEnd);
    }

    public String getSearchString(){
        return searchString.substring(targetStart,targetEnd);
    }



    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSourceStart() {
        return sourceStart;
    }

    public void setSourceStart(int start) {
        this.sourceStart = start;
    }

    public int getSourceEnd() {
        return sourceEnd;
    }

    public void setSourceEnd(int end) {
        this.sourceEnd = end;
    }

    public int getTargetStart() {
        return targetStart;
    }

    public void setTargetStart(int targetStart) {
        this.targetStart = targetStart;
    }

    public int getTargetEnd() {
        return targetEnd;
    }

    public void setTargetEnd(int targetEnd) {
        this.targetEnd = targetEnd;
    }

    public int getTargetSize(){
        return targetEnd-targetStart;
    }

    @Override
    public int compareTo(MatchResult other) {
        return (this.getSourceEnd()-this.getSourceStart()+this.getTargetEnd()-this.getTargetStart())
                +(other.getSourceEnd()-other.getSourceStart()+other.getTargetEnd()-other.getTargetStart());
    }

    @Override
    public String toString(){
        return this.searchString.substring(targetStart,targetEnd)+" "+this.dbString.substring(sourceStart,sourceEnd)+":"+score;
    }
}
