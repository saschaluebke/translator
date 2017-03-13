package web.model;

/**
 * Created by Saschbot on 11.03.2017.
 */
public class Word {
    private int id;
    private String name;

    public Word(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
