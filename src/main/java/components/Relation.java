package components;

//This correspond to a translation or Synonym table
public class Relation implements Comparable<Relation>{
    private int id;
    private int count;
    private int prior;
    private int idFrom;
    private int idTo;

    public Relation(int id, int idFrom, int idTo){
        setId(id);
        setIdFrom(idFrom);
        setIdTo(idTo);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrior() {
        return prior;
    }

    public void setPrior(int prior) {
        this.prior = prior;
    }

    public int getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(int idFrom) {
        this.idFrom = idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    public void setIdTo(int idTo) {
        this.idTo = idTo;
    }


    public int compareTo(Relation otherRelation) {

        return Integer.compare(otherRelation.prior,this.prior);
    }
}
