package components;

/**
 * Created by sascha on 07.06.17.
 */
public class MatchResult implements Comparable<MatchResult> {
    private int score, sourceStart, sourceEnd, targetStart, targetEnd;
    private final String sourceText, targetText;

    public MatchResult(String sourceText, String targetText, int score,int sourceStart,int sourceEnd, int targetStart, int targetEnd){
        this.sourceText = sourceText;
        this.targetText = targetText;
        this.score=score;
        this.sourceStart=sourceStart;
        this.sourceEnd=sourceEnd;
        this.targetStart = targetStart;
        this.targetEnd = targetEnd;
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

    @Override
    public int compareTo(MatchResult other) {
        return (this.getSourceEnd()-this.getSourceStart()+this.getTargetEnd()-this.getTargetStart())
                +(other.getSourceEnd()-other.getSourceStart()+other.getTargetEnd()-other.getTargetStart());
    }

    @Override
    public String toString(){
        int compareScore = this.getSourceEnd()-this.getSourceStart()+this.getTargetEnd()-this.getTargetStart();
        return this.targetText.substring(targetStart,targetEnd)+" "+this.sourceText.substring(sourceStart,sourceEnd)+":"+compareScore;
    }
}
