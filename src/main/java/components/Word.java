package components;

public class Word {
    private String name;
    private int id;
    private String description="";     //To distinguish homonyms
    private String language;

    public Word(int id,String name, String language){
        setWord(name);
        setLanguage(language);
        setId(id);
    }

    public Word(int id,String name, String description, String language){
        setWord(name);
        setLanguage(language);
        setId(id);
        setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setWord(String name) {
        this.name = name;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


}